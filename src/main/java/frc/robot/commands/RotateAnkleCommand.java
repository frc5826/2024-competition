package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class RotateAnkleCommand extends Command {

    private double angle;
    private ElevatorSubsystem elevatorSubsystem;

    public RotateAnkleCommand(double angle, ElevatorSubsystem elevatorSubsystem) {
        this.angle = angle;
        this.elevatorSubsystem = elevatorSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        elevatorSubsystem.setDesiredAnkleAngle(angle);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elevatorSubsystem.setAnkleHome();
    }
}
