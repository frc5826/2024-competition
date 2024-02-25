package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class NoteShootCommandGroup extends SequentialCommandGroup {

    public NoteShootCommandGroup(ShooterSubsystem shooterSubsystem) {
        addRequirements(shooterSubsystem);
        addCommands(
                new ShooterCommand(shooterSubsystem, 1, ShooterCommand.ShooterType.POWER),
                new WaitCommand(1),
                new ShooterCommand(shooterSubsystem, 1, ShooterCommand.ShooterType.CONTROL),
                new WaitCommand(1),
                new ShooterCommand(shooterSubsystem, 0, ShooterCommand.ShooterType.POWER),
                new ShooterCommand(shooterSubsystem, 0, ShooterCommand.ShooterType.CONTROL),
                new InstantCommand(() -> shooterSubsystem.setHasRing(false))
        );
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
