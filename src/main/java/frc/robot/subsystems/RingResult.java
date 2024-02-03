package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.math.RingMath;

public class RingResult {

    private RobotCamera camera;

    private double yaw;
    private double pitch;
    private double area;

    private double distance;
    private double robotYaw;

    public RingResult(RobotCamera camera, double yaw, double pitch, double area) {
        this.camera = camera;

        this.yaw = yaw;
        this.pitch = pitch;
        this.area = area;

        Transform3d camLocation = camera.getCameraPostion();
        double x = Math.cos(camLocation.getRotation().getZ()) * camLocation.getX() - Math.sin(camLocation.getRotation().getZ()) * camLocation.getY();
        double y = camLocation.getX() * Math.sin(camLocation.getRotation().getZ()) + camLocation.getY() * Math.cos(camLocation.getRotation().getZ());
        double d = RingMath.getDistance(pitch + Math.toDegrees(camLocation.getRotation().getX()), camLocation.getZ());
        double A = Math.hypot(x, y);
        double B = RingMath.getB(d, yaw);
        double c = RingMath.getcangle(x, y, yaw);
        double C = RingMath.getC(A, B, c);
        double robotYawBroken = RingMath.getRobotYaw(B, C, c, x, y);

        this.robotYaw = robotYawBroken + camLocation.getRotation().getZ();
        this.distance = d;
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
}
