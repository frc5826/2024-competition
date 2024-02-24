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

    private int ringCount;

    public AutoCommandGroup(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem, int ringCount, Pose2d endPose, Pose2d... rings) {
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        this.ringCount = ringCount;

        addCommands(new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem));

        for(int i = 0; i < ringCount; i++) {
            Pose2d ring = rings[i];
            if (ring != Constants.nothingPose) {
                addCommands(
                        new PathWithStopDistance(localizationSubsystem, ring, 1.25)
                                .onlyIf(() -> ring.getTranslation().getDistance(localizationSubsystem.getCurrentPose().getTranslation()) > 2),
                        new TurnToCommand(localizationSubsystem, swerveSubsystem, ring),
                        new PickupRing(localizationSubsystem, swerveSubsystem).finallyDo( () -> {
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
        //end pose
        addCommands(localizationSubsystem.buildPath(endPose));
    }


}
