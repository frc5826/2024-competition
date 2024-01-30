package frc.robot.subsystems;

public class RingResult {

    private RobotCamera camera;

    private double yaw;
    private double pitch;
    private double area;

    public RingResult(RobotCamera camera, double yaw, double pitch, double area) {
        this.camera = camera;

        this.yaw = yaw;
        this.pitch = pitch;
        this.area = area;
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
}
