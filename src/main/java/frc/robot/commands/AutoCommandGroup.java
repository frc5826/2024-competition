package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.LocalizationSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    private LocalizationSubsystem localizationSubsystem;

    public AutoCommandGroup(LocalizationSubsystem localizationSubsystem) {
        this.localizationSubsystem = localizationSubsystem;
        addCommands(
                localizationSubsystem.buildPath(Constants.cAmpPark),
                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                localizationSubsystem.buildPath(Constants.cRightStagePark),
                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                localizationSubsystem.buildPath(Constants.cAmpPark),
                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                localizationSubsystem.buildPath(Constants.cRightStagePark),
                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                localizationSubsystem.buildPath(Constants.cAmpPark),
                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                localizationSubsystem.buildPath(Constants.cRightStagePark)

        );
    }


}
