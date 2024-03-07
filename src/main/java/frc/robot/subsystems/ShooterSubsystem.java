package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import static frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {

    SimpleWidget inp1, inp2, bothInput;

    CANSparkMax shooterMotor1, shooterMotor2;
    WPI_TalonSRX shooterControlMotor;
    DigitalInput beamBreak;

    private boolean hasRing;

    public ShooterSubsystem() {

        ShuffleboardTab tab = Shuffleboard.getTab("SHOOTER");

        shooterMotor1 = new CANSparkMax(shooterMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        shooterMotor2 = new CANSparkMax(shooterMotor2ID, CANSparkLowLevel.MotorType.kBrushless);

        shooterMotor1.setIdleMode(CANSparkBase.IdleMode.kBrake);
        shooterMotor2.setIdleMode(CANSparkBase.IdleMode.kBrake);

        shooterMotor1.setInverted(false);
        shooterMotor2.setInverted(false);

        shooterMotor1.restoreFactoryDefaults();
        shooterMotor2.restoreFactoryDefaults();

        shooterMotor1.getPIDController().setP(24e-5);
        shooterMotor2.getPIDController().setP(24e-5);

        shooterMotor1.getPIDController().setI(1e-6);
        shooterMotor2.getPIDController().setI(1e-6);

        shooterMotor1.getPIDController().setD(4e-6);
        shooterMotor2.getPIDController().setD(4e-6);

        shooterMotor1.getPIDController().setFF(30e-6);
        shooterMotor2.getPIDController().setFF(30e-6);

        shooterMotor1.getPIDController().setIZone(0);
        shooterMotor2.getPIDController().setIZone(0);

        shooterMotor1.getPIDController().setOutputRange(-1, 1);
        shooterMotor2.getPIDController().setOutputRange(-1, 1);


        shooterControlMotor = new WPI_TalonSRX(shooterControlMotorID);

        shooterControlMotor.setNeutralMode(NeutralMode.Brake);
        shooterControlMotor.setInverted(false);


        beamBreak = new DigitalInput(beamBreakID);

        tab.addBoolean("BeamBreak", this::getBeamBreak).withPosition(0, 2);
        tab.addNumber("1 Speed", this::getShooterMotor1Speed);
        tab.addNumber("2 Speed", this::getShooterMotor2Speed);
        inp1 = tab.add("1 Input", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -5700, "max", 5700)).withProperties(Map.of("publish_all", true));
        inp2 = tab.add("2 Input", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -5700, "max", 5700)).withProperties(Map.of("publish_all", true));
        bothInput = tab.add("both", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -5700, "max", 5700)).withProperties(Map.of("publish_all", true));
    }

    @Override
    public void periodic() {
        super.periodic();
        if (bothInput.getEntry().getDouble(0) == 0) {
            shooterMotor1.getPIDController().setReference(inp1.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
            shooterMotor2.getPIDController().setReference(inp2.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
        } else {
            shooterMotor1.getPIDController().setReference(-bothInput.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
            shooterMotor2.getPIDController().setReference(bothInput.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
        }

    }

    public void setShooterOutput(double speed){
        setShooterOutput(speed, true, true);
    }

    public void setShooterOutput(double speed, boolean top, boolean bottom){
        if(top) {
            shooterMotor1.set(speed);
        }
        if(bottom) {
            shooterMotor2.set(speed);
        }
    }

    public void setShooterSpeed(double speed){
        shooterMotor1.getPIDController().setReference(speed, CANSparkBase.ControlType.kVelocity);
        shooterMotor2.getPIDController().setReference(speed, CANSparkBase.ControlType.kVelocity);
    }

    public CANSparkMax getShooterMotor1() {
        return shooterMotor1;
    }

    public CANSparkMax getShooterMotor2() {
        return shooterMotor2;
    }

    public double getShooterMotor1Speed(){
        return shooterMotor1.getEncoder().getVelocity();
    }

    public double getShooterMotor2Speed(){
        return shooterMotor2.getEncoder().getVelocity();
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

    public void setHasRing(boolean doesIt) {
        hasRing = doesIt;
    }

    public boolean getHasRing() {
        return hasRing;
    }
}
