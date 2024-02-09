package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import java.util.List;

public class AutoCommandGroup extends SequentialCommandGroup {

    private LocalizationSubsystem localizationSubsystem;
    private SwerveSubsystem swerveSubsystem;

    private boolean holdingRing;

    public AutoCommandGroup(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem, Pose2d... rings) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        addCommands(new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem));

        for(Pose2d ring : rings) {
            addCommands(
                    new PathWithStopDistance(localizationSubsystem, ring, 1),
                    new PickupRing(localizationSubsystem, swerveSubsystem).finallyDo( () -> {
                        //TODO: next time add a command after to just turn to the position
                        if (localizationSubsystem.getBestPickupRing().getFieldPose().equals(new Translation2d(0, 0))) {
                            holdingRing = false; }
                        else {holdingRing = true;} }
                    ),
                    Commands.sequence(
                            localizationSubsystem.buildPath(Constants.cSpeakerPark),
                            new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem)).onlyIf(() -> holdingRing)

            );
        }
    }


}
