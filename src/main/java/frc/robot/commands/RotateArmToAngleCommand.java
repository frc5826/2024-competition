package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class RotateArmToAngleCommand extends Command {

    private Optional<Double> angle;
    private ElevatorSubsystem elevatorSubsystem;

    public RotateArmToAngleCommand(double angle, ElevatorSubsystem elevatorSubsystem) {
        this.angle = Optional.of(angle);
        this.elevatorSubsystem = elevatorSubsystem;
    }

    public RotateArmToAngleCommand(Optional<Double> angle, ElevatorSubsystem elevatorSubsystem) {
        this.angle = angle;
        this.elevatorSubsystem = elevatorSubsystem;
    }

    public RotateArmToAngleCommand(ElevatorSubsystem elevatorSubsystem) {
        this.angle = Optional.empty();
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            elevatorSubsystem.setDesiredArmAngle(angle.get());
        } else {
            elevatorSubsystem.setArmHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(elevatorSubsystem.getRotation() - elevatorSubsystem.getDesiredArmRotations())
                < 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
