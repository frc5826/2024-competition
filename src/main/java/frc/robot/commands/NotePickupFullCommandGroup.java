package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NotePickupFullCommandGroup extends SequentialCommandGroup {

    public NotePickupFullCommandGroup(ArmSubsystem armSubsystem, ShooterSubsystem shooterSubsystem) {
        addRequirements(armSubsystem, shooterSubsystem);
        addCommands(
                new IntakeSequenceCommandGroup(armSubsystem),
                new NoteHookCommand(shooterSubsystem),
                new ParallelCommandGroup(
                    new HomeSequenceCommandGroup(armSubsystem),
                    new NoteLoadCommand(shooterSubsystem)
                )
        );
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}