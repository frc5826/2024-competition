package frc.robot.commands;

import com.pathplanner.lib.path.PathConstraints;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LocalizationSubsystem;

public class PathWithStopDistance extends Command {
    private LocalizationSubsystem localizationSubsystem;
    private Pose2d ringPose;
    private double stopDistance;

    private Command buildCommand;
    public PathWithStopDistance(LocalizationSubsystem localizationSubsystem, Pose2d ringPose, double stopDistance) {
        this.localizationSubsystem = localizationSubsystem;

        this.ringPose = ringPose;

        this.stopDistance = stopDistance;
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
        localizationSubsystem.setRotationTarget(ringPose.getTranslation()
                .minus(localizationSubsystem.getCurrentPose().getTranslation()).getAngle());
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
