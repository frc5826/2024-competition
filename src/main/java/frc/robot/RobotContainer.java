// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.*;
import frc.robot.commands.TargetSpeakerCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.commands.PathWithStopDistance;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.io.File;

import static frc.robot.Constants.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

    private final XboxController xbox = new XboxController(1);

    private final LegSubsystem legSubsystem = new LegSubsystem();

    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    private final VisionSubsystem visionSubsystem = new VisionSubsystem();

    private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem(
            new File(Filesystem.getDeployDirectory() + "/swerve"));

    private final LocalizationSubsystem localizationSubsystem = new LocalizationSubsystem(visionSubsystem, swerveSubsystem);

    private final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(
            swerveSubsystem, localizationSubsystem,() ->-xbox.getLeftY(), ()->-xbox.getLeftX(), ()->-xbox.getRightX(), () -> xbox.getXButton());

    private final TargetSpeakerCommand targetSpeakerCommand = new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem);

    private final PickupRingTest pickupRingTest = new PickupRingTest(visionSubsystem, swerveSubsystem, localizationSubsystem);

    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {

        //Button panel bindings //TODO put arm presets n stuff here
        panelButtons[0].onTrue(null);
        panelButtons[1].onTrue(null);
        panelButtons[2].onTrue(null);
        panelButtons[3].onTrue(null);
        panelButtons[4].onTrue(null);
        panelButtons[5].onTrue(null);
        panelButtons[6].onTrue(null);
        panelButtons[7].onTrue(null);
        panelButtons[8].onTrue(null);
        panelButtons[9].onTrue(null);
        panelButtons[10].onTrue(null);
        panelButtons[11].onTrue(null);

        //Xbox button bindings

        new Trigger(xbox::getAButton).whileTrue(new PickupRing(localizationSubsystem, swerveSubsystem));

        new Trigger(xbox::getXButton).whileTrue(localizationSubsystem.buildPath(cPickupPark));

        new Trigger(xbox::getYButton).whileTrue(new PathWithStopDistance(localizationSubsystem, cSpeakerPose, 2.5));

        new Trigger(xbox::getBButton).whileTrue(localizationSubsystem.buildPath(cAmpPark));

        new Trigger(() -> xbox.getPOV() == 0).or(() -> xbox.getPOV() == 180).whileTrue(localizationSubsystem.buildPath(cCenterStagePark));

        new Trigger(() -> xbox.getPOV() == 90).whileTrue(localizationSubsystem.buildPath(cRightStagePark));

        new Trigger(() -> xbox.getPOV() == 270).whileTrue(localizationSubsystem.buildPath(cLeftStagePark));

        //TODO find way to zero gyro that doesnt reset pose
//        new Trigger(xbox::getAButtonPressed)
//                .onTrue(new InstantCommand(() ->
//                {
//                    //Need to be run in this order
//                    swerveSubsystem.zeroGyro();
//                    localizationSubsystem.reset();
//                }));
//        new Trigger(()-> xbox.getPOV() == 0).onTrue(
//                new InstantCommand(() ->
//                {
//                    swerveSubsystem.zeroGyro();
//                    swerveSubsystem.zeroOdometry();
//                    localizationSubsystem.reset();
//                }));

        //new Trigger(xbox::getYButton).whileTrue(new AutoRings(localizationSubsystem, swerveSubsystem, new Pose2d(2, 0, Rotation2d.fromRadians(0))));
        //test auto
        //new Trigger(xbox::getYButton).whileTrue(new AutoCommandGroup(localizationSubsystem, swerveSubsystem, Constants.cTopCloseRing1, Constants.cBotCloseRing3, Constants.cFarRing7));
        //new Trigger(xbox::getXButton).whileTrue(targetSpeakerCommand);
        //new Trigger(xbox::getBButton).whileTrue(pickupRingTest);
        //all subject to changed keybinds
        //new Trigger(()-> xbox.getPOV() == 90).whileTrue(
        //        localizationSubsystem.buildPath(Constants.cAmpPark));
        //new Trigger(()-> xbox.getPOV() == 180).whileTrue(new PathWithStopDistance(localizationSubsystem, Constants.cTopCloseRing1, 1.1));
        //new Trigger(()-> xbox.getPOV() == 270).whileTrue(
        //        localizationSubsystem.buildPath(Constants.cRightStagePark));
        //pickup ring
        //new Trigger(()-> xbox.getLeftBumper()).whileTrue(new PickupRing(localizationSubsystem, swerveSubsystem));
        //new Trigger(()-> xbox.getRightBumper()).whileTrue(new TurnToCommand(localizationSubsystem, swerveSubsystem, Constants.cBotCloseRing3));
        //new Trigger(() -> joystick.getRawButton(2)).whileTrue(new RotateToAngleCommand(Math.toRadians(90), elevatorSubsystem));

        new Trigger(joystick::getTrigger).whileTrue(new RotateAnkleCommand(Math.toRadians(10), legSubsystem));
        new Trigger(()-> xbox.getPOV() == 0).onTrue(
                new InstantCommand(() ->
                {
                    swerveSubsystem.zeroGyro();
                    swerveSubsystem.zeroOdometry();
                    localizationSubsystem.reset();
                }));

        new Trigger(()-> xbox.getPOV() == 90).whileTrue(
                localizationSubsystem.buildPath(Constants.cAmpPark));

        new Trigger(()-> xbox.getPOV() == 180).whileTrue(new PathWithStopDistance(localizationSubsystem, Constants.cTopCloseRing1, 1.1));

        new Trigger(()-> xbox.getPOV() == 270).whileTrue(
                localizationSubsystem.buildPath(Constants.cRightStagePark));

        new Trigger(()-> xbox.getLeftBumper()).whileTrue(new PickupRing(localizationSubsystem, swerveSubsystem));

        new Trigger(()-> xbox.getRightBumper()).whileTrue(new TurnToCommand(localizationSubsystem, swerveSubsystem, Constants.cBotCloseRing3));

        new Trigger(joystick::getTrigger).onTrue(new IntakeSequenceCommandGroup(legSubsystem));
        new Trigger(() -> joystick.getRawButton(2)).onTrue(new HomeSequenceCommandGroup(legSubsystem));
        new Trigger(() -> joystick.getRawButton(3)).whileTrue(new ShooterCommand(shooterSubsystem, -0.5, ShooterCommand.ShooterType.POWER));
        new Trigger(() -> joystick.getRawButton(4)).whileTrue(new ShooterCommand(shooterSubsystem, -0.5, ShooterCommand.ShooterType.CONTROL));
        new Trigger(() -> joystick.getRawButton(5)).whileTrue(new ShooterCommand(shooterSubsystem, 0.5, ShooterCommand.ShooterType.POWER));
        new Trigger(() -> joystick.getRawButton(6)).whileTrue(new ShooterCommand(shooterSubsystem, 0.5, ShooterCommand.ShooterType.CONTROL));

        CommandScheduler.getInstance().setDefaultCommand(swerveSubsystem, teleopDriveCommand);
    }
    
    
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
//    public Command getAutonomousCommand()
//    {
//
//    }
}
