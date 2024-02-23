package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeTestCommand extends Command {

    private ShooterSubsystem shooterSubsystem;
    private double speed;


    public IntakeTestCommand(ShooterSubsystem shooterSubsystem, double speed) {
        this.shooterSubsystem = shooterSubsystem;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();
        shooterSubsystem.getShooterControlMotor().set(speed);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.getShooterControlMotor().set(0);
    }
}
