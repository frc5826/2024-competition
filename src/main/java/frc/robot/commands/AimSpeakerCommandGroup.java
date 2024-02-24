package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LegSubsystem;

public class AimSpeakerCommandGroup extends SequentialCommandGroup {

    public AimSpeakerCommandGroup(LegSubsystem legSubsystem) {
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(45), legSubsystem),
                new RotateAnkleCommand(Math.toRadians(85), legSubsystem)
        );
    }
}
