package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.math.ElevatorMath;
import frc.robot.math.PID;

public class IntakeElevatorSubsystem extends SubsystemBase {
    private CANSparkMax rotateMotor1;
    private CANSparkMax rotateMotor2;
    private CANSparkMax extensionMotor;

    private PID rotatePID;
    private PID extensionPID;

    private ElevatorMath elevatorMath;

    public IntakeElevatorSubsystem(){
        this.rotateMotor1 = new CANSparkMax(Constants.cRotateMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        this.rotateMotor2 = new CANSparkMax(Constants.cRotateMotor2ID, CANSparkLowLevel.MotorType.kBrushless);
        this.extensionMotor = new CANSparkMax(Constants.cExtensionMotorID, CANSparkLowLevel.MotorType.kBrushless);

        this.rotatePID = new PID(Constants.cRotateP, Constants.cRotateI, Constants.cRotateD, Constants.cRotateMax, Constants.cRotateMin, Constants.cRotateDeadband, this::getArmRotation);
        this.extensionPID = new PID(Constants.cExtensionP, Constants.cExtensionI, Constants.cExtensionD, Constants.cExtensionMax, Constants.cExtensionMin, Constants.cExtensionDeadband, this::getArmExtension);

        this.elevatorMath = new ElevatorMath(Constants.cElevatorMinLength, Constants.cElevatorMaxLength, Constants.cElevatorOrigin);
    }

    @Override
    public void periodic() {
        super.periodic();
        rotatePID.calculate();
        extensionPID.calculate();
    }

    public double getArmExtension(){
        return 0;
    }

    public double getArmRotation(){
        return 0;
    }


}
