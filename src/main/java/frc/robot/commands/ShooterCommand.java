package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

import static frc.robot.Constants.*;

public class ShooterCommand extends Command {

    private ShooterSubsystem shooterSubsystem;
    private ShooterType shooterType;
    private double speed;

    public ShooterCommand(ShooterSubsystem shooterSubsystem, double speed ,ShooterType shooterType) {
        this.shooterSubsystem = shooterSubsystem;
        this.shooterType = shooterType;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();
        switch (shooterType){
            case POWER -> shooterSubsystem.setShooterSpeed(speed);
            case CONTROL -> shooterSubsystem.setShooterControlSpeed(speed);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    //    @Override
//    public void end(boolean interrupted) {
//        super.end(interrupted);
//        switch (shooterType){
//            case POWER -> shooterSubsystem.setShooterSpeed(0);
//            case CONTROL -> shooterSubsystem.setShooterControlSpeed(0);
//        }
//    }

    public enum ShooterType{
        POWER,
        CONTROL
    }

}
