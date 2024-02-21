package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.Optional;

public class HomeSequenceCommandGroup extends SequentialCommandGroup {

    public HomeSequenceCommandGroup(ElevatorSubsystem elevatorSubsystem){
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), elevatorSubsystem),
            new ParallelCommandGroup(
                new RotateAnkleCommand(Optional.empty(), elevatorSubsystem),
                new ExtendToLengthCommand(Optional.empty(), elevatorSubsystem)
            ),
            new RotateArmToAngleCommand(Optional.empty(), elevatorSubsystem)
        );
    }

}
