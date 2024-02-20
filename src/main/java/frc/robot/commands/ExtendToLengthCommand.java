package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class ExtendToLengthCommand extends Command {

    private ElevatorSubsystem elevatorSubsystem;
    private Optional<Double> extensionMeters;

    public ExtendToLengthCommand(double extensionMeters, ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.extensionMeters = Optional.of(extensionMeters);
    }

    public ExtendToLengthCommand(Optional<Double> extensionMeters, ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.extensionMeters = extensionMeters;
    }

    public ExtendToLengthCommand(ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.extensionMeters = Optional.empty();
    }

    @Override
    public void initialize() {
        super.initialize();
        if (extensionMeters.isPresent()){
            elevatorSubsystem.setDesiredExtension(extensionMeters.get());
        } else {
            elevatorSubsystem.setExtensionHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(elevatorSubsystem.getExtension() - elevatorSubsystem.getDesiredExtensionRotations())
                < 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
