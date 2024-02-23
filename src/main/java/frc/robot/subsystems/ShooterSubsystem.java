package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {

    CANSparkMax shooterMotor1, shooterMotor2;
    WPI_TalonSRX shooterControlMotor;
    DigitalInput beamBreak;

    public ShooterSubsystem() {

        ShuffleboardTab tab = Shuffleboard.getTab("ARM");

        shooterMotor1 = new CANSparkMax(shooterMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        shooterMotor2 = new CANSparkMax(shooterMotor2ID, CANSparkLowLevel.MotorType.kBrushless);

        shooterMotor1.setIdleMode(CANSparkBase.IdleMode.kBrake);
        shooterMotor2.setIdleMode(CANSparkBase.IdleMode.kBrake);

        shooterMotor1.setInverted(false);
        shooterMotor2.setInverted(true);

        shooterControlMotor = new WPI_TalonSRX(shooterControlMotorID);

        shooterControlMotor.setNeutralMode(NeutralMode.Brake);
        shooterControlMotor.setInverted(true);


        beamBreak = new DigitalInput(beamBreakID);

        tab.addBoolean("BeamBreak", this::getBeamBreak).withPosition(0, 2);
    }

    public void setShooterSpeed(double speed){
        shooterMotor1.set(speed);
        shooterMotor2.set(speed);
    }

    public CANSparkMax getShooterMotor1() {
        return shooterMotor1;
    }

    public CANSparkMax getShooterMotor2() {
        return shooterMotor2;
    }

    public WPI_TalonSRX getShooterControlMotor() {
        return shooterControlMotor;
    }

    public void setShooterControlSpeed(double speed){
        shooterControlMotor.set(speed);
    }

    public boolean getBeamBreak(){
        return beamBreak.get();
    }
}
