// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.jni.SignalLoggerJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TestSubsystem;
import sun.misc.Signal;

import java.util.Random;
import java.util.random.RandomGenerator;

import static frc.robot.positioning.FieldOrientation.getOrientation;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    private Command autonomousCommand;
    
    private RobotContainer robotContainer;

    StructPublisher<Pose2d> publisher = NetworkTableInstance.getDefault()
            .getStructTopic("MyPose", Pose2d.struct).publish();


    
    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit()
    {
        Constants.panelButtons[0].onTrue(new TestSubsystem().getCommand());
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        //robotContainer = new RobotContainer();
        ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
//        CommandScheduler.getInstance().onCommandExecute(this::onCommandExecute);
        CommandScheduler.getInstance().onCommandInitialize(this::onCommandInit);
        CommandScheduler.getInstance().onCommandFinish(this::onCommandFinished);
        CommandScheduler.getInstance().onCommandInterrupt(this::onCommandInterrupt);
        DataLogManager.start();
        DriverStation.startDataLog(DataLogManager.getLog(),true);
    }

    private void onCommandExecute(Command c){
        System.err.println("COMMAND EXECUTE - " + c);
    }

    private void onCommandInit(Command c){
        System.err.println("COMMAND INITIALIZE - " + c);
    }

    private void onCommandFinished(Command c){
        System.err.println("COMMAND FINISHED - " + c);
    }

    private void onCommandInterrupt(Command c){
        System.err.println("COMMAND INTERRUPT - " + c);
    }
    
    
    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */

    double x,y,rot = 0;
    Random r = new Random();
    @Override
    public void robotPeriodic()
    {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.

//        robotContainer.configureAutoTab();

        SignalLogger.writeDoubleArray("Pose",new double[]{x+=(r.nextDouble(-0.01,0.01)),y+=(r.nextDouble(-0.01,0.01)),rot+=(r.nextDouble(-0.01,0.01))});
        SignalLogger.writeString("Msg","test" + System.currentTimeMillis());
        publisher.set(new Pose2d(x,y, Rotation2d.fromRadians(rot)));

        CommandScheduler.getInstance().run();
    }
    
    
    /** This method is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}
    
    
//    @Override
//    public void disabledPeriodic() {
//        robotContainer.updateField();
//    }
    
    
    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit()
    {
//        robotContainer.getAutonomousCommand().schedule();
    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}
    
    
    @Override
    public void teleopInit()
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }

//        robotContainer.armSubsystem.setArmHome();
//        robotContainer.armSubsystem.setExtensionHome();
//        robotContainer.armSubsystem.setWristHome();

    }
    
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
    }
    
    
    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
    
    
    /** This method is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}
    
    
    /** This method is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
}
