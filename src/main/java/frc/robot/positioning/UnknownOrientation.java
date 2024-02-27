package frc.robot.positioning;

import edu.wpi.first.math.geometry.Pose2d;

public class UnknownOrientation implements Orientation {

    protected UnknownOrientation() {
    }

    @Override
    public Pose2d getPickupPark() {
        return null;
    }

    @Override
    public Pose2d getSpeakerPark() {
        return null;
    }

    @Override
    public Pose2d getAmpPark() {
        return null;
    }

    @Override
    public Pose2d getCenterStagePark() {
        return null;
    }

    @Override
    public Pose2d getRightStagePark() {
        return null;
    }

    @Override
    public Pose2d getLeftStagePark() {
        return null;
    }

    @Override
    public Pose2d getNothingPose() {
        return FieldOrientation.NOTHING_POSE;
    }

    @Override
    public Pose2d getTopCloseRing() {
        return null;
    }

    @Override
    public Pose2d getMidCloseRing() {
        return null;
    }

    @Override
    public Pose2d getBotCloseRing() {
        return null;
    }

    @Override
    public Pose2d getFarRing4() {
        return null;
    }

    @Override
    public Pose2d getFarRing5() {
        return null;
    }

    @Override
    public Pose2d getFarRing6() {
        return null;
    }

    @Override
    public Pose2d getFarRing7() {
        return null;
    }

    @Override
    public Pose2d getFarRing8() {
        return null;
    }

    @Override
    public Pose2d getSpeakerTargetPos() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
