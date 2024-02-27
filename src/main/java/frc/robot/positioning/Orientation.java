package frc.robot.positioning;

import edu.wpi.first.math.geometry.Pose2d;

public interface Orientation {

    Pose2d getPickupPark();

    Pose2d getSpeakerPark();

    Pose2d getAmpPark();

    Pose2d getCenterStagePark();

    Pose2d getRightStagePark();

    Pose2d getLeftStagePark();

    Pose2d getNothingPose();

    Pose2d getTopCloseRing();

    Pose2d getMidCloseRing();

    Pose2d getBotCloseRing();

    Pose2d getFarRing4();

    Pose2d getFarRing5();

    Pose2d getFarRing6();

    Pose2d getFarRing7();

    Pose2d getFarRing8();

    Pose2d getSpeakerTargetPos();

    boolean isValid();

}
