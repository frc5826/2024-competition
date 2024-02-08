package frc.robot.filters;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.christopherfrantz.dbscan.DBSCANClusterer;
import org.christopherfrantz.dbscan.DBSCANClusteringException;
import org.christopherfrantz.dbscan.DistanceMetric;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParticleFilter {

    private List<Particle> particles;
    private int numParticles;
    private Random random;
    private double resamplePct;
    private final AprilTagFieldLayout field;
    private double resampleNoise;
    private Function<Particle, Double> weightProvider;

    public ParticleFilter(int numParticles, AprilTagFieldLayout field, double resamplePct, double resampleNoise, Function<Particle, Double> weightProvider) {
        this.weightProvider = weightProvider;
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
        for(int i = 0; i < this.particles.size(); i++){
            Particle p = this.particles.get(i);
            p.setWeight(weightProvider.apply(p));
        }
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

    public List<Translation2d> getCurrent() {
        return particles.stream().map(Particle::getPoint).toList();
   }

   public List<Translation2d> getCentroids(double epsilon, int minPoints) {
       System.out.println("Start centroids");
        List<Translation2d> output = new LinkedList<>();
        try {
            DBSCANClusterer<Translation2d> clusterer =
                    new DBSCANClusterer<>(
                            getCurrent(),
                            minPoints,
                            epsilon,
                            new DistanceMetricTranslation2Ds()
                    );
            List<ArrayList<Translation2d>> clusters = clusterer.performClustering();
            for(ArrayList<Translation2d> clump : clusters){
                DoubleSummaryStatistics xStats = clump.stream().map(Translation2d::getX).collect(Collectors.summarizingDouble(d -> d));
                double x = xStats.getAverage();

                DoubleSummaryStatistics yStats = clump.stream().map(Translation2d::getY).collect(Collectors.summarizingDouble(d -> d));
                double y = yStats.getAverage();
                output.add(new Translation2d(x, y));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       System.out.println("End centroids: " + output);
        return output;
   }

   private static class DistanceMetricTranslation2Ds implements DistanceMetric<Translation2d> {

       @Override
       public double calculateDistance(Translation2d translation2d, Translation2d v1) throws DBSCANClusteringException {
           return translation2d.getDistance(v1);
       }
   }
}
