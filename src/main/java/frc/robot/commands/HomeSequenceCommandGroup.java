package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class HomeSequenceCommandGroup extends SequentialCommandGroup {

    public HomeSequenceCommandGroup(LegSubsystem legSubsystem){
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), legSubsystem),
            new ParallelCommandGroup(
                new RotateAnkleCommand(Optional.empty(), legSubsystem),
                new ExtendToLengthCommand(Optional.empty(), legSubsystem)
            ),
            new RotateArmToAngleCommand(Optional.empty(), legSubsystem)
        );
    }

}
