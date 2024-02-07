package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.math.PID;
import frc.robot.subsystems.IntakeElevatorSubsystem;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.RingResult;
import frc.robot.subsystems.SwerveSubsystem;

public class PickupRing extends Command {

    private LocalizationSubsystem localizationSubsystem;
    private SwerveSubsystem swerveSubsystem;

    private PID turnPID = new PID(Constants.cTurnPID, 2, 0.01, 0.01, this::ringYaw);

    private PID drivePID = new PID(Constants.cDrivePID, 2, 0.01, 0.01, this::ringDistance);

    private RingResult ringTracking;
    private double previousRingDistance;

    private double epsilon = .15;

    public PickupRing(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        ringTracking = localizationSubsystem.getBestPickupRing();
        turnPID.setGoal(0);
        drivePID.setGoal(0);
    }

    @Override
    public void execute() {
        RingResult bestRing = localizationSubsystem.getBestPickupRing();

        if (bestRing.getDistance() - previousRingDistance < epsilon) {
            ringTracking = bestRing;
        }

        previousRingDistance = ringTracking.getDistance();

        swerveSubsystem.driveRobotOriented(new ChassisSpeeds(-drivePID.calculate(), 0, turnPID.calculate()));
    }

    private double ringYaw() {
        return ringTracking.getAngleToHeading();
    }

    private double ringDistance() {
        return ringTracking.getDistance();
    }

}
