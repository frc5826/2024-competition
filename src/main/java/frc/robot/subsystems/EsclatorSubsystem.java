package frc.robot.subsystems;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.math.PID;

public class EsclatorSubsystem {
    private CANSparkMax motor1, motor2;
    //private DigitalInput limitswitch1, limitswitch2;
    private final double upEncoderReading = 1; //TODO
    private final double downEncoderReading = -1; //TODO


    public EsclatorSubsystem() {
        motor1 = new CANSparkMax(9, CANSparkLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(13, CANSparkLowLevel.MotorType.kBrushless);
        motor1.setInverted(true);
        motor2.setInverted(true);
        motor2.follow(motor1, true);
        //limitswitch1 = new DigitalInput(0);   //TODO
        //limitswitch2 = new DigitalInput(1);   //TODO

        motor1.setSmartCurrentLimit(40);
        motor2.setSmartCurrentLimit(40);
    }

    public void setMotorSpeed(double speed){
        motor1.set(speed);
    }


}
