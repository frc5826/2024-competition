package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LegSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class NotePickupFullCommandGroup extends SequentialCommandGroup {

    public NotePickupFullCommandGroup(LegSubsystem legSubsystem, ShooterSubsystem shooterSubsystem) {
        addCommands(
                new IntakeSequenceCommandGroup(legSubsystem),
                new NoteHookCommand(shooterSubsystem),
                new HomeSequenceCommandGroup(legSubsystem),
                new NoteLoadCommand(shooterSubsystem)
        );
    }
}