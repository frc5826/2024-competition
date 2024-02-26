package frc.robot.positioning;

import edu.wpi.first.wpilibj.DriverStation;

import java.util.Optional;

public class FieldOrientation {

    private static final Orientation blueOrientation = new BlueOrientation();
    private static final Orientation redOrientation = new RedOrientation();

    public static Orientation getOrientation(){
        Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
        if(alliance.isEmpty()){
            System.err.println("WARNING - NO ALLIANCE SET FROM DRIVER STATION - SETTING TO BLUE");
            return blueOrientation;
        } else if (alliance.get().equals(DriverStation.Alliance.Red)) {
            return redOrientation;
        } else if(alliance.get().equals(DriverStation.Alliance.Blue)){
            return blueOrientation;
        } else {
            System.err.println("WARNING - UNKNOWN ALLIANCE FROM DRIVER STATION (" + alliance.get() + ") - SETTING TO BLUE");
            return blueOrientation;
        }
    }

}
