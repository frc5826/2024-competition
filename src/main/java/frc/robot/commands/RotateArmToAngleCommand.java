package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class RotateArmToAngleCommand extends Command {

    private Optional<Double> angle;
    private ArmSubsystem armSubsystem;

    public RotateArmToAngleCommand(double angle, ArmSubsystem armSubsystem) {
        this.angle = Optional.of(angle);
        this.armSubsystem = armSubsystem;
    }

    public RotateArmToAngleCommand(Optional<Double> angle, ArmSubsystem armSubsystem) {
        this.angle = angle;
        this.armSubsystem = armSubsystem;
    }

    public RotateArmToAngleCommand(ArmSubsystem armSubsystem) {
        this.angle = Optional.empty();
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            armSubsystem.setDesiredArmAngle(angle.get());
        } else {
            armSubsystem.setArmHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.getRotation() - armSubsystem.getDesiredArmRotations())
                < 0.04;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
