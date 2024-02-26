package frc.robot.subsystems;

import edu.wpi.first.math.geometry.*;
import frc.robot.math.RingMath;
import org.photonvision.targeting.TargetCorner;

import java.util.List;

public class RingResult {

    private RobotCamera camera;

    private double yaw;
    private double pitch;
    private double area;

    private double distance;
    private double robotYaw;

    private Pose2d robotPose;
    private Translation2d fieldPose;

    private List<TargetCorner> targetCorners;
    private TargetCorner botRightCorner;

    public RingResult(RobotCamera camera, double yaw, double pitch, double area, Pose2d robotPose, List<TargetCorner> corners) {
        this.camera = camera;

        this.yaw = yaw;
        this.pitch = pitch;
        this.area = area;

        this.robotPose = robotPose;

        this.targetCorners = corners;

        if (!corners.isEmpty()) {
            this.botRightCorner = findBotRightCorner(corners);
        }

        Transform3d camLocation = camera.getCameraPostion();
        double x = Math.cos(camLocation.getRotation().getZ()) * camLocation.getX() - Math.sin(camLocation.getRotation().getZ()) * camLocation.getY();
        double y = camLocation.getX() * Math.sin(camLocation.getRotation().getZ()) + camLocation.getY() * Math.cos(camLocation.getRotation().getZ());
        double d = RingMath.getDistance(pitch + Math.toDegrees(camLocation.getRotation().getY()), camLocation.getZ());
        double A = Math.hypot(x, y);
        double B = RingMath.getB(d, yaw);
        double c = RingMath.getcangle(x, y, yaw);
        double C = RingMath.getC(A, B, c);
        double robotYawBroken = RingMath.getRobotYaw(B, C, c, x, y);

        this.robotYaw = robotYawBroken + camLocation.getRotation().getZ();
        this.distance = d;

        fieldPose = robotPose.getTranslation().plus(new Translation2d(d, Rotation2d.fromRadians(-robotYaw + robotPose.getRotation().getRadians())).plus(new Translation2d(.178 - .05, robotPose.getRotation().plus(Rotation2d.fromDegrees(90)))));
    }

    private RingResult() {
        fieldPose = new Translation2d();
        distance = Double.POSITIVE_INFINITY;
    }

    private TargetCorner findBotRightCorner(List<TargetCorner> corners) {
        TargetCorner botRight = corners.get(0);

        for (TargetCorner corner : corners) {
            if (corner.x > botRight.x && corner.y > botRight.y) {
                botRight = corner;
            }
        }

        return botRight;
    }

    public Translation2d getFieldPose() {
        return fieldPose;
    }

    public RobotCamera getCamera() {
        return camera;
    }

    public double getArea() {
        return area;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public double getAngleToHeading() { return robotYaw; }

    public double getDistance() {
        return distance;
    }

    public static RingResult getEmpty() {
        return new RingResult();
    }

    private class Corners {

    }
}
