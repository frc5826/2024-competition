package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    private LocalizationSubsystem localizationSubsystem;
    private SwerveSubsystem swerveSubsystem;
    private ArmSubsystem armSubsystem;
    private ShooterSubsystem shooterSubsystem;

    private boolean holdingRing;

    private int ringCount;

    public AutoCommandGroup(LocalizationSubsystem localizationSubsystem, SwerveSubsystem swerveSubsystem,
                            ArmSubsystem armSubsystem, ShooterSubsystem shooterSubsystem,
                            int ringCount, Pose2d endPose, Pose2d... rings) {

        this.armSubsystem = armSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        this.ringCount = ringCount;

        addCommands(new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem),
                //new AimSpeakerCommandGroup(armSubsystem),
                new HomeSequenceCommandGroup(armSubsystem),
                new NoteShootCommandGroup(shooterSubsystem).deadlineWith());

        for(int i = 0; i < ringCount; i++) {
            Pose2d ring = rings[i];
            if (ring != Constants.nothingPose) {
                addCommands(
                        new PathWithStopDistance(localizationSubsystem, ring, 1.25, false)
                                .onlyIf(() -> ring.getTranslation().getDistance(localizationSubsystem.getCurrentPose().getTranslation()) > 2),
                        new TurnToCommand(localizationSubsystem, swerveSubsystem, ring),
                        new AutoPickupRingSequence(armSubsystem, shooterSubsystem,
                                localizationSubsystem, swerveSubsystem),
                        Commands.sequence(
                                localizationSubsystem.buildPath(Constants.cSpeakerPark),
                                new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem)/*.onlyIf(() -> shooterSubsystem.getHasRing()*/),
                                //new AimSpeakerCommandGroup(armSubsystem),
                                new NoteShootCommandGroup(shooterSubsystem));
            }
        }
        //end pose
        addCommands(localizationSubsystem.buildPath(endPose));
    }


}
