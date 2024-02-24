package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class HomeSequenceCommandGroup extends SequentialCommandGroup {

    public HomeSequenceCommandGroup(ArmSubsystem armSubsystem){
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), armSubsystem),
            new ParallelCommandGroup(
                new RotateAnkleCommand(Optional.empty(), armSubsystem),
                new ExtendToLengthCommand(Optional.empty(), armSubsystem)
            ),
            new RotateArmToAngleCommand(Optional.empty(), armSubsystem)
        );
    }

}