package frc.robot.math;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;

import static frc.robot.positioning.FieldOrientation.getOrientation;

public class ShooterMath {
    public static double getDistance3D(Pose2d robotPose) {
        return Math.sqrt(Math.pow(getDistance2D(robotPose), 2) + Math.pow(Constants.cSpeakerTargetHeight, 2));
    }

    public static double getDistance2D(Pose2d robotPose) {
        return Math.sqrt(Math.pow(getXDiff(robotPose), 2) + Math.pow(getYDiff(robotPose), 2));
    }

    public static double getMinMotorVel(Pose2d robotPose) {
        return getDistance2D(robotPose) * Constants.cMotorVeltoDistance;
    }

    public static double getProjectileExitVel(double motorVel) {
        return motorVel * Constants.cMotorVeltoExitVel;
    }

    //TODO pick up right here
    public static double getProjectileTime(Pose2d robotPose, double motorVel) {
        return Math.pow(getProjectileExitVel(motorVel), -1) * getDistance3D(robotPose);
    }

    public static double getAngleToSpeaker(Pose2d robotPose) {
        double xDiff = getXDiff(robotPose);
        double yDiff = getYDiff(robotPose);

        double targetAngle = /*(Math.PI / 2) -*/ Math.atan2(yDiff, xDiff);

        return targetAngle;
    }

    public static double fixSpin(double angleDifference) {
        if(angleDifference < -Math.PI){
            angleDifference += 2*Math.PI;
        } else if (angleDifference > Math.PI) {
            angleDifference -= 2*Math.PI;
        }

        return angleDifference;
    }

    //TODO - Relies on getOrientation, which might not be initialized
    public static double getXDiff(Pose2d robotPose) {
//        return Constants.cSpeakerTargetPos.getX() - robotPose.getX();
        return getOrientation().getSpeakerTargetPos().getX() - robotPose.getX();
    }

    //TODO - Relies on getOrientation, which might not be initialized
    public static double getYDiff(Pose2d robotPose) {
//        return Constants.cSpeakerTargetPos.getY() - robotPose.getY();
        return getOrientation().getSpeakerTargetPos().getY() - robotPose.getY();
    }
}
