package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberRaiseCommandGroup extends ParallelCommandGroup {

    public ClimberRaiseCommandGroup(ClimberSubsystem climberSubsystem){
        addRequirements(climberSubsystem);
        addCommands(
                new ClimberRaiseSideCommand(climberSubsystem, ClimberRaiseSideCommand.ClimberSide.LEFT),
                new ClimberRaiseSideCommand(climberSubsystem, ClimberRaiseSideCommand.ClimberSide.RIGHT)
        );
    }

}
