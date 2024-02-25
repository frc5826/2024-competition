package frc.robot.subsystems;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.math.Mth;
import frc.robot.math.PID;

import static frc.robot.Constants.*;

public class ArmSubsystem extends SubsystemBase {

    private CANSparkMax rotateMotor, rotateMotorSecondary, extendMotor, wristMotor;

    private DutyCycleEncoder rotateEncoder, extendEncoder, wristEncoder;

    private PID rotatePID, extendPID, wristPID;

    private double desiredExtensionRotations, desiredArmRotations, desiredWristRotations;

    public ArmSubsystem() {
        ShuffleboardTab tab = Shuffleboard.getTab("ARM");

        rotateMotor = new CANSparkMax(rotateMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        rotateMotorSecondary = new CANSparkMax(rotateMotor2ID, CANSparkLowLevel.MotorType.kBrushless);
        extendMotor = new CANSparkMax(extendMotorID, CANSparkLowLevel.MotorType.kBrushless);
        wristMotor = new CANSparkMax(ankleMotorID, CANSparkLowLevel.MotorType.kBrushless);

        rotateMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);
        rotateMotorSecondary.setIdleMode(CANSparkBase.IdleMode.kBrake);
        extendMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);
        wristMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);

        rotateMotor.setInverted(false);
        rotateMotorSecondary.setInverted(false);
        extendMotor.setInverted(false);
        wristMotor.setInverted(true);

        rotateMotor.setSmartCurrentLimit(40);
        rotateMotorSecondary.setSmartCurrentLimit(40);
        extendMotor.setSmartCurrentLimit(40);
        wristMotor.setSmartCurrentLimit(40);

        rotateMotorSecondary.follow(rotateMotor);

        rotateEncoder = new DutyCycleEncoder(rotateEncoderID);
        extendEncoder = new DutyCycleEncoder(extendEncoderID);
        wristEncoder = new DutyCycleEncoder(ankleEncoderID);

        rotatePID = new PID(cRotateP, cRotateI, cRotateD, cRotateMax, cRotateMin, cRotateDeadband, this::getRotation);
        extendPID = new PID(cExtendP, cExtendI, cExtendD, cExtendMax, cExtendMin, cExtendDeadband, this::getExtension);
        wristPID = new PID(cWristP, cWristI, cWristD, cWristMax, cWristMin, cWristDeadband, this::getWrist);

        setExtensionHome();
        setArmHome();
        setWristHome();

        tab.add("Rotation Encoder", rotateEncoder);
        tab.add("Extension Encoder", extendEncoder);
        tab.add("Ankle Encoder", wristEncoder);

        tab.addNumber("Rotation", this::getRotation);
        tab.addNumber("Extension", this::getExtension);
        tab.addNumber("Ankle", this::getWrist);

        tab.addNumber("Absolute Rotation", this::getRotationAbsolute);
        tab.addNumber("Absolute Extension", this::getExtensionAbsolute);
        tab.addNumber("Ankle Absolute", this::getWristAbsolute);

        tab.addNumber("Arm Desired Rotation", () -> desiredArmRotations);
        tab.addNumber("Arm Desired Extension", () -> desiredExtensionRotations);
        tab.addNumber("Ankle Desired Rotation", () -> desiredWristRotations);
    }

    @Override
    public void periodic() {
        super.periodic();
        rotatePID.setGoal(desiredArmRotations);
        extendPID.setGoal(desiredExtensionRotations);
        wristPID.setGoal(desiredWristRotations);

        setRotateSpeed(Mth.clamp(rotatePID.calculate(), -0.8, 0.8));
        setExtendSpeed(Mth.clamp(extendPID.calculate(), -0.8, 0.1));
        setWristSpeed(Mth.clamp(wristPID.calculate(), -0.2, 0.3));
    }


    public void setDesiredExtension(double desiredExtensionMeters){
        double desiredExtensionClamped = Mth.clamp(desiredExtensionMeters, 0, 0.5);
        desiredExtensionRotations = (-9.4316 * desiredExtensionClamped) + 0.32;

    }

    public void setExtensionHome(){
        setDesiredExtension(0);
    }

    public void setDesiredArmAngle(double armAngleRAD){
        double clampedAngle = Mth.clamp(armAngleRAD, 0, Math.toRadians(110));
        desiredArmRotations = (0.161 * clampedAngle) + 0.143;
    }

    public void setArmHome(){
        desiredArmRotations = 0.1439;
    }

    //TODO add range
    public void setDesiredWristAngle(double ankleAngleRAD){
        desiredWristRotations = (0.1536 * ankleAngleRAD) + 0.4095;
    }

    public void setWristHome(){
        setDesiredWristAngle(Math.toRadians(120));
    }

    public void setRotateSpeed(double speed){
        rotateMotor.set(speed);
    }

    public void setExtendSpeed(double speed){
        extendMotor.set(speed);
    }

    public void setWristSpeed(double speed){
        wristMotor.set(speed);
    }

    public double getRotation(){
        return rotateEncoder.get();
    }
    public double getExtension(){
        return extendEncoder.get();
    }
    public double getWrist(){
        return wristEncoder.get();
    }

    public double getRotationAbsolute(){
        return rotateEncoder.getAbsolutePosition();
    }
    public double getExtensionAbsolute(){
        return extendEncoder.getAbsolutePosition();
    }
    public double getWristAbsolute(){
        return wristEncoder.getAbsolutePosition();
    }

    public double getDesiredArmRotations() {
        return desiredArmRotations;
    }

    public double getDesiredExtensionRotations() {
        return desiredExtensionRotations;
    }

    public double getDesiredWristRotations() {
        return desiredWristRotations;
    }
}
