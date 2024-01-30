package frc.robot.math;

public class RingMath {
    public double getDistance(double pitch, double cameraHeight) {
        return cameraHeight / Math.tan(pitch);
    }

    public double getB(double distance, double yaw) {
        return distance / Math.cos(yaw);
    }

    public double getC(double A, double B, double camX, double camY, double yaw) {
        double c = Math.atan2(camX, camY) + (90 - yaw);

        return Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2) - 2 * A * B * Math.cos(c));
    }

    public double getRobotYaw(double B, double C, double c, double camX, double camY) {
        return Math.asin(B * Math.sin(c) / C) - Math.atan2(camY, camX);
    }

}
