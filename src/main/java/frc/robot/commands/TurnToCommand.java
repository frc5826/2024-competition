package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.math.PID;
import frc.robot.math.ShooterMath;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class TurnToCommand extends Command {

    private LocalizationSubsystem localizationSubsystem;
    private SwerveSubsystem swerveSubsystem;
    private Pose2d ringPose;

    private PID turnPID = new PID(Constants.cTurnPID, 6, 0.01, 0.01, this::getTurn);

    public TurnToCommand(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem, Pose2d ringPose) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;
        this.ringPose = ringPose;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void execute() {
        swerveSubsystem.driveFieldOriented(new ChassisSpeeds(0, 0, turnPID.calculate()));
    }

    @Override
    public void initialize() {
        turnPID.setGoal(0);
    }

    private double getTurn() {
        return -ShooterMath.fixSpin(ringPose.getTranslation()
                .minus(localizationSubsystem.getCurrentPose().getTranslation()).getAngle().getRadians()
                - localizationSubsystem.getCurrentPose().getRotation().getRadians());

    }

    @Override
    public boolean isFinished() {
        return Math.abs(getTurn()) < 0.15;
    }
}
