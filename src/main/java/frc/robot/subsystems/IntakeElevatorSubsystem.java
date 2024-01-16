package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.sun.jdi.request.ModificationWatchpointRequest;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.lang.reflect.Modifier;

public class IntakeElevatorSubsystem extends SubsystemBase {
    private CANSparkMax rotateMotor1;
    private CANSparkMax rotateMotor2;
    private CANSparkMax extensionMotor;

    private PID rotatePID;
    private PID extensionPID;

    public IntakeElevatorSubsystem(){
        this.rotateMotor1 = new CANSparkMax(Constants.cRotateMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        this.rotateMotor2 = new CANSparkMax(Constants.cRotateMotor2ID, CANSparkLowLevel.MotorType.kBrushless);
        this.extensionMotor = new CANSparkMax(Constants.cExtensionMotorID, CANSparkLowLevel.MotorType.kBrushless);

        this.rotatePID = new PID(Constants.cRotateP, Constants.cRotateI, Constants.cRotateD, Constants.cRotateMax, Constants.cRotateMin, Constants.cRotateDeadband);
        this.extensionPID = new PID(Constants.cExtensionP, Constants.cExtensionI, Constants.cExtensionD, Constants.cExtensionMax, Constants.cExtensionMin, Constants.cExtensionDeadband);
    }

    public void setRotateMotorPosition(){

    }

}
