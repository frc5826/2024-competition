package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.positioning.Orientation;
import frc.robot.subsystems.LocalizationSubsystem;

import java.util.function.Function;
import java.util.function.Supplier;

public class PathThenFollowCommand extends Command {

    private LocalizationSubsystem localizationSubsystem;

    private final Supplier<Orientation> orientationSupplier;

    private final Function<Orientation, Pose2d> endPositionSupplier;

    private final Function<Orientation, Pose2d> startPositionSupplier;

    private Command pathCommand;

    public PathThenFollowCommand(Function<Orientation, Pose2d> startPositionSupplier, Function<Orientation, Pose2d> endPositionSupplier, Supplier<Orientation> orientationSupplier, LocalizationSubsystem localizationSubsystem) {
        this.localizationSubsystem = localizationSubsystem;
        this.orientationSupplier = orientationSupplier;
        this.endPositionSupplier = endPositionSupplier;
        this.startPositionSupplier = startPositionSupplier;
    }

    @Override
    public void initialize() {
        if(pathCommand != null){
            pathCommand.end(true);
            pathCommand = null;
        }

        Orientation orientation = orientationSupplier.get();

        if(orientation.isValid()) {
            Pose2d startPose = startPositionSupplier.apply(orientationSupplier.get());
            Pose2d endPose = endPositionSupplier.apply(orientationSupplier.get());
            pathCommand = localizationSubsystem.buildPathThenFollow(endPose, startPose);
            pathCommand.initialize();
        }
    }

    @Override
    public void execute() {
        if(pathCommand != null){
            pathCommand.execute();
        }
    }

    @Override
    public void end(boolean interrupted) {
        localizationSubsystem.removeRotationTarget();
        pathCommand.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        // We want to kill the command if we're not "ready" or the path is complete.
        // In practice, not being "ready" means we don't know our orientation.
        return pathCommand == null || pathCommand.isFinished();
    }

}
