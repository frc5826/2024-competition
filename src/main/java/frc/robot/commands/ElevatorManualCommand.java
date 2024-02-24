package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

import static frc.robot.Constants.*;

public class ElevatorManualCommand extends Command {

    ArmSubsystem armSubsystem;
    ArmControlMode armControlMode;

    public ElevatorManualCommand(ArmSubsystem armSubsystem, ArmControlMode armControlMode) {
        this.armSubsystem = armSubsystem;
        this.armControlMode = armControlMode;
    }

    @Override
    public void execute() {
        super.execute();
        switch (armControlMode){
            case ROTATE -> armSubsystem.setRotateSpeed(joystick.getRawAxis(3));
            case EXTEND -> armSubsystem.setExtendSpeed(joystick.getRawAxis(3));
            case ANKLE -> armSubsystem.setWristSpeed(joystick.getRawAxis(3));
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        switch (armControlMode){
            case ROTATE -> armSubsystem.setRotateSpeed(0);
            case EXTEND -> armSubsystem.setExtendSpeed(0);
            case ANKLE -> armSubsystem.setWristSpeed(0);
        }
    }

    public enum ArmControlMode{
        ROTATE,
        EXTEND,
        ANKLE
    }

}
