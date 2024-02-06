package frc.robot.filters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.Random;

public class Particle {
    private final Translation2d point;
    private final double weight;

    public Particle(Translation2d point, double weight) {
        this.point = point;
        this.weight = weight;
    }

    public Translation2d getPoint() {
        return point;
    }

    public double getWeight() {
        return weight;
    }

    public Particle fuzz(Random random, double resampleNoise) {
        double xNoise = (random.nextDouble() - 0.5) * resampleNoise;
        double yNoise = (random.nextDouble() - 0.5) * resampleNoise;
        return new Particle(new Translation2d(this.point.getX() + xNoise, this.point.getY() + yNoise), this.weight);
    }
}
