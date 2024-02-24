package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

public class AimSpeakerCommandGroup extends SequentialCommandGroup {

    public AimSpeakerCommandGroup(ArmSubsystem armSubsystem) {
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(45), armSubsystem),
                new RotateWristCommand(Math.toRadians(85), armSubsystem)
        );
    }
}
