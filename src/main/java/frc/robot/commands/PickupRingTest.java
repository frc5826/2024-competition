package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.math.PID;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class PickupRingTest extends Command {

    private VisionSubsystem visionSubsystem;
    private SwerveSubsystem swerveSubsystem;
    private LocalizationSubsystem localizationSubsystem;

    private double angleGoal;

    private final PID turnPID = new PID(1.5, 0, 0.1, 3, 0, 0.04, this::turn);

    public PickupRingTest(VisionSubsystem visionSubsystem, SwerveSubsystem swerveSubsystem, LocalizationSubsystem localizationSubsystem) {
        this.visionSubsystem = visionSubsystem;
        this.swerveSubsystem = swerveSubsystem;
        this.localizationSubsystem = localizationSubsystem;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        angleGoal = -visionSubsystem.getBestRing().getAngleToHeading();
        turnPID.setGoal(angleGoal + localizationSubsystem.getCurrentPose().getRotation().getRadians());
    }

    @Override
    public void execute() {
        swerveSubsystem.driveRobotOriented(new ChassisSpeeds(0, 0, turnPID.calculate()));
    }

    private double turn() {
        return localizationSubsystem.getCurrentPose().getRotation().getRadians();
    }
}
