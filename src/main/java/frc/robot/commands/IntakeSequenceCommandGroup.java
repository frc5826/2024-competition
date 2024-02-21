package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LegSubsystem;

public class IntakeSequenceCommandGroup extends SequentialCommandGroup {

    public IntakeSequenceCommandGroup(LegSubsystem legSubsystem) {
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), legSubsystem),
            new RotateAnkleCommand(Math.toRadians(-30), legSubsystem),
            new ParallelCommandGroup(
                new ExtendToLengthCommand(0.23, legSubsystem),
                new RotateAnkleCommand(Math.toRadians(-80), legSubsystem)
            ),
            new RotateArmToAngleCommand(Math.toRadians(5), legSubsystem)
        );
    }
}
