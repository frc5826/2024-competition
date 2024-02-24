package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

public class IntakeSequenceCommandGroup extends SequentialCommandGroup {

    public IntakeSequenceCommandGroup(ArmSubsystem armSubsystem) {
        addRequirements(armSubsystem);
        addCommands(
            new RotateArmToAngleCommand(Math.toRadians(45), armSubsystem),
            new RotateWristCommand(Math.toRadians(-30), armSubsystem),
            new ParallelCommandGroup(
                new ExtendToLengthCommand(0.23, armSubsystem),
                new RotateWristCommand(Math.toRadians(-80), armSubsystem)
            ),
            new RotateArmToAngleCommand(Math.toRadians(5), armSubsystem)
        );
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
//TODO jump wyatt