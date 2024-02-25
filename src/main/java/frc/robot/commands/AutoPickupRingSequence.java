package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import static edu.wpi.first.wpilibj2.command.Commands.*;

public class AutoPickupRingSequence extends SequentialCommandGroup {

    public AutoPickupRingSequence(ArmSubsystem armSubsystem, ShooterSubsystem shooterSubsystem,
                                  LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem) {
        addCommands(
                new IntakeSequenceCommandGroup(armSubsystem),
                new ParallelDeadlineGroup(
                    new NoteHookCommand(shooterSubsystem),
                    new AutoPickupRing(localizationSubsystem, swerveSubsystem)
                ),
                new ParallelCommandGroup(
                        new HomeSequenceCommandGroup(armSubsystem),
                        new NoteLoadCommand(shooterSubsystem)
                )
        );

    }

}
