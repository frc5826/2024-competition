package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LegSubsystem;

import static frc.robot.Constants.*;

public class ElevatorManualCommand extends Command {

    LegSubsystem legSubsystem;
    ArmControlMode armControlMode;

    public ElevatorManualCommand(LegSubsystem legSubsystem, ArmControlMode armControlMode) {
        this.legSubsystem = legSubsystem;
        this.armControlMode = armControlMode;
    }

    @Override
    public void execute() {
        super.execute();
        switch (armControlMode){
            case ROTATE -> legSubsystem.setRotateSpeed(joystick.getRawAxis(3));
            case EXTEND -> legSubsystem.setExtendSpeed(joystick.getRawAxis(3));
            case ANKLE -> legSubsystem.setAnkleSpeed(joystick.getRawAxis(3));
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        switch (armControlMode){
            case ROTATE -> legSubsystem.setRotateSpeed(0);
            case EXTEND -> legSubsystem.setExtendSpeed(0);
            case ANKLE -> legSubsystem.setAnkleSpeed(0);
        }
    }

    public enum ArmControlMode{
        ROTATE,
        EXTEND,
        ANKLE
    }

}
