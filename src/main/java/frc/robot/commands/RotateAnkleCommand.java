package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class RotateAnkleCommand extends Command {

    private Optional<Double> angle;
    private LegSubsystem legSubsystem;

    public RotateAnkleCommand(double angle, LegSubsystem legSubsystem) {
        this.angle = Optional.of(angle);
        this.legSubsystem = legSubsystem;
    }

    public RotateAnkleCommand(Optional<Double> angle, LegSubsystem legSubsystem) {
        this.angle = angle;
        this.legSubsystem = legSubsystem;
    }

    public RotateAnkleCommand(LegSubsystem legSubsystem) {
        this.angle = Optional.empty();
        this.legSubsystem = legSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            legSubsystem.setDesiredAnkleAngle(angle.get());
        } else {
            legSubsystem.setAnkleHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(legSubsystem.getAnkle() - legSubsystem.getDesiredAnkleRotations())
                < 0.01;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
