package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {

    private ShooterSubsystem shooterSubsystem;
    private ShooterType shooterType;
    private double speed;
    private boolean top;
    private boolean bottom;

    public ShooterCommand(ShooterSubsystem shooterSubsystem, double speed ,ShooterType shooterType, boolean top, boolean bottom) {
        this.shooterSubsystem = shooterSubsystem;
        this.shooterType = shooterType;
        this.speed = speed;
        this.top = top;
        this.bottom = bottom;
    }

    public ShooterCommand(ShooterSubsystem shooterSubsystem, double speed ,ShooterType shooterType) {
        this(shooterSubsystem, speed, shooterType, true, true);
    }

        @Override
    public void initialize() {
        super.initialize();
        switch (shooterType){
            case POWER -> shooterSubsystem.setShooterOutput(speed, top, bottom);
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
