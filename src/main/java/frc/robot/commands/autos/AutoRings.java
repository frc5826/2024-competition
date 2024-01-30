package frc.robot.commands.autos;

import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class AutoRings extends Command {
    private LocalizationSubsystem localizationSubsystem;
    private SwerveSubsystem swerveSubsystem;
    private Pose2d ringPose;

    private Command buildCommand;
    public AutoRings(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem, Pose2d ringPose) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        this.ringPose = ringPose;
    }

    @Override
    public void initialize() {
        buildCommand = localizationSubsystem.buildPath(ringPose);
        CommandScheduler.getInstance().schedule(buildCommand);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        CommandScheduler.getInstance().cancel(buildCommand);
    }

    @Override
    public boolean isFinished() {
        PathConstraints constraints = new PathConstraints(
                2.0,
                2.0,
                3.14159,
                3.14159);
            Pose2d currentPose = localizationSubsystem.getCurrentPose();
            ChassisSpeeds currentSpeeds = swerveSubsystem.getRobotVelocity();

            double currentVel =
                    Math.hypot(currentSpeeds.vxMetersPerSecond, currentSpeeds.vyMetersPerSecond);
            double stoppingDistance =
                    Math.pow(currentVel, 2) / (2 * constraints.getMaxAccelerationMpsSq());

            return currentPose.getTranslation().getDistance(ringPose.getTranslation()) - 1
                    <= stoppingDistance;
    }
}
