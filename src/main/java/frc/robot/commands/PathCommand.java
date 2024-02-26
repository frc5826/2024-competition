package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.positioning.Orientation;
import frc.robot.subsystems.LocalizationSubsystem;

import java.util.function.Function;
import java.util.function.Supplier;

public class PathCommand extends Command {

    private final Supplier<Orientation> orientationSupplier;

    private final Function<Orientation, Pose2d> positionSupplier;

    private final LocalizationSubsystem localizationSubsystem;

    private Command pathCommand;

    public PathCommand(Supplier<Orientation> orientationSupplier, Function<Orientation, Pose2d> positionSupplier, LocalizationSubsystem localizationSubsystem) {
        this.orientationSupplier = orientationSupplier;
        this.positionSupplier = positionSupplier;
        this.localizationSubsystem = localizationSubsystem;
    }

    @Override
    public void initialize() {
        if(pathCommand != null){
            pathCommand.end(true);
            pathCommand = null;
        }

        Orientation orientation = orientationSupplier.get();

        if(orientation.isValid()) {
            pathCommand = localizationSubsystem.buildPath(positionSupplier.apply(orientationSupplier.get()));
            pathCommand.initialize();
        }
    }

    @Override
    public void execute() {
        //TODO - Why does PathWithStopDistance have specific logic to deal with the rotation?
        if(pathCommand != null){
            pathCommand.execute();
        }
    }

    @Override
    public void end(boolean interrupted) {
        //TODO - Same question as execute()
        localizationSubsystem.removeRotationTarget();
        pathCommand.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        // We want to kill the command if we're not "ready" or the path is complete.
        // In practice, not being "ready" means we don't know our orientation.

        //TODO - PathWithStopDistance doesn't rely on "isFinished". Do we want to copy that here?
        return pathCommand == null || pathCommand.isFinished();
    }



}
