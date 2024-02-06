package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.math.PID;
import frc.robot.math.ShooterMath;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.RingResult;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class PickupRingTest extends Command {

    private VisionSubsystem visionSubsystem;
    private SwerveSubsystem swerveSubsystem;
    private LocalizationSubsystem localizationSubsystem;

    private boolean finished = false;

    private RingResult bestRing;

    private PID turnPID = new PID(1.5, 0, 0.1, 3, 0, 0.04, this::turn);

    private PID drivexPID = new PID(0.5, 0, 0, 0.5, 0, 0.05, null);
    private PID driveyPID = new PID(0.5, 0, 0, 0.5, 0, 0.05, null);

    public PickupRingTest(VisionSubsystem visionSubsystem, SwerveSubsystem swerveSubsystem, LocalizationSubsystem localizationSubsystem) {
        this.visionSubsystem = visionSubsystem;
        this.swerveSubsystem = swerveSubsystem;
        this.localizationSubsystem = localizationSubsystem;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        drivexPID = new PID(0.5, 0, 0, 1, 0, 0.05, () -> localizationSubsystem.getCurrentPose().getX());
        driveyPID = new PID(0.5, 0, 0, 1, 0, 0.05, () -> localizationSubsystem.getCurrentPose().getY());

//        bestRing = visionSubsystem.getBestRing();
//
//        if (Double.isNaN(-bestRing.getAngleToHeading())) {
//            finished = true;
//        } else {
//            drivexPID.setGoal(drive().getX());
//            driveyPID.setGoal(drive().getY());
//
//            turnPID.setGoal(ShooterMath.fixSpin(-bestRing.getAngleToHeading() + localizationSubsystem.getCurrentPose().getRotation().getRadians()));
//        }
    }

    @Override
    public void end(boolean interrupted) {
        finished = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void execute() {
        swerveSubsystem.driveFieldOriented(new ChassisSpeeds(drivexPID.calculate(), driveyPID.calculate(), turnPID.calculate()));
    }

    private double turn() {
        return localizationSubsystem.getCurrentPose().getRotation().getRadians();
    }

    private Pose2d drive() {
        Translation2d d = new Translation2d(bestRing.getDistance(), Rotation2d.fromRadians(-bestRing.getAngleToHeading()));

        System.out.println("x: " + d.getX());
        System.out.println("y: " + d.getY());

        return localizationSubsystem.getCurrentPose().plus(new Transform2d(d.getX(), d.getY(), Rotation2d.fromRadians(0)));
    }
}
