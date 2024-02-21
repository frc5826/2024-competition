package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class ThreeAngleCommand extends Command {

    private LegSubsystem legSubsystem;

    private Optional<Double> armAngle;
    private Optional<Double> armLength;
    private Optional<Double> ankleAngle;

    public ThreeAngleCommand(LegSubsystem legSubsystem, Optional<Double> armAngle, Optional<Double> armLength, Optional<Double> ankleAngle) {
        this.legSubsystem = legSubsystem;
        this.armAngle = armAngle;
        this.armLength = armLength;
        this.ankleAngle = ankleAngle;
    }

    @Override
    public void initialize() {
        super.initialize();
        armAngle.ifPresent(aDouble -> legSubsystem.setDesiredArmAngle(aDouble));
        armLength.ifPresent(aDouble -> legSubsystem.setDesiredExtension(aDouble));
        ankleAngle.ifPresent(aDouble -> legSubsystem.setDesiredAnkleAngle(aDouble));
    }
}
