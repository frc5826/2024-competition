package frc.robot.commands;

import com.pathplanner.lib.util.PIDConstants;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.math.PID;
import frc.robot.math.ShooterMath;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TargetSpeakerCommand extends Command {
    private final SwerveSubsystem swerveSubsystem;
    private final LocalizationSubsystem localizationSubsystem;

    private final PID turnPID = new PID(Constants.cTurnPID, 6, 0, 0.02, this::getAngleDiff);

    public TargetSpeakerCommand(SwerveSubsystem swerveSubsystem, LocalizationSubsystem localizationSubsystem) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        turnPID.setGoal(0);
    }

    @Override
    public void execute() {
        swerveSubsystem.driveRobotOriented(new ChassisSpeeds(0, 0,
                turnPID.calculate()));
    }

    private double getAngleDiff(){
        return ShooterMath.fixSpin(
                localizationSubsystem.getCurrentPose().getRotation().getRadians() -
                        ShooterMath.getAngleToSpeaker(localizationSubsystem.getCurrentPose()));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(getAngleDiff()) < 0.08;
    }
}
