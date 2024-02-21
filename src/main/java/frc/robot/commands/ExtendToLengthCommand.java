package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class ExtendToLengthCommand extends Command {

    private LegSubsystem legSubsystem;
    private Optional<Double> extensionMeters;

    public ExtendToLengthCommand(double extensionMeters, LegSubsystem legSubsystem) {
        this.legSubsystem = legSubsystem;
        this.extensionMeters = Optional.of(extensionMeters);
    }

    public ExtendToLengthCommand(Optional<Double> extensionMeters, LegSubsystem legSubsystem) {
        this.legSubsystem = legSubsystem;
        this.extensionMeters = extensionMeters;
    }

    public ExtendToLengthCommand(LegSubsystem legSubsystem) {
        this.legSubsystem = legSubsystem;
        this.extensionMeters = Optional.empty();
    }

    @Override
    public void initialize() {
        super.initialize();
        if (extensionMeters.isPresent()){
            legSubsystem.setDesiredExtension(extensionMeters.get());
        } else {
            legSubsystem.setExtensionHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(legSubsystem.getExtension() - legSubsystem.getDesiredExtensionRotations())
                < 0.05;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
