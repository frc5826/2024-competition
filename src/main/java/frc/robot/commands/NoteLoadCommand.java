package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class NoteLoadCommand extends Command {

    private ShooterSubsystem shooterSubsystem;
    private boolean isEscaped = true;


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
    public void execute() {
        super.execute();
        if (shooterSubsystem.getBeamBreak() && !isEscaped){
            isEscaped = true;
            shooterSubsystem.setShooterSpeed(0);
            shooterSubsystem.setShooterControlSpeed(0.3);
        }
    }

    @Override
    public boolean isFinished() {
        return isEscaped && !shooterSubsystem.getBeamBreak();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.setShooterSpeed(0);
        shooterSubsystem.setShooterControlSpeed(0);
    }
}
