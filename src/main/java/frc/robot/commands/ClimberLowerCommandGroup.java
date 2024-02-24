package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberLowerCommandGroup extends ParallelCommandGroup {

    public ClimberLowerCommandGroup(ClimberSubsystem climberSubsystem){
        addRequirements(climberSubsystem);
        addCommands(
                new ClimberLowerSideCommand(climberSubsystem, ClimberLowerSideCommand.ClimberSide.LEFT),
                new ClimberLowerSideCommand(climberSubsystem, ClimberLowerSideCommand.ClimberSide.RIGHT)
        );
    }

}
