package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class RotateAnkleCommand extends Command {

    private Optional<Double> angle;
    private ArmSubsystem armSubsystem;

    public RotateAnkleCommand(double angle, ArmSubsystem armSubsystem) {
        this.angle = Optional.of(angle);
        this.armSubsystem = armSubsystem;
    }

    public RotateAnkleCommand(Optional<Double> angle, ArmSubsystem armSubsystem) {
        this.angle = angle;
        this.armSubsystem = armSubsystem;
    }

    public RotateAnkleCommand(ArmSubsystem armSubsystem) {
        this.angle = Optional.empty();
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            armSubsystem.setDesiredWristAngle(angle.get());
        } else {
            armSubsystem.setWristHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(armSubsystem.getWrist() - armSubsystem.getDesiredWristRotations())
                < 0.01;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
