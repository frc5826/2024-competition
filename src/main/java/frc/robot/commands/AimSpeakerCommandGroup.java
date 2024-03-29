package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class AimSpeakerCommandGroup extends SequentialCommandGroup {

    public AimSpeakerCommandGroup(ArmSubsystem armSubsystem) {
        addRequirements(armSubsystem);
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(45), armSubsystem),
                new ParallelCommandGroup(
                    new ExtendToLengthCommand(Optional.empty(), armSubsystem),
                    new RotateWristCommand(Math.toRadians(87), armSubsystem)
                )
        );
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
