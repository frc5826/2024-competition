package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.math.ElevatorMath;
import frc.robot.math.PID;

import java.util.ArrayList;

public class IntakeElevatorSubsystem extends SubsystemBase {
    private CANSparkMax rotateMotor1, rotateMotor2, extensionMotor;

    private DutyCycleEncoder rotateEncoder, extensionEncoder;

    private PID rotatePID;
    private PID extensionPID;

    private ElevatorMath elevatorMath;

    private Translation2d armTarget;

    public IntakeElevatorSubsystem(){
        this.rotateMotor1 = new CANSparkMax(Constants.cRotateMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        this.rotateMotor2 = new CANSparkMax(Constants.cRotateMotor2ID, CANSparkLowLevel.MotorType.kBrushless);
        this.extensionMotor = new CANSparkMax(Constants.cExtensionMotorID, CANSparkLowLevel.MotorType.kBrushless);

        this.rotateEncoder = new DutyCycleEncoder(Constants.cRotateEncoderID);
        this.extensionEncoder = new DutyCycleEncoder(Constants.cExtensionEncoderID);

        this.rotatePID = new PID(Constants.cRotateP, Constants.cRotateI, Constants.cRotateD, Constants.cRotateMax, Constants.cRotateMin, Constants.cRotateDeadband, this::getArmRotation);
        this.extensionPID = new PID(Constants.cExtensionP, Constants.cExtensionI, Constants.cExtensionD, Constants.cExtensionMax, Constants.cExtensionMin, Constants.cExtensionDeadband, this::getArmExtension);

        this.elevatorMath = new ElevatorMath(Constants.cElevatorMinLength, Constants.cElevatorMaxLength, Constants.cElevatorOrigin, new ArrayList<>());
    }

    @Override
    public void periodic() {
        super.periodic();
        rotatePID.calculate();
        extensionPID.calculate();
    }

    public double getArmExtension(){
        //TODO replace encoder value with encoder call and math
        double encoderValue = -1;
        return elevatorMath.lerp((encoderValue - Constants.minArmEncoderExtension)/(Constants.maxArmEncoderExtension - Constants.minArmEncoderExtension));
    }

    public double getArmRotation(){
        //TODO replace encoder value with encoder call and math
        double encoderValue = -1;
        return elevatorMath.lerp((encoderValue - Constants.minArmEncoderRotation)/(Constants.maxArmEncoderRotation - Constants.minArmEncoderRotation));
    }

    public void setArmTarget(Translation2d point, ElevatorMath.OriginType originType){
        armTarget = elevatorMath.clamp(point, ElevatorMath.PointType.CARTESIAN, originType);
    }

}
