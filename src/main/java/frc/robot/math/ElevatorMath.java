package frc.robot.math;

import edu.wpi.first.math.geometry.Translation2d;

import java.util.List;

/**
 * A collection of all the functions required to make the elevator work properly
 */
public class ElevatorMath {
    private final double minLength;
    private final double maxLength;
    private final Translation2d elevatorOrigin;
    private final List<ElevatorBoundary> elevatorBoundaries;

    public ElevatorMath(double minLength, double maxLength, Translation2d elevatorOrigin, List<ElevatorBoundary> elevatorBoundaries){
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.elevatorOrigin = elevatorOrigin;
        this.elevatorBoundaries = elevatorBoundaries;
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

    /**
     * Clamps the desired elevator point to both arm extension constraints and boundaries defined in the elevatorBoundaries list.
     * !! ELEVATOR BOUNDARIES ARE CENTERED AROUND ROBOT ORIGIN !!
     * @param point Unclamped input point
     * @param pointType Type of point being used (Polar or Cartesian)
     * @param originType Type of origin being used (Robot or Arm)
     * @return a point clamped to within defined boundaries
     */
    public Translation2d clamp(Translation2d point, PointType pointType, OriginType originType){
        Translation2d newPoint;
        if (pointType == PointType.POLAR){
            point = getCartesian(point);
        }

        if (originType == OriginType.ELEVATOR){
            point = getRobotRelativeCoordinate(point, PointType.CARTESIAN);
        }

        newPoint = point;

        for (ElevatorBoundary elevatorBoundary : elevatorBoundaries) {
            if (elevatorBoundary.axis == ElevatorBoundary.Axis.X){
                if (
                    newPoint.getX() < elevatorBoundary.constraintValue && elevatorBoundary.boundType == ElevatorBoundary.BoundType.MINIMUM ||
                    newPoint.getX() > elevatorBoundary.constraintValue && elevatorBoundary.boundType == ElevatorBoundary.BoundType.MAXIMUM
                ){
                    newPoint = new Translation2d(elevatorBoundary.constraintValue, newPoint.getY());
                }
            }
            else if (elevatorBoundary.axis == ElevatorBoundary.Axis.Y){
                if (
                    newPoint.getY() < elevatorBoundary.constraintValue && elevatorBoundary.boundType == ElevatorBoundary.BoundType.MINIMUM ||
                    newPoint.getY() > elevatorBoundary.constraintValue && elevatorBoundary.boundType == ElevatorBoundary.BoundType.MAXIMUM
                ){
                    newPoint = new Translation2d(newPoint.getX(), elevatorBoundary.constraintValue);
                }
            }
        }

        newPoint = getPolar(newPoint);

        if (originType == OriginType.ELEVATOR){
            newPoint = getElevatorRelativeCoordinate(newPoint, PointType.POLAR);
        }

        newPoint = new Translation2d(Math.max(Math.min(newPoint.getX(), maxLength), minLength), newPoint.getY());

        if(pointType == PointType.CARTESIAN){
            newPoint = getCartesian(newPoint);
        }
        return newPoint;
    }

    public Translation2d getRobotRelativeCoordinate(Translation2d point, PointType pointType){
        Translation2d newPoint;
        if (pointType == PointType.POLAR){
            point = getCartesian(point);
        }

        newPoint = new Translation2d(point.getX() + elevatorOrigin.getX(), point.getY() + elevatorOrigin.getY());

        if (pointType == PointType.POLAR){
            newPoint = getPolar(newPoint);
        }
        return newPoint;
    }

    public Translation2d getElevatorRelativeCoordinate(Translation2d point, PointType pointType){
        Translation2d newPoint;
        if (pointType == PointType.POLAR){
            point = getCartesian(point);
        }
        newPoint = new Translation2d(point.getX() - elevatorOrigin.getX(), point.getY() - elevatorOrigin.getY());

        if (pointType == PointType.POLAR){
            newPoint = getPolar(newPoint);
        }
        return newPoint;
    }

    public static class ElevatorBoundary {

        private final double constraintValue;
        private final BoundType boundType;
        private final Axis axis;


        public ElevatorBoundary(double constraintValue, BoundType boundType, Axis axis){

            this.constraintValue = constraintValue;
            this.axis = axis;
            this.boundType = boundType;

        }

        public Axis getAxis() {
            return axis;
        }

        public BoundType getBoundType() {
            return boundType;
        }

        public double getConstraintValue() {
            return constraintValue;
        }

        public enum BoundType{
            MINIMUM,
            MAXIMUM
        }

        public enum Axis{
            X,
            Y
        }
    }

    public enum PointType {
        CARTESIAN,
        POLAR
    }

    public enum OriginType {
        ELEVATOR,
        ROBOT
    }

}