package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//Code pulled from - https://github.com/STMARobotics/frc-7028-2023/blob/5916bb426b97f10e17d9dfd5ec6c3b6fda49a7ce/src/main/java/frc/robot/subsystems/PoseEstimatorSubsystem.java
public class LocalizationSubsystem extends SubsystemBase {

    private AprilTagFieldLayout fieldLayout;
    private final VisionSubsystem visionSubsystem;
    private final SwerveSubsystem swerveSubsystem;
    private final SwerveDrivePoseEstimator poseEstimator;
    private final HashSet<AprilTagResult> processed;

    private Pose3d robotPos = new Pose3d();

    private final Field2d field = new Field2d();

    private Rotation2d rotationTarget;

    private static final Vector<N3> visionMeasurementStdDevs = VecBuilder.fill(0.1, 0.1, Units.degreesToRadians(10));

    private static final Vector<N3> stateStdDevs = VecBuilder.fill(0.01, 0.01, Units.degreesToRadians(5));

    public LocalizationSubsystem(VisionSubsystem visionSubsystem, SwerveSubsystem swerveSubsystem) {
        try {
            fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
            //TODO - Is this the best way to orient the field?
            Optional<DriverStation.Alliance> allianceOption = DriverStation.getAlliance();
            if(allianceOption.isPresent()){
                DriverStation.Alliance alliance = allianceOption.get();
                fieldLayout.setOrigin(alliance == DriverStation.Alliance.Blue ? AprilTagFieldLayout.OriginPosition.kBlueAllianceWallRightSide : AprilTagFieldLayout.OriginPosition.kRedAllianceWallRightSide);
            }
        } catch (IOException e) {
            fieldLayout = null;
            e.printStackTrace();
        }
        this.processed = new HashSet<>();
        this.visionSubsystem = visionSubsystem;
        this.swerveSubsystem = swerveSubsystem;
        //TODO - Is there a better guess at initial pose?
        this.poseEstimator = new SwerveDrivePoseEstimator(swerveSubsystem.getKinematics(), swerveSubsystem.getGyroRotation(), swerveSubsystem.getModulePositions(), new Pose2d(), stateStdDevs, visionMeasurementStdDevs);

        setupShuffleboard(field);

        PPHolonomicDriveController.setRotationTargetOverride(this::updateRotationTarget);

        setupPathPlanner();
    }

    public void setRotationTarget(Rotation2d rotation) { rotationTarget = rotation; }

    public Rotation2d getRotationTarget() { return rotationTarget; }

    public void removeRotationTarget() { rotationTarget = null; }

    private Optional<Rotation2d> updateRotationTarget() {
        if (rotationTarget != null) {
            return Optional.of(rotationTarget);
        } else {
            return Optional.empty();
        }
    }

    public void periodic() {
        if(fieldLayout != null){
            for(AprilTagResult result : visionSubsystem.getAprilTagResults()){
                if(!processed.contains(result)) {
                    processed.add(result);
                    Optional<Pose3d> tagPose = fieldLayout.getTagPose(result.getId());
                    if (tagPose.isPresent()) {
                        Pose3d camPose = tagPose.get().transformBy(result.getAprilTagLocation().inverse());
                        Pose3d robotPose = camPose.transformBy(result.getCamera().getRobotLocation());
                        robotPos = robotPose;

                        poseEstimator.addVisionMeasurement(robotPose.toPose2d(), result.getTimestamp());
                    }
                }
            }

            poseEstimator.update(swerveSubsystem.getGyroRotation(), swerveSubsystem.getModulePositions());
        }
        else {
            System.err.println("Unable to localize. Field Layout not loaded.");
        }
        field.setRobotPose(getCurrentPose());
    }

    public void reset() {
        poseEstimator.resetPosition(
                swerveSubsystem.getGyroRotation(),
                swerveSubsystem.getModulePositions(),
                new Pose2d(0,0,Rotation2d.fromRadians(0)));
    }

    public void setupPathPlanner()
    {
        AutoBuilder.configureHolonomic(
                this::getCurrentPose, // Robot pose supplier
                swerveSubsystem::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
                swerveSubsystem::getRobotVelocity, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                swerveSubsystem::driveRobotOriented, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
                new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                        new PIDConstants(4.0, 0.0, 0.5),
                        // Translation PID constants
                        //swerveSubsystem.getPID(),
                        new PIDConstants(4.0, 0, 0),
                        // Rotation PID constants
                        4.5,
                        // Max module speed, in m/s
                        swerveSubsystem.getDriveBaseRadius(),
                        // Drive base radius in meters. Distance from robot center to furthest module.
                        new ReplanningConfig()
                        // Default path replanning config. See the API for the options here
                ),
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE
                    var alliance = DriverStation.getAlliance();
                    return alliance.filter(value -> value == DriverStation.Alliance.Red).isPresent();
                },
                swerveSubsystem // Reference to this subsystem to set requirements
        );
    }

    public Command buildPath(Pose2d targetPose) {
        PathConstraints constraints = new PathConstraints(
                2.0,
                2.0,
                3.14159,
                3.14159);

        Command path = AutoBuilder.pathfindToPose(targetPose, constraints);

        return path;
    }

    private void setupShuffleboard(Field2d field) {
        ShuffleboardTab tab = Shuffleboard.getTab("position");

        tab.add(field)
                .withPosition(2,0)
                .withSize(5,3);

        ShuffleboardLayout position = tab.getLayout("Robot position", BuiltInLayouts.kList)
                .withPosition(0,0)
                .withSize(2,2);


        position.addDouble("Robot X", ()-> getCurrentPose().getX());
        position.addDouble("Robot Y", ()-> getCurrentPose().getY());
        position.addDouble("Robot rotation", ()-> swerveSubsystem.getHeading().getDegrees());


        ShuffleboardLayout robot3DPose = tab.getLayout("robot 3d pose", BuiltInLayouts.kList)
                .withPosition(7, 0)
                .withSize(2, 4);

        robot3DPose.addDouble("x", ()-> robotPos.getX());
        robot3DPose.addDouble("y", ()-> robotPos.getY());
        robot3DPose.addDouble("z", ()-> robotPos.getZ());
        robot3DPose.addDouble("roll", ()-> Math.toDegrees(robotPos.getRotation().getX()));
        robot3DPose.addDouble("pitch", ()-> Math.toDegrees(robotPos.getRotation().getY()));
        robot3DPose.addDouble("yaw", ()-> Math.toDegrees(robotPos.getRotation().getZ()));

    }

    public Pose2d getCurrentPose() {
        return poseEstimator.getEstimatedPosition();
    }

    public List<RingResult> getRingResults(List<Pair<PhotonTrackedTarget, RobotCamera>> rings) {
        List<RingResult> ringResults = new LinkedList<>();

        for (Pair<PhotonTrackedTarget, RobotCamera> ring : rings) {
            ringResults.add(new RingResult(ring.getSecond(),
                    ring.getFirst().getYaw(),
                    ring.getFirst().getPitch(),
                    ring.getFirst().getArea(),
                    getCurrentPose()));
        }

        return ringResults;
    }

    //also update periodically
//    public RingResult getBestRing() {
//        RingResult bestRing = null;
//
//        for(RingResult ring : getRings()) {
//            if (bestRing != null && ring.getDistance() > bestRing.getDistance()) {
//                bestRing = ring;
//            } else if (bestRing == null) {
//                bestRing = ring;
//            }
//        }
//
//        if (bestRing == null) {
//            bestRing = emptyRing;
//        }
//
//        return bestRing;
//    }
}
