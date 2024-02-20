package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeSequenceCommandGroup extends SequentialCommandGroup {

    public IntakeSequenceCommandGroup(ElevatorSubsystem elevatorSubsystem) {
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(45), elevatorSubsystem),
                new WaitCommand(5),
                new RotateAnkleCommand(Math.toRadians(-70), elevatorSubsystem),
                new WaitCommand(5),
                new ExtendToLengthCommand(0.28, elevatorSubsystem),
                new WaitCommand(5),
                new RotateArmToAngleCommand(Math.toRadians(15), elevatorSubsystem)
        );
    }
}
