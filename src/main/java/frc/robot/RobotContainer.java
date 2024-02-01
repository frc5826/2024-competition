// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.Autos;
import frc.robot.commands.TargetSpeakerCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.commands.autos.AutoRings;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import java.io.File;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

    private final XboxController xbox = new XboxController(1);

    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    private final VisionSubsystem visionSubsystem = new VisionSubsystem();

    private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem(
            new File(Filesystem.getDeployDirectory() + "/swerve"));

    private final LocalizationSubsystem localizationSubsystem = new LocalizationSubsystem(visionSubsystem, swerveSubsystem);

    private final TeleopDriveCommand teleopDriveCommand = new TeleopDriveCommand(
            swerveSubsystem, ()->-xbox.getLeftY(), ()->-xbox.getLeftX(), ()->-xbox.getRightX());

    private final TargetSpeakerCommand targetSpeakerCommand = new TargetSpeakerCommand(swerveSubsystem, localizationSubsystem);

    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {

        new Trigger(xbox::getAButtonPressed)
                .onTrue(new InstantCommand(() ->
                {
                    //Need to be run in this order
                    swerveSubsystem.zeroGyro();
                    localizationSubsystem.reset();
                }));

        new Trigger(xbox::getYButtonPressed).onTrue(
                new InstantCommand((swerveSubsystem::zeroOdometry)));

        new Trigger(xbox::getXButton).whileTrue(targetSpeakerCommand);

        new Trigger(xbox::getBButton).whileTrue(new AutoRings(localizationSubsystem,swerveSubsystem,new Pose2d(2,0,new Rotation2d(0))));

        //all subject to changed keybinds
        new Trigger(()-> xbox.getPOV() == 0).whileTrue(
                localizationSubsystem.buildPath(Constants.cSpeakerPark));

        new Trigger(()-> xbox.getPOV() == 90).whileTrue(
                localizationSubsystem.buildPath(Constants.cAmpPark));

        new Trigger(()-> xbox.getPOV() == 180).whileTrue(
                localizationSubsystem.buildPath(Constants.cLeftStagePark));

        new Trigger(()-> xbox.getPOV() == 270).whileTrue(
                localizationSubsystem.buildPath(Constants.cRightStagePark));

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
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous
        return Autos.exampleAuto(exampleSubsystem);
    }
}
