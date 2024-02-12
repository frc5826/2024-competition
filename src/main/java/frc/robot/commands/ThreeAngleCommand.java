package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class ThreeAngleCommand extends Command {

    private ElevatorSubsystem elevatorSubsystem;

    private Optional<Double> armAngle;
    private Optional<Double> armLength;
    private Optional<Double> ankleAngle;

    public ThreeAngleCommand(ElevatorSubsystem elevatorSubsystem, Optional<Double> armAngle, Optional<Double> armLength, Optional<Double> ankleAngle) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armAngle = armAngle;
        this.armLength = armLength;
        this.ankleAngle = ankleAngle;
    }

    @Override
    public void initialize() {
        super.initialize();
        armAngle.ifPresent(aDouble -> elevatorSubsystem.setDesiredArmAngle(aDouble));
        armLength.ifPresent(aDouble -> elevatorSubsystem.setDesiredExtension(aDouble));
        ankleAngle.ifPresent(aDouble -> elevatorSubsystem.setDesiredAnkleAngle(aDouble));
    }
}
