package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class NoteLoadCommand extends Command {

    ShooterSubsystem shooterSubsystem;

    public NoteLoadCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        shooterSubsystem.setShooterSpeed(-0.2);
        shooterSubsystem.setShooterControlSpeed(-0.3);
    }

    @Override
    public boolean isFinished() {
        return shooterSubsystem.getBeamBreak();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.setShooterSpeed(0);
        shooterSubsystem.setShooterControlSpeed(0);
    }
}
