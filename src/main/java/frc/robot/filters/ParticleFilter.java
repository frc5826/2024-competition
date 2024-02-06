package frc.robot.filters;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleFilter {

    private List<Particle> particles;
    private int numParticles;
    private Random random;
    private double resamplePct;
    private final AprilTagFieldLayout field;
    private double resampleNoise;

    public ParticleFilter(int numParticles, AprilTagFieldLayout field, double resamplePct, double resampleNoise) {
        this.resampleNoise = resampleNoise;
        this.field = field;
        this.resamplePct = resamplePct;
        this.random = new Random();
        this.numParticles = numParticles;
        this.particles = new ArrayList<>();
        for(int i = 0; i < numParticles; i++){
            this.particles.add(new Particle(new Translation2d(random.nextDouble() * field.getFieldLength(),random.nextDouble() * field.getFieldWidth()),  1));
        }
    }

    public void resample() {
        List<Particle> sampled = new ArrayList<>();
        RandomCollection<Particle> randomCollection =  new RandomCollection<Particle>(random);
        for(int i = 0; i < this.particles.size(); i++){
           Particle p = this.particles.get(i);
           randomCollection.add(p.getWeight(), p);
        }
        for(int i = 0; i < numParticles * resamplePct; i++){
            sampled.add(randomCollection.next().fuzz(random, resampleNoise));
        }
        for(int i = 0; i < numParticles * (1. - resamplePct); i++){
            sampled.add(new Particle(new Translation2d(random.nextDouble() * field.getFieldLength(),random.nextDouble() * field.getFieldWidth()),  1));
        }
        this.particles = sampled;
    }

    public List<Pose2d> getCurrent() {
        return null;
    }
}
