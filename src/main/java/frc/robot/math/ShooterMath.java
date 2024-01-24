package frc.robot.math;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;

public class ShooterMath {
    public static double getDistance3D(double distance2d, double z) {
        return Math.sqrt(Math.pow(distance2d, 2) + Math.pow(z, 2));
    }

    public static double getDistance2D(Pose2d robotPose) {
        return Math.sqrt(Math.pow(getXDiff(robotPose), 2) + Math.pow(getYDiff(robotPose), 2));
    }

    public static double getMinMotorVel(Pose2d robotPose) {
        return getDistance2D(robotPose) * Constants.motorVeltoDistance;
    }

    public static double getProjectileExitVel() {
        return 1;
    }

    public static double getAngleToSpeaker(Pose2d robotPose) {
        double xDiff = getXDiff(robotPose);
        double yDiff = getYDiff(robotPose);

        double targetAngle = (Math.PI / 2) - Math.atan2(yDiff, xDiff);

        return targetAngle;
    }

    public static double fixSpin(double angleDifference) {
        if(angleDifference < -180){
            angleDifference += 360;
        } else if (angleDifference > 180) {
            angleDifference -= 360;
        }

        return angleDifference;
    }

    public static double getXDiff(Pose2d robotPose) {
        return Constants.speakerTargetPos.getX() - robotPose.getX();
    }

    public static double getYDiff(Pose2d robotPose) {
        return Constants.speakerTargetPos.getY() - robotPose.getY();
    }
}
