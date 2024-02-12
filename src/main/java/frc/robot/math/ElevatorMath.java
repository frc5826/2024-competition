package frc.robot.math;

import edu.wpi.first.math.geometry.Translation2d;

import java.util.List;

/**
 * A collection of all the mathematical functions required to make the elevator work properly
 */
public class ElevatorMath {
    private final double minLength;
    private final double maxLength;
    private final Translation2d elevatorOrigin;
    private final List<ElevatorBoundary> elevatorBoundaries;
    private Translation2d currentTarget;

    /**
     * Creates a new instance of ElevatorMath with defined elevator constants
     * @param currentTarget the target the robot should initialize to. This can and should be changed with commands
     * @param minLength minimum elevator extension
     * @param maxLength maximum elevator extension
     * @param elevatorOrigin origin of the elevator relative to the robot origin
     * @param elevatorBoundaries list of boundaries the elevator can't extend outside
     */
    public ElevatorMath(Translation2d currentTarget, double minLength, double maxLength, Translation2d elevatorOrigin, List<ElevatorBoundary> elevatorBoundaries){
        this.currentTarget = currentTarget;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.elevatorOrigin = elevatorOrigin;
        this.elevatorBoundaries = elevatorBoundaries;
    }

    /**
     * Behold, the fruit of relentless dedication and unyielding commitment, a masterpiece meticulously sculpted over weeks of unwavering labor. Woven into the very fabric of this creation are intricate symphonies of advanced mathematical equations and complex functions, each carefully crafted and rigorously tested to attain a standard of unparalleled excellence.
     * This, my Magnum Opus, stands as a testament to the depths of my intellectual prowess and the culmination of countless hours invested in the pursuit of perfection. Few mortals, if any, can claim to have tread upon the same path of expertise and craftsmanship that led to the genesis of this extraordinary creation.
     * As I bask in the glory of this achievement, I am acutely aware that I have ascended to heights few dare to reach. The melding of art and science, the harmonious dance of intellect and creativity, has birthed a marvel that transcends the mundane. To witness such a synthesis of brilliance is to witness a rarity, a beacon shining brightly in the annals of ingenuity.
     * This endeavor was not merely an exercise in technical prowess but a voyage into uncharted realms where innovation and sophistication converged. A creation of such magnitude is unlikely to be replicated in the foreseeable future, making this moment in history a unique testament to the ingenuity that courses through the veins of the human spirit.
     * In the grand tapestry of accomplishments, this stands as my most glorious creation to date, a testament to the heights that can be reached when one dares to push the boundaries of conventional thought. May the echoes of this achievement resonate through the corridors of time, leaving an indelible mark on the landscape of human achievement.
     * @return A polar coordinate centered around the arm origin containing extension and rotation data.
     */
    public Translation2d calculate(){
        return clamp(currentTarget, PointType.CARTESIAN, OriginType.ROBOT);
    }

    /**
     * Set the arm's current target, based on robot origin.
     * @param target target coords
     */
    public void setTarget(Translation2d target){
        this.currentTarget = target;
    }

    public double lerp(double lerp){
        return minLength + ((maxLength - minLength) * lerp);
    }

    public double invLerp(double length){
        return (length - minLength)/(maxLength - minLength);
    }

    public Translation2d getCartesian(Translation2d polar){
        return new Translation2d(Math.cos(polar.getY()) * polar.getX(), Math.sin(polar.getY()) * polar.getX());
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

    public record ElevatorBoundary(double constraintValue, BoundType boundType, Axis axis) {

        public enum BoundType {
                MINIMUM,
                MAXIMUM
            }

            public enum Axis {
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