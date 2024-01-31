package frc.robot.math;

public class RingMath {
    public static double getDistance(double pitch, double cameraHeight) {
        return cameraHeight / Math.tan(-Math.toRadians(pitch));
    }

    public static double getB(double distance, double yaw) {
        return distance / Math.cos(Math.toRadians(yaw));
    }

    public static double getcangle(double camX, double camY, double yaw) {
        return Math.atan2(camX, camY) + (Math.PI / 2 - Math.toRadians(yaw));
    }

    public static double getC(double A, double B, double c) {
        return Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2) - 2 * A * B * Math.cos(c));
    }

    public static double getRobotYaw(double B, double C, double c, double camX, double camY) {
        return Math.asin(B * Math.sin(c) / C) - Math.atan2(camY, camX);
    }

}
