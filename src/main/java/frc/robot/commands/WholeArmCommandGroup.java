package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class WholeArmCommandGroup extends SequentialCommandGroup {

    public WholeArmCommandGroup(ElevatorSubsystem elevatorSubsystem, Optional<Double> armAngle, Optional<Double> extensionMeters, Optional<Double> ankleAngle, MoveFirst order) {
        switch (order){
            case ROTATE:
                addCommands(
                    new RotateArmToAngleCommand(armAngle, elevatorSubsystem),
                    new WaitCommand(5),
                    new ExtendToLengthCommand(extensionMeters, elevatorSubsystem),
                    new WaitCommand(5),
                    new RotateAnkleCommand(ankleAngle, elevatorSubsystem)
                );
                break;
            case EXTEND:
                addCommands(
                    new ExtendToLengthCommand(extensionMeters, elevatorSubsystem),
                    new WaitCommand(5),
                    new RotateArmToAngleCommand(armAngle, elevatorSubsystem),
                    new WaitCommand(5),
                    new RotateAnkleCommand(ankleAngle, elevatorSubsystem)
                );
                break;
            case TOP_DOWN:
                //TODO MAKE THIS
                break;
        }
    }

    public enum MoveFirst{
        ROTATE,
        EXTEND,
        TOP_DOWN
    }

}
