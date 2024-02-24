package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends Command {

    private ClimberSubsystem climberSubsystem;
    private double speed;

    public ClimberCommand(ClimberSubsystem climberSubsystem, double speed) {
        this.climberSubsystem = climberSubsystem;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();
        climberSubsystem.setMotorSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        climberSubsystem.setMotorSpeed(0);
    }
}
