package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EsclatorSubsystem;

public class ClimberCommand extends Command {

    private EsclatorSubsystem esclatorSubsystem;
    private double speed;

    public ClimberCommand(EsclatorSubsystem esclatorSubsystem, double speed) {
        this.esclatorSubsystem = esclatorSubsystem;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        super.initialize();
        esclatorSubsystem.setMotorSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        esclatorSubsystem.setMotorSpeed(0);
    }
}
