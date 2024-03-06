// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.PIDConstants;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

    public static final Joystick joystick = new Joystick(0);

    public static final int rotateMotor1ID = 10;
    public static final int rotateMotor2ID = 11;
    public static final int extendMotorID = 14;
    public static final int ankleMotorID = 12;

    public static final int shooterMotor1ID = 15;
    public static final int shooterMotor2ID = 16;
    public static final int shooterControlMotorID = 30;

    public static final int rotateEncoderID = 0;
    public static final int extendEncoderID = 1;
    public static final int ankleEncoderID = 2;

    public static final int beamBreakID = 3;

    public static final double cRotateP = 4.5;
    public static final double cRotateI = 1;
    public static final double cRotateD = 0.2;
    public static final double cRotateMax = 1;
    public static final double cRotateMin = -1;
    public static final double cRotateDeadband = 0;
    public static final double cExtendP = 3.5;
    public static final double cExtendI = 0.2;
    public static final double cExtendD = 0.1;
    public static final double cExtendMax = 1;
    public static final double cExtendMin = -1;
    public static final double cExtendDeadband = 0;

    public static final double cWristP = 4;
    public static final double cWristI = 0.1;
    public static final double cWristD = 0;
    public static final double cWristMax = 1;
    public static final double cWristMin = -1;
    public static final double cWristDeadband = 0;

    public static final double cDriveDeadband = 0.15;
    public static final double cTurnDeadband = 0.15;

    //If you want to use these, check out FieldOrientation and RobotContainer for how it's used.
//    public static final Pose2d cSpeakerPark = new Pose2d(1.4, 5.54, Rotation2d.fromDegrees(0));
//    public static final Pose2d cSpeakerPose = new Pose2d(0, 5.55, Rotation2d.fromDegrees(180));
//    public static final Pose2d cPickupPark = new Pose2d(15.25, 1.2, Rotation2d.fromDegrees(-60));
//    public static final Pose2d cAmpPark = new Pose2d(1.84, 7.5, Rotation2d.fromDegrees(-90));
//
//    public static final Pose2d cLeftStagePark = new Pose2d(4.5, 5, Rotation2d.fromDegrees(300));
//    public static final Pose2d cCenterStagePark = new Pose2d(5.8, 4.5, Rotation2d.fromDegrees(180));
//    public static final Pose2d cRightStagePark = new Pose2d(4.5, 3.5, Rotation2d.fromDegrees(60));
//    public static final Pose2d cTopCloseRing1 = new Pose2d(2.89, 7, Rotation2d.fromDegrees(0));
//    public static final Pose2d cMidCloseRing2 = new Pose2d(2.89, 5.55, Rotation2d.fromDegrees(0));
//    public static final Pose2d cBotCloseRing3 = new Pose2d(2.89, 4.11, Rotation2d.fromDegrees(0));
//    public static final Pose2d cFarRing4 = new Pose2d(8.28, 7.44, Rotation2d.fromDegrees(0));
//    public static final Pose2d cFarRing5 = new Pose2d(8.28, 5.78, Rotation2d.fromDegrees(0));
//    public static final Pose2d cFarRing6 = new Pose2d(8.28, 4.11, Rotation2d.fromDegrees(0));
//    public static final Pose2d cFarRing7 = new Pose2d(8.28, 2.44, Rotation2d.fromDegrees(0));
//    public static final Pose2d cFarRing8 = new Pose2d(8.28, 0.77, Rotation2d.fromDegrees(0));
//    public static final Pose2d nothingPose = new Pose2d(69, 420, Rotation2d.fromDegrees(0));
//
//    //TODO set these values :)
//    public static final Pose2d cSpeakerTargetPos = new Pose2d(0, 5.55, Rotation2d.fromDegrees(0));
    public static final double cSpeakerTargetHeight = 0;
    public static final double cMotorVeltoDistance = 100;
    public static final double cMotorVeltoExitVel = 1;

    public static final PIDConstants cTurnPID = new PIDConstants(4.0, 0.01, 0.25);
    public static final PIDConstants cDrivePID = new PIDConstants(4.0, 0, 0.5);

    public static final double maxVelocity = 3.6; //doesn't make robot faster

    public static final PathConstraints pathConstraints = new PathConstraints(
            2.0, //TODO max this then tune PID
            2.0,
            2 * Math.PI,
            2 * Math.PI);

    public static final Joystick buttonPanel = new Joystick(2);
    public static final Trigger[] panelButtons;

    static {
        panelButtons = new Trigger[12];

        for(int i = 0; i < panelButtons.length; i++) {
            int finalI = i+1;
            panelButtons[i] = new Trigger(() -> buttonPanel.getRawButton(finalI));
        }
    }


}
