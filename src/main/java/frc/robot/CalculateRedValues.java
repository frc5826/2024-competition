package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import java.io.IOException;
import java.util.HashMap;

public class CalculateRedValues {

    public static void main(String[] args) throws IOException {
        AprilTagFieldLayout field = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);

        System.out.println("Length:" + field.getFieldLength());
        System.out.println("Width: " + field.getFieldWidth());

        HashMap<String, Pose2d> things = new HashMap<>();
        things.put("cSpeakerPark",  new Pose2d(1.4, 5.54, Rotation2d.fromDegrees(0)));
        things.put("cSpeakerPose",  new Pose2d(0, 5.55, Rotation2d.fromDegrees(180)));
        things.put("cPickupPark",  new Pose2d(15.25, 1.2, Rotation2d.fromDegrees(-60)));
        things.put("cAmpPark",  new Pose2d(1.84, 7.5, Rotation2d.fromDegrees(-90)));
        things.put("cLeftStagePark",  new Pose2d(4.5, 5, Rotation2d.fromDegrees(300)));
        things.put("cCenterStagePark",  new Pose2d(5.8, 4.5, Rotation2d.fromDegrees(180)));
        things.put("cRightStagePark",  new Pose2d(4.5, 3.5, Rotation2d.fromDegrees(60)));
        things.put("cTopCloseRing1",  new Pose2d(2.89, 7, Rotation2d.fromDegrees(0)));
        things.put("cMidCloseRing2",  new Pose2d(2.89, 5.55, Rotation2d.fromDegrees(0)));
        things.put("cBotCloseRing3",  new Pose2d(2.89, 4.11, Rotation2d.fromDegrees(0)));
        things.put("cFarRing4",  new Pose2d(8.28, 7.44, Rotation2d.fromDegrees(0)));
        things.put("cFarRing5",  new Pose2d(8.28, 5.78, Rotation2d.fromDegrees(0)));
        things.put("cFarRing6",  new Pose2d(8.28, 4.11, Rotation2d.fromDegrees(0)));
        things.put("cFarRing7",  new Pose2d(8.28, 2.44, Rotation2d.fromDegrees(0)));
        things.put("cFarRing8",  new Pose2d(8.28, 0.77, Rotation2d.fromDegrees(0)));
        things.put("nothingPose",  new Pose2d(69, 420, Rotation2d.fromDegrees(0)));

        for(String position : things.keySet()){
            System.out.println(position);
            Pose2d p = things.get(position);
            System.out.println(new Pose2d(field.getFieldLength() - p.getX(), p.getY(), p.getRotation().rotateBy(Rotation2d.fromDegrees(180))));
        }

    }

}
