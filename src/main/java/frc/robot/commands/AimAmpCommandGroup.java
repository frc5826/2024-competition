package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class AimAmpCommandGroup extends SequentialCommandGroup {

    public AimAmpCommandGroup(LegSubsystem legSubsystem) {
        addCommands(
                new RotateArmToAngleCommand(Math.toRadians(15), legSubsystem),
                new ExtendToLengthCommand(0.18, legSubsystem),
                new RotateArmToAngleCommand(Math.toRadians(100), legSubsystem),
                new RotateAnkleCommand(Optional.empty(), legSubsystem)
        );
    }
}
