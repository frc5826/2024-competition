package frc.robot.math;

import edu.wpi.first.math.geometry.Translation2d;

public class ElevatorMath {
    private double minLength, maxLength;
    private Translation2d elevatorOrigin;

    public ElevatorMath(double minLength, double maxLength, Translation2d elevatorOrigin){
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.elevatorOrigin = elevatorOrigin;

    }

    public double lerp(double lerp){
        return minLength + ((maxLength - minLength) * lerp);
    }

    public double invLerp(double length){
        return (length - minLength)/(maxLength - minLength);
    }

    public Translation2d getCartesian(double len, double angle){
        return new Translation2d(Math.cos(angle) * len, Math.sin(angle) * len);
    }
    public Translation2d getCartesian(Translation2d polar){
        return new Translation2d(Math.cos(polar.getY()) * polar.getX(), Math.sin(polar.getY()) * polar.getX());
    }

    public Translation2d getPolar(double x, double y){
        return new Translation2d(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), Math.atan2(y, x));
    }

    public Translation2d getPolar(Translation2d point){
        return new Translation2d(Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2)), Math.atan2(point.getY(), point.getX()));
    }

    public Translation2d clamp(Translation2d point, PointType inputType, PointType outputType){
        Translation2d newPoint;
        if (inputType == PointType.CARTESIAN){
            point = getPolar(point);
        }
        newPoint = new Translation2d(Math.max(Math.min(point.getX(), maxLength), minLength), point.getY());
        if(outputType == PointType.CARTESIAN){
            newPoint = getCartesian(newPoint);
        }
        return newPoint;
    }

    public Translation2d getRobotRelativeCoordinate(Translation2d point, PointType inputType){
        if (inputType == PointType.POLAR){
            point = getCartesian(point);
        }
        return new Translation2d(point.getX() + elevatorOrigin.getX(), point.getY() + elevatorOrigin.getY());
    }

    public Translation2d getElevatorRelativeCoordinate(Translation2d point, PointType inputType){
        if (inputType == PointType.POLAR){
            point = getCartesian(point);
        }
        return new Translation2d(point.getX() - elevatorOrigin.getX(), point.getY() - elevatorOrigin.getY());
    }

    public enum PointType {
        CARTESIAN,
        POLAR
    }

}