package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

import static frc.robot.Constants.*;

public class ElevatorManualCommand extends Command {

    ElevatorSubsystem elevatorSubsystem;
    ArmControlMode armControlMode;

    public ElevatorManualCommand(ElevatorSubsystem elevatorSubsystem, ArmControlMode armControlMode) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.armControlMode = armControlMode;
    }

    @Override
    public void execute() {
        super.execute();
        switch (armControlMode){
            case ROTATE -> elevatorSubsystem.setRotateSpeed(joystick.getRawAxis(3));
            case EXTEND -> elevatorSubsystem.setExtendSpeed(joystick.getRawAxis(3));
            case ANKLE -> elevatorSubsystem.setAnkleSpeed(joystick.getRawAxis(3));
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        switch (armControlMode){
            case ROTATE -> elevatorSubsystem.setRotateSpeed(0);
            case EXTEND -> elevatorSubsystem.setExtendSpeed(0);
            case ANKLE -> elevatorSubsystem.setAnkleSpeed(0);
        }
    }

    public enum ArmControlMode{
        ROTATE,
        EXTEND,
        ANKLE
    }

}
