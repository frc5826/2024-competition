package frc.robot.commands.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LocalizationSubsystem;

public class TopCloseRing1 extends Command {
    private LocalizationSubsystem localizationSubsystem;
    public TopCloseRing1(LocalizationSubsystem localizationSubsystem) {
        this.localizationSubsystem = localizationSubsystem;

        addRequirements(localizationSubsystem);
    }

    @Override
    public void execute() {
        localizationSubsystem.buildPath(new Pose2d(2.2, 7, Rotation2d.fromDegrees(180)));
    }
}
