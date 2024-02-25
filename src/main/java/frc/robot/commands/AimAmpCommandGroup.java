package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

import java.util.Optional;

public class AimAmpCommandGroup extends SequentialCommandGroup {

    public AimAmpCommandGroup(ArmSubsystem armSubsystem) {
        addRequirements(armSubsystem);
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(20), armSubsystem),
                new ExtendToLengthCommand(0.25, armSubsystem),
                new RotateArmToAngleCommand(Math.toRadians(100), armSubsystem),
                new RotateWristCommand(Optional.empty(), armSubsystem)
        );
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
