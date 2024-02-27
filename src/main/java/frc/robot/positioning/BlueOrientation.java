package frc.robot.positioning;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class BlueOrientation implements Orientation{

    protected BlueOrientation() {
    }

    @Override
    public Pose2d getPickupPark() {
        return new Pose2d(15.25, 1.2, Rotation2d.fromDegrees(-60));
    }

    @Override
    public Pose2d getSpeakerPark() {
        return new Pose2d(1.4, 5.54, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getAmpPark() {
        return new Pose2d(1.84, 7.5, Rotation2d.fromDegrees(-90));
    }

    @Override
    public Pose2d getCenterStagePark() {
        return new Pose2d(5.8, 4.5, Rotation2d.fromDegrees(180));
    }

    @Override
    public Pose2d getRightStagePark() {
        return new Pose2d(4.5, 3.5, Rotation2d.fromDegrees(60));
    }

    @Override
    public Pose2d getLeftStagePark() {
        return new Pose2d(4.5, 5, Rotation2d.fromDegrees(300));
    }

    @Override
    public Pose2d getNothingPose() {
        return FieldOrientation.NOTHING_POSE;
    }

    @Override
    public Pose2d getTopCloseRing() {
        return new Pose2d(2.89, 7, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getMidCloseRing() {
        return new Pose2d(2.89, 5.55, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getBotCloseRing() {
        return new Pose2d(2.89, 4.11, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getFarRing4() {
        return  new Pose2d(8.28, 7.44, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getFarRing5() {
        return new Pose2d(8.28, 5.78, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getFarRing6() {
        return new Pose2d(8.28, 4.11, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getFarRing7() {
        return new Pose2d(8.28, 2.44, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getFarRing8() {
        return new Pose2d(8.28, 0.77, Rotation2d.fromDegrees(0));
    }

    @Override
    public Pose2d getSpeakerTargetPos() {
        return new Pose2d(0, 5.55, Rotation2d.fromDegrees(0));
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
