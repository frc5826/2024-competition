package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class ExtendToLengthCommand extends Command {

    private ArmSubsystem armSubsystem;
    private Optional<Double> extensionMeters;

    public ExtendToLengthCommand(double extensionMeters, ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        this.extensionMeters = Optional.of(extensionMeters);
    }

    public ExtendToLengthCommand(Optional<Double> extensionMeters, ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        this.extensionMeters = extensionMeters;
    }

    public ExtendToLengthCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        this.extensionMeters = Optional.empty();
    }

    @Override
    public void initialize() {
        super.initialize();
        if (extensionMeters.isPresent()){
            armSubsystem.setDesiredExtension(extensionMeters.get());
        } else {
            armSubsystem.setExtensionHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.getExtension() - armSubsystem.getDesiredExtensionRotations())
                < 0.05;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
