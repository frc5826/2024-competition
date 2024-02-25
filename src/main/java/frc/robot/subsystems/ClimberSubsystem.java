package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private CANSparkMax motor1, motor2;
    private DigitalInput limitswitch1, limitswitch2;

    public ClimberSubsystem() {

        ShuffleboardTab tab = Shuffleboard.getTab("CLIMBER");

        motor1 = new CANSparkMax(9, CANSparkLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(13, CANSparkLowLevel.MotorType.kBrushless);
        motor1.setInverted(false);
        motor2.setInverted(true);
        limitswitch1 = new DigitalInput(4);
        limitswitch2 = new DigitalInput(5);

        motor1.setSmartCurrentLimit(80);
        motor2.setSmartCurrentLimit(80);

        tab.addBoolean("Left Switch", this::getLeftLimitSwitch);
        tab.addBoolean("Right Switch", this::getRightLimitSwitch);
    }

    public void setRightMotorSpeed(double speed){
        motor1.set(speed);
    }

    public void setLeftMotorSpeed(double speed){
        motor2.set(speed);
    }

    public boolean getRightLimitSwitch(){
        return limitswitch1.get();
    }
    public boolean getLeftLimitSwitch() {
        return limitswitch2.get();
    }

}
