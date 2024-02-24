package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

public class ClimberSubsystem {
    private CANSparkMax motor1, motor2;
    private DigitalInput limitswitch1, limitswitch2;

    public ClimberSubsystem() {
        motor1 = new CANSparkMax(9, CANSparkLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(13, CANSparkLowLevel.MotorType.kBrushless);
        motor1.setInverted(true);
        motor2.setInverted(true);
        motor2.follow(motor1, true);
        limitswitch1 = new DigitalInput(4);   //TODO
        limitswitch2 = new DigitalInput(5);   //TODO

        motor1.setSmartCurrentLimit(40);
        motor2.setSmartCurrentLimit(40);
    }

    public void setMotorSpeed(double speed){
        motor1.set(speed);
    }


}
