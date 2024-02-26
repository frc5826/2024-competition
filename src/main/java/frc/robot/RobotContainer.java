// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.*;
import frc.robot.commands.TargetSpeakerCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.commands.PathWithStopDistance;
import frc.robot.positioning.FieldOrientation;
import frc.robot.positioning.Orientation;
import frc.robot.subsystems.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import static frc.robot.positioning.FieldOrientation.getOrientation;
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

    public final ArmSubsystem armSubsystem = new ArmSubsystem();

    private final VisionSubsystem visionSubsystem = new VisionSubsystem();

    private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem(
            new File(Filesystem.getDeployDirectory() + "/swerve"));

    private final LocalizationSubsystem localizationSubsystem = new LocalizationSubsystem(visionSubsystem, swerveSubsystem);

    private final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(
            swerveSubsystem, localizationSubsystem,
            () ->-xbox.getLeftY(), ()->-xbox.getLeftX(),
            ()->-xbox.getRightX(), () -> xbox.getXButton(),
            ()-> xbox.getLeftTriggerAxis(), ()-> xbox.getRightTriggerAxis());

    private final TargetSpeakerCommand targetSpeakerCommand = new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem);

    private final PickupRingTest pickupRingTest = new PickupRingTest(visionSubsystem, swerveSubsystem, localizationSubsystem);

    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

    private ArrayList<SendableChooser<Pose2d>> autoOptions;
    //private SendableChooser<Pose2d>[] autoOptions;
    private int autoRings = 0;

    private Supplier<Double> endX;
    private Supplier<Double> endY;
    private Supplier<Double> endRotation;
    private final Field2d field = new Field2d();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {

        //Button panel bindings //TODO put arm presets n stuff here
        panelButtons[0].onTrue(new ClimberLowerCommandGroup(climberSubsystem));
        panelButtons[1].onTrue(new RotateArmToAngleCommand(Math.toRadians(110), armSubsystem));
        panelButtons[2].onTrue(new ClimberRaiseCommandGroup(climberSubsystem));
//        panelButtons[3].onTrue(null);
//        panelButtons[4].onTrue(null);
        panelButtons[5].onTrue(new NotePickupFullCommandGroup(armSubsystem, shooterSubsystem));
//        panelButtons[6].onTrue(null);
        panelButtons[7].onTrue(new AimSpeakerCommandGroup(armSubsystem));
        panelButtons[8].onTrue(new NoteShootAmpCommandGroup(shooterSubsystem));
        panelButtons[9].onTrue(new AimAmpCommandGroup(armSubsystem));
        panelButtons[10].onTrue(new HomeSequenceCommandGroup(armSubsystem));
        panelButtons[11].onTrue(new NoteShootCommandGroup(shooterSubsystem));

        //Xbox button bindings

        new Trigger(xbox::getAButton).whileTrue(new AutoPickupRing(localizationSubsystem, swerveSubsystem));

        //TODO - Fill in with the right command
//        new Trigger(xbox::getXButton).whileTrue(localizationSubsystem.buildPath(cPickupPark));
        new Trigger(xbox::getXButton).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

//        new Trigger(xbox::getLeftBumper).whileTrue(localizationSubsystem.buildPath(cSpeakerPark));
        new Trigger(xbox::getLeftBumper).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

//        new Trigger(xbox::getRightBumper).whileTrue(localizationSubsystem.buildPath(cAmpPark));
        new Trigger(xbox::getRightBumper).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

//        new Trigger(() -> xbox.getPOV() == 0).or(() -> xbox.getPOV() == 180).whileTrue(localizationSubsystem.buildPath(cCenterStagePark));
        new Trigger(() -> xbox.getPOV() == 0).or(() -> xbox.getPOV() == 180).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

//        new Trigger(() -> xbox.getPOV() == 90).whileTrue(localizationSubsystem.buildPath(cRightStagePark));
        new Trigger(() -> xbox.getPOV() == 90).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

//        new Trigger(() -> xbox.getPOV() == 270).whileTrue(localizationSubsystem.buildPath(cLeftStagePark));
        new Trigger(() -> xbox.getPOV() == 270).whileTrue(new PathCommand(FieldOrientation::getOrientation, Orientation::getPickupPark, localizationSubsystem));

        //test
        new Trigger(xbox::getYButton).whileTrue(new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem));

        //TODO find way to zero gyro that doesn't reset pose TODO destroy
        new Trigger(xbox::getBackButton).and(xbox::getStartButton).debounce(1)
                .onTrue(new InstantCommand(() ->
                {
                    //Need to be run in this order
                    swerveSubsystem.zeroGyro();
//                    localizationSubsystem.reset();
                }));
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
//
//        new Trigger(()-> xbox.getPOV() == 0).onTrue(
//                new InstantCommand(() ->
//                {
//                    swerveSubsystem.zeroGyro();
//                    swerveSubsystem.zeroOdometry();
//                    localizationSubsystem.reset();
//                }));
//
//        new Trigger(()-> xbox.getPOV() == 90).whileTrue(
//                localizationSubsystem.buildPath(cAmpPark));
//
//        new Trigger(()-> xbox.getPOV() == 180).whileTrue(new PathWithStopDistance(localizationSubsystem, cTopCloseRing1, 1.1));
//
//        new Trigger(()-> xbox.getPOV() == 270).whileTrue(
//                localizationSubsystem.buildPath(cRightStagePark));
//
//        new Trigger(()-> xbox.getLeftBumper()).whileTrue(new PickupRing(localizationSubsystem, swerveSubsystem));
//
//        new Trigger(()-> xbox.getRightBumper()).whileTrue(new TurnToCommand(localizationSubsystem, swerveSubsystem, cBotCloseRing3));

