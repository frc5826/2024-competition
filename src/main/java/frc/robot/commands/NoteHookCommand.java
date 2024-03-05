package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class NoteHookCommand extends Command {

    ShooterSubsystem shooterSubsystem;

    public NoteHookCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        shooterSubsystem.setShooterOutput(-0.3);
    }

    @Override
    public boolean isFinished() {
        return !shooterSubsystem.getBeamBreak();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.setShooterOutput(0);
    }
}
