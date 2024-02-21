package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class RotateAnkleCommand extends Command {

    private Optional<Double> angle;
    private ElevatorSubsystem elevatorSubsystem;

    public RotateAnkleCommand(double angle, ElevatorSubsystem elevatorSubsystem) {
        this.angle = Optional.of(angle);
        this.elevatorSubsystem = elevatorSubsystem;
    }

    public RotateAnkleCommand(Optional<Double> angle, ElevatorSubsystem elevatorSubsystem) {
        this.angle = angle;
        this.elevatorSubsystem = elevatorSubsystem;
    }

    public RotateAnkleCommand(ElevatorSubsystem elevatorSubsystem) {
        this.angle = Optional.empty();
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            elevatorSubsystem.setDesiredAnkleAngle(angle.get());
        } else {
            elevatorSubsystem.setAnkleHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(elevatorSubsystem.getAnkle() - elevatorSubsystem.getDesiredAnkleRotations())
                < 0.01;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
