// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

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
    public static final Translation2d cElevatorOrigin = new Translation2d(-1, -1);

    //TODO find motor CAN IDs
    public static final int cRotateMotor1ID = -1;
    public static final int cRotateMotor2ID = -1;
    public static final int cExtensionMotorID = -1;

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

    public static class OperatorConstants
    {
        public static final int DRIVER_CONTROLLER_PORT = 0;


    }
}
