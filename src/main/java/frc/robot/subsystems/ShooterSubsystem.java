package frc.robot.subsystems;

import com.revrobotics.*;
//import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import static frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {

    SimpleWidget inp1, inp2, bothInput;

    CANSparkMax shooterMotorTop, shooterMotorBottom, shooterControlMotor;
    DigitalInput beamBreak;

    private boolean hasRing;

    public ShooterSubsystem() {

        ShuffleboardTab tab = Shuffleboard.getTab("SHOOTER");

        shooterMotorTop = new CANSparkMax(shooterMotor1ID, CANSparkLowLevel.MotorType.kBrushless);
        shooterMotorBottom = new CANSparkMax(shooterMotor2ID, CANSparkLowLevel.MotorType.kBrushless);

        shooterMotorTop.restoreFactoryDefaults();
        shooterMotorBottom.restoreFactoryDefaults();

        shooterMotorTop.setIdleMode(CANSparkBase.IdleMode.kBrake);
        shooterMotorBottom.setIdleMode(CANSparkBase.IdleMode.kBrake);

        shooterMotorTop.setInverted(false);
        shooterMotorBottom.setInverted(true);

        shooterMotorTop.getPIDController().setP(24e-5);
        shooterMotorBottom.getPIDController().setP(24e-5);

        shooterMotorTop.getPIDController().setI(1e-6);
        shooterMotorBottom.getPIDController().setI(1e-6);

        shooterMotorTop.getPIDController().setD(4e-6);
        shooterMotorBottom.getPIDController().setD(4e-6);

        shooterMotorTop.getPIDController().setFF(30e-6);
        shooterMotorBottom.getPIDController().setFF(30e-6);

        shooterMotorTop.getPIDController().setIZone(0);
        shooterMotorBottom.getPIDController().setIZone(0);

        shooterMotorTop.getPIDController().setOutputRange(-1, 1);
        shooterMotorBottom.getPIDController().setOutputRange(-1, 1);

        shooterControlMotor = new CANSparkMax(6, CANSparkMax.MotorType.kBrushless);

        shooterControlMotor.setIdleMode(CANSparkBase.IdleMode.kBrake);
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
//        if (bothInput.getEntry().getDouble(0) == 0) {
//            shooterMotor1.getPIDController().setReference(inp1.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
//            shooterMotor2.getPIDController().setReference(inp2.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
//        } else {
//            shooterMotor1.getPIDController().setReference(-bothInput.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
//            shooterMotor2.getPIDController().setReference(bothInput.getEntry().getDouble(0), CANSparkBase.ControlType.kVelocity);
//        }

        if (panelButtons[11].getAsBoolean()) {
            shooterMotorTop.getPIDController().setReference(5700, CANSparkBase.ControlType.kVelocity);
            shooterMotorBottom.getPIDController().setReference(5700, CANSparkBase.ControlType.kVelocity);
        } else if (panelButtons[10].getAsBoolean()){
            shooterMotorTop.getPIDController().setReference(-5700, CANSparkBase.ControlType.kVelocity);
            shooterMotorBottom.getPIDController().setReference(-5700, CANSparkBase.ControlType.kVelocity);
        } else {
            shooterMotorTop.getPIDController().setReference(0, CANSparkBase.ControlType.kVelocity);
            shooterMotorBottom.getPIDController().setReference(0, CANSparkBase.ControlType.kVelocity);
        }

        if (panelButtons[6].getAsBoolean()) {
            shooterControlMotor.set(.25);
        } else if (panelButtons[3].getAsBoolean()) {
            shooterControlMotor.set(-.25);
        } else {
            shooterControlMotor.set(0);
        }

    }

    public void setShooterOutput(double speed){
        setShooterOutput(speed, true, true);
    }

    public void setShooterOutput(double speed, boolean top, boolean bottom){
        if(top) {
            shooterMotorTop.set(speed);
        }
        if(bottom) {
            shooterMotorBottom.set(speed);
        }
    }

    public void setShooterSpeed(double speed){
        shooterMotorTop.getPIDController().setReference(speed, CANSparkBase.ControlType.kVelocity);
        shooterMotorBottom.getPIDController().setReference(speed, CANSparkBase.ControlType.kVelocity);
    }

    public CANSparkMax getShooterMotorTop() {
        return shooterMotorTop;
    }

    public CANSparkMax getShooterMotorBottom() {
        return shooterMotorBottom;
    }

    public double getShooterMotor1Speed(){
        return shooterMotorTop.getEncoder().getVelocity();
    }

    public double getShooterMotor2Speed(){
        return shooterMotorBottom.getEncoder().getVelocity();
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
