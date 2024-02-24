package frc.robot.commands;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LocalizationSubsystem;

public class PathWithStopDistance extends Command {
    private LocalizationSubsystem localizationSubsystem;
    private Pose2d ringPose;
    private double stopDistance;
    private boolean speakerDrive;

    private Command buildCommand;
    public PathWithStopDistance(LocalizationSubsystem localizationSubsystem, Pose2d ringPose, double stopDistance, boolean speakerDrive) {
        this.localizationSubsystem = localizationSubsystem;

        this.ringPose = ringPose;

        this.stopDistance = stopDistance;

        this.speakerDrive = speakerDrive;
    }

    @Override
    public void initialize() {
        buildCommand = localizationSubsystem.buildPath(ringPose);
        //CommandScheduler.getInstance().schedule(buildCommand);
        buildCommand.initialize();
    }

    @Override
    public void execute() {
        buildCommand.execute();
        double angleChange;
        if (speakerDrive) {
            angleChange = Math.PI;
        } else {
            angleChange = 0;
        }
        localizationSubsystem.setRotationTarget(ringPose.getTranslation()
                .minus(localizationSubsystem.getCurrentPose().getTranslation()).getAngle().minus(Rotation2d.fromRadians(angleChange)));
    }

    @Override
    public void end(boolean interrupted) {
        localizationSubsystem.removeRotationTarget();
        //CommandScheduler.getInstance().cancel(buildCommand);
        buildCommand.end(interrupted);
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
