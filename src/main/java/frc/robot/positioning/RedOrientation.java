package frc.robot.positioning;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

//TODO - Switch extending BlueOrientation to implementing Orientation and fill in details
public class RedOrientation implements Orientation {

    protected RedOrientation() {
    }

    @Override
    public Pose2d getPickupPark() {
        return new Pose2d(1.29, 1.20, Rotation2d.fromDegrees(60));
    }

    @Override
    public Pose2d getSpeakerPark() {
        return new Pose2d(15.14, 5.54, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getAmpPark() {
        return new Pose2d(14.70, 7.50, Rotation2d.fromDegrees(-90));
    }

    @Override
    public Pose2d getCenterStagePark() {
        return new Pose2d(10.74, 4.5, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getRightStagePark() {
        return new Pose2d(12.04, 3.5, Rotation2d.fromDegrees(-120));
    }

    @Override
    public Pose2d getLeftStagePark() {
        return new Pose2d(12.04, 5, Rotation2d.fromDegrees(120));
    }

    @Override
    public Pose2d getNothingPose() {
        return FieldOrientation.NOTHING_POSE;
    }

    @Override
    public Pose2d getTopCloseRing() {
        return new Pose2d(13.65, 7, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getMidCloseRing() {
        return new Pose2d(13.65, 5.55, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getBotCloseRing() {
        return new Pose2d(13.65, 4.11, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getFarRing4() {
        return new Pose2d(8.26, 7.44, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getFarRing5() {
        return new Pose2d(8.26, 5.78, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getFarRing6() {
        return new Pose2d(8.26, 4.11, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getFarRing7() {
        return new Pose2d(8.26, 2.44, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getFarRing8() {
        return new Pose2d(8.26, 0.77, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getSpeakerTargetPos() {
        return new Pose2d(16.54, 5.55, Rotation2d.fromDegrees(0));
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
