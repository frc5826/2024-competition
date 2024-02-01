// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.*;
import frc.robot.math.BetterArrayList;
import frc.robot.math.ElevatorMath;

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
    //TODO ask Schaefer for arm lengths
    public static final double cElevatorMinLength = -1;
    public static final double cElevatorMaxLength = -1;
    public static final Translation2d cStartingTarget = new Translation2d(0, 0);
    public static final Translation2d cElevatorOrigin = new Translation2d(-1, -1);

    //TODO add real boundaries, example here so whoever is unfortunate enough to work on this knows how to construct the list
    public static final BetterArrayList<ElevatorMath.ElevatorBoundary> cElevatorBoundaries = new BetterArrayList<ElevatorMath.ElevatorBoundary>()
            .append(new ElevatorMath.ElevatorBoundary(50, ElevatorMath.ElevatorBoundary.BoundType.MAXIMUM, ElevatorMath.ElevatorBoundary.Axis.X))
            .append(new ElevatorMath.ElevatorBoundary(50, ElevatorMath.ElevatorBoundary.BoundType.MINIMUM, ElevatorMath.ElevatorBoundary.Axis.Y));

    //TODO find IDs
    public static final int cRotateMotor1ID = -1;
    public static final int cRotateMotor2ID = -1;
    public static final int cExtensionMotorID = -1;
    public static final int cRotateEncoderID = -1;
    public static final int cExtensionEncoderID = -1;

    //TODO tune PIDs
    public static final double cRotateP = -1;
    public static final double cRotateI = -1;
    public static final double cRotateD = -1;
    public static final double cRotateMin = -1;
    public static final double cRotateMax = -1;
    public static final double cRotateDeadband = -1;

    public static final double cExtensionP = -1;
    public static final double cExtensionI = -1;
    public static final double cExtensionD = -1;
    public static final double cExtensionMin = 0;
    public static final double cExtensionMax = 1;
    public static final double cExtensionDeadband = -1;

    public static final int cMaxArmEncoderRotation = -1;
    public static final int cMinArmEncoderRotation = -1;
    public static final int cMaxArmEncoderExtension = -1;
    public static final int cMinArmEncoderExtension = -1;
    public static final double cDriveDeadband = 0.15;
    public static final double cTurnDeadband = 0.15;

    public static final Pose2d cSpeakerPark = new Pose2d(1.65, 5.54, Rotation2d.fromDegrees(180));
    public static final Pose2d cAmpPark = new Pose2d(1.84, 7.5, Rotation2d.fromDegrees(90));

    public static final Pose2d cLeftStagePark = new Pose2d(4.5, 5, Rotation2d.fromDegrees(300));
    public static final Pose2d cCenterStagePark = new Pose2d(5.8, 4.5, Rotation2d.fromDegrees(180));
    public static final Pose2d cRightStagePark = new Pose2d(4.5, 3.5, Rotation2d.fromDegrees(60));
    public static final Pose2d cTopCloseRing1 = new Pose2d(2.2, 7, Rotation2d.fromDegrees(0));
    public static final Pose2d cMidCloseRing2 = new Pose2d(2.2, 5.55, Rotation2d.fromDegrees(0));
    public static final Pose2d cBotCloseRing3 = new Pose2d(2.9, 4.1, Rotation2d.fromDegrees(0));

    //TODO set these values :)
    public static final Pose2d cSpeakerTargetPos = new Pose2d(0, 5.55, Rotation2d.fromDegrees(0));
    public static final double cSpeakerTargetHeight = 0;
    public static final double cMotorVeltoDistance = 100;
    public static final double cMotorVeltoExitVel = 1;


}
