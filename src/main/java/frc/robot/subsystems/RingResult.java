package frc.robot.subsystems;

import edu.wpi.first.math.geometry.*;
import frc.robot.math.RingMath;

public class RingResult {

    private RobotCamera camera;

    private double yaw;
    private double pitch;
    private double area;

    private double distance;
    private double robotYaw;

    private Pose2d robotPose;
    private Translation2d fieldPose;

    public RingResult(RobotCamera camera, double yaw, double pitch, double area, Pose2d robotPose) {
        this.camera = camera;

        this.yaw = yaw;
        this.pitch = pitch;
        this.area = area;

        this.robotPose = robotPose;

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

        fieldPose = robotPose.getTranslation().plus(new Translation2d(d, Rotation2d.fromRadians(-robotYaw + robotPose.getRotation().getRadians())));
    }

    private RingResult() {
        fieldPose = new Translation2d();
        distance = Double.POSITIVE_INFINITY;
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
}
