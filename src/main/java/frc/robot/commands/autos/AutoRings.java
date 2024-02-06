package frc.robot.commands.autos;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class AutoRings extends Command {
    private LocalizationSubsystem localizationSubsystem;
    private Pose2d ringPose;
    private double stopDistance;

    private Command buildCommand;
    public AutoRings(LocalizationSubsystem localizationSubsystem, Pose2d ringPose, double stopDistance) {
        this.localizationSubsystem = localizationSubsystem;

        this.ringPose = ringPose;

        this.stopDistance = stopDistance;
    }

    @Override
    public void initialize() {
        buildCommand = localizationSubsystem.buildPath(ringPose);
        //CommandScheduler.getInstance().schedule(buildCommand);
        buildCommand.schedule();
    }

    @Override
    public void execute() {
        localizationSubsystem.setRotationTarget(ringPose.getTranslation()
                .minus(localizationSubsystem.getCurrentPose().getTranslation()).getAngle());
    }

    @Override
    public void end(boolean interrupted) {
        localizationSubsystem.removeRotationTarget();
        //CommandScheduler.getInstance().cancel(buildCommand);
        buildCommand.cancel();
    }

    @Override
    public boolean isFinished() {
        PathConstraints constraints = new PathConstraints(
                2.0,
                1.0,
                3.14159,
                3.14159);
            Pose2d currentPose = localizationSubsystem.getCurrentPose();

            return currentPose.getTranslation().getDistance(ringPose.getTranslation()) - stopDistance <= 0;
    }
}
