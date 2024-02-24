package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class ThreeAngleCommand extends Command {

    private ArmSubsystem armSubsystem;

    private Optional<Double> armAngle;
    private Optional<Double> armLength;
    private Optional<Double> ankleAngle;

    public ThreeAngleCommand(ArmSubsystem armSubsystem, Optional<Double> armAngle, Optional<Double> armLength, Optional<Double> ankleAngle) {
        this.armSubsystem = armSubsystem;
        this.armAngle = armAngle;
        this.armLength = armLength;
        this.ankleAngle = ankleAngle;
    }

    @Override
    public void initialize() {
        super.initialize();
        armAngle.ifPresent(aDouble -> armSubsystem.setDesiredArmAngle(aDouble));
        armLength.ifPresent(aDouble -> armSubsystem.setDesiredExtension(aDouble));
        ankleAngle.ifPresent(aDouble -> armSubsystem.setDesiredWristAngle(aDouble));
    }
}
