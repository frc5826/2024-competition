package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.math.PID;
import frc.robot.math.ShooterMath;
import frc.robot.subsystems.LocalizationSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import javax.swing.text.StyledEditorKit;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import static frc.robot.Constants.*;

public class TeleopDriveCommand extends Command {

    private final SwerveSubsystem swerveSubsystem;
    private final LocalizationSubsystem localizationSubsystem;

    private final DoubleSupplier x;
    private final DoubleSupplier y;
    private final DoubleSupplier angleVel;
    private final BooleanSupplier xboxXButton;

    //TODO
    private final PID targetTurnPID = new PID(1, 0, 0, 6, 0, 0.05, this::getAngleDiff);
    private final PID turnPID = new PID(Constants.cTurnPID, 6, 0, 0.05, this::getAngleDiff);

    public TeleopDriveCommand(SwerveSubsystem swerveSubsystem, LocalizationSubsystem localizationSubsystem,
                              DoubleSupplier x, DoubleSupplier y, DoubleSupplier angleVel, BooleanSupplier xboxXButton) {

        this.localizationSubsystem = localizationSubsystem;
        this.swerveSubsystem = swerveSubsystem;

        this.xboxXButton = xboxXButton;
        this.x = x;
        this.y = y;
        this.angleVel = angleVel;

        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        turnPID.setGoal(0);
    }

    @Override
    public void execute() {

        double x0 = x.getAsDouble();
        double y0 = y.getAsDouble();
        double angleV = angleVel.getAsDouble();

        //TODO
//        if (xboxXButton.getAsBoolean()) {
//            angleV = turnPID.calculate();
//        }

        double bandedx = Math.abs(x0) < cDriveDeadband ? 0 : x0;
        double bandedy = Math.abs(y0) < cDriveDeadband ? 0 : y0;

        double bandedAngle = Math.abs(angleV) < cTurnDeadband ? 0 : angleV;

        ChassisSpeeds speeds = new ChassisSpeeds(bandedx * swerveSubsystem.maximumSpeed,
                bandedy * swerveSubsystem.maximumSpeed,
                bandedAngle * swerveSubsystem.maximumAngularVel);

        swerveSubsystem.driveFieldOriented(speeds);

    }

    private double getAngleDiff() {
        return ShooterMath.fixSpin(
                localizationSubsystem.getCurrentPose().getRotation().getRadians() -
                        ShooterMath.getAngleToSpeaker(localizationSubsystem.getCurrentPose()));
    }

}
