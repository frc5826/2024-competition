package frc.robot.commands.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LocalizationSubsystem;

public class AutoRings extends Command {
    private LocalizationSubsystem localizationSubsystem;
    public AutoRings(LocalizationSubsystem localizationSubsystem) {
        this.localizationSubsystem = localizationSubsystem;

        addRequirements(localizationSubsystem);
    }

    public Command TopCloseRing1() {
        return localizationSubsystem.buildPath(Constants.TopCloseRing1);
    }

    public Command MidCloseRing2() {
        return localizationSubsystem.buildPath(Constants.MidCloseRing2);
    }

    public Command BottomCloseRing3() {
        return localizationSubsystem.buildPath(Constants.BotCloseRing3);
    }

    @Override
    public void execute() {

    }
}
