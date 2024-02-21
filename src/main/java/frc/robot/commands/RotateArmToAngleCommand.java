package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class RotateArmToAngleCommand extends Command {

    private Optional<Double> angle;
    private LegSubsystem legSubsystem;

    public RotateArmToAngleCommand(double angle, LegSubsystem legSubsystem) {
        this.angle = Optional.of(angle);
        this.legSubsystem = legSubsystem;
    }

    public RotateArmToAngleCommand(Optional<Double> angle, LegSubsystem legSubsystem) {
        this.angle = angle;
        this.legSubsystem = legSubsystem;
    }

    public RotateArmToAngleCommand(LegSubsystem legSubsystem) {
        this.angle = Optional.empty();
        this.legSubsystem = legSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (angle.isPresent()) {
            legSubsystem.setDesiredArmAngle(angle.get());
        } else {
            legSubsystem.setArmHome();
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(legSubsystem.getRotation() - legSubsystem.getDesiredArmRotations())
                < 0.01;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