//        new Trigger(() -> joystick.getRawButton(7)).whileTrue(new IntakeTestCommand(shooterSubsystem, 1));
//        new Trigger(() -> joystick.getRawButton(8)).whileTrue(new IntakeTestCommand(shooterSubsystem, -1));

        CommandScheduler.getInstance().setDefaultCommand(swerveSubsystem, teleopDriveCommand);

        setupAutoTab();
    }

    public void updateField() {
        field.setRobotPose(new Pose2d(endX.get(), endY.get(), Rotation2d.fromDegrees(endRotation.get())));
    }

    //TODO - Relies on getOrientation, which might not be initialized
    private void setupAutoTab() {
        ShuffleboardTab autoTab = Shuffleboard.getTab("auto");

        autoOptions = new ArrayList<SendableChooser<Pose2d>>();

        autoRings = 0;

        int widgetX = 0;
        int widgetY = 0;

        for(int i = 0; i < 8; i++) {
            autoOptions.add(i, new SendableChooser<Pose2d>());

//            autoOptions.get(i).setDefaultOption("Nothing", nothingPose);
            autoOptions.get(i).setDefaultOption("Nothing", getOrientation().getNothingPose());
//            autoOptions.get(i).addOption("Top Close Ring 1", cTopCloseRing1);
            autoOptions.get(i).addOption("Top Close Ring 1", getOrientation().getTopCloseRing());
//            autoOptions.get(i).addOption("Mid Close Ring 2", cMidCloseRing2);
            autoOptions.get(i).addOption("Mid Close Ring 2", getOrientation().getMidCloseRing());
//            autoOptions.get(i).addOption("Bot Close Ring 3", cBotCloseRing3);
            autoOptions.get(i).addOption("Bot Close Ring 3", getOrientation().getBotCloseRing());
//            autoOptions.get(i).addOption("Top Far Ring 4", cFarRing4);
            autoOptions.get(i).addOption("Top Far Ring 4", getOrientation().getFarRing4());
//            autoOptions.get(i).addOption("Mid Top Far Ring 5", cFarRing5);
            autoOptions.get(i).addOption("Mid Top Far Ring 5", getOrientation().getFarRing5());
//            autoOptions.get(i).addOption("Mid Far Ring 6", cFarRing6);
            autoOptions.get(i).addOption("Mid Far Ring 6", getOrientation().getFarRing6());
//            autoOptions.get(i).addOption("Mid Bot Far Ring 7", cFarRing7);
            autoOptions.get(i).addOption("Mid Bot Far Ring 7", getOrientation().getFarRing7());
//            autoOptions.get(i).addOption("Bot Far Ring 8", cFarRing8);
            autoOptions.get(i).addOption("Bot Far Ring 8", getOrientation().getFarRing8());
            //autoOptions[i].addOption("End Location", new Pose2d(endX, endY, Rotation2d.fromDegrees(endRotation)));

            autoTab.add("Auto " + i, autoOptions.get(i)).withWidget(BuiltInWidgets.kComboBoxChooser)
                    .withSize(2, 1).withPosition(widgetX, widgetY);

            widgetX += 2;
            if (widgetX == 8) {
                widgetY += 1;
                widgetX = 0;
            }
        }

        var x = autoTab.add("End X", 0)
                .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 16.5))
                .withSize(2, 1).withPosition(3, 2);

        var y = autoTab.add("End Y", 0)
                .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 8.15))
                .withSize(2, 1).withPosition(3, 3);

        var rot = autoTab.add("End Rotation", 0)
                .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -180, "max", 180))
                .withSize(2, 1).withPosition(5, 2);

        endX = () -> x.getEntry().getDouble(0);
        endY = () -> y.getEntry().getDouble(0);
        endRotation = () -> rot.getEntry().getDouble(0);

        autoTab.add(field).withSize(3, 2).withPosition(0, 2);
    }
    
    
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link CommandPS4Controller
     * PS4} controllers or {@link CommandJoystick Flight
     * joysticks}.
     */
    
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    //TODO - Relies on getOrientation, which might not be initialized
    public Command getAutonomousCommand()
    {
        boolean endPose = false;

        Pose2d endLoc = new Pose2d(endX.get(), endY.get(), Rotation2d.fromDegrees(endRotation.get()));

        for (int i = 0; i < 8; i++) {
            if (autoOptions.get(i).getSelected() != getOrientation().getNothingPose()) {
                autoRings++;
            }
        }

        return new AutoCommandGroup(localizationSubsystem, swerveSubsystem, armSubsystem, shooterSubsystem, autoRings, endLoc,
                autoOptions.get(0).getSelected(), autoOptions.get(1).getSelected(),
                autoOptions.get(2).getSelected(), autoOptions.get(3).getSelected(),
                autoOptions.get(4).getSelected(), autoOptions.get(5).getSelected(),
                autoOptions.get(6).getSelected(), autoOptions.get(7).getSelected());
    }
}
