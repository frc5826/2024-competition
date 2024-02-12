package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

import static frc.robot.Constants.*;

public class ShooterCommand extends Command {

    ShooterSubsystem shooterSubsystem;
    ShooterType shooterType;

    public ShooterCommand(ShooterSubsystem shooterSubsystem, ShooterType shooterType) {
        this.shooterSubsystem = shooterSubsystem;
        this.shooterType = shooterType;
    }

    @Override
    public void execute() {
        super.execute();
        switch (shooterType){
            case POWER -> shooterSubsystem.setShooterSpeed(joystick.getRawAxis(3));
            case CONTROL -> shooterSubsystem.setShooterControlSpeed(joystick.getRawAxis(3));
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        switch (shooterType){
            case POWER -> shooterSubsystem.setShooterSpeed(0);
            case CONTROL -> shooterSubsystem.setShooterControlSpeed(0);
        }
    }

    public enum ShooterType{
        POWER,
        CONTROL
    }

}
