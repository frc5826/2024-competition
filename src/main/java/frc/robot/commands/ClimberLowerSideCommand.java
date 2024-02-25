package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberLowerSideCommand extends Command {

    private ClimberSubsystem climberSubsystem;
    private ClimberSide climberSide;
    private boolean escapedTop;

    public ClimberLowerSideCommand(ClimberSubsystem climberSubsystem, ClimberSide climberSide) {
        this.climberSubsystem = climberSubsystem;
        this.climberSide = climberSide;

        this.escapedTop = false;
    }

    @Override
    public void initialize() {
        super.initialize();
        switch (climberSide){
            case LEFT -> climberSubsystem.setLeftMotorSpeed(-0.90);
            case RIGHT -> climberSubsystem.setRightMotorSpeed(-0.90);
        }
    }

    @Override
    public void execute() {
        super.execute();
        switch (climberSide){
            case LEFT -> {
                if (climberSubsystem.getLeftLimitSwitch()){
                    escapedTop = true;
                }
            }
            case RIGHT -> {
                if (climberSubsystem.getRightLimitSwitch()){
                    escapedTop = true;
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        switch (climberSide){
            case LEFT -> {
                return escapedTop && !climberSubsystem.getLeftLimitSwitch();
            }
            case RIGHT -> {
                return escapedTop && !climberSubsystem.getRightLimitSwitch();
            }
        }
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        switch (climberSide){
            case LEFT -> climberSubsystem.setLeftMotorSpeed(0);
            case RIGHT -> climberSubsystem.setRightMotorSpeed(0);
        }
        escapedTop = false;
    }

    public enum ClimberSide{
        LEFT,
        RIGHT,
    }
}
