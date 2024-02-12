package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ExtendToLengthCommand extends Command {

    private ElevatorSubsystem elevatorSubsystem;
    private double extensionMeters;

    public ExtendToLengthCommand(ElevatorSubsystem elevatorSubsystem, double extensionMeters) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.extensionMeters = extensionMeters;
    }

    @Override
    public void initialize() {
        super.initialize();
        elevatorSubsystem.setDesiredExtension(extensionMeters);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elevatorSubsystem.setExtensionHome();
    }
}
