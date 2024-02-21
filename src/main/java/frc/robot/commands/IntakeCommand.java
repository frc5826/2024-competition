package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeCommand extends Command {

    private ShooterSubsystem shooterSubsystem;

    public IntakeCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() {
        super.initialize();
        shooterSubsystem.setShooterSpeed(1);
    }

    @Override
    public boolean isFinished() {
        return !shooterSubsystem.getBeamBreak();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.setShooterSpeed(0);
    }
}
