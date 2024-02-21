package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class IntakeSequenceCommandGroup extends SequentialCommandGroup {

    public IntakeSequenceCommandGroup(ElevatorSubsystem elevatorSubsystem) {
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), elevatorSubsystem),
            new RotateAnkleCommand(Math.toRadians(-30), elevatorSubsystem),
            new ParallelCommandGroup(
                new ExtendToLengthCommand(0.23, elevatorSubsystem),
                new RotateAnkleCommand(Math.toRadians(-80), elevatorSubsystem)
            ),
            new RotateArmToAngleCommand(Math.toRadians(5), elevatorSubsystem)
        );
    }
}
