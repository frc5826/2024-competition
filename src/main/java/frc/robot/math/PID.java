package frc.robot.math;


import java.util.function.DoubleSupplier;

public class PID {
    double P,I,D;
    double previous_error, integral = 0;
    double setpoint = 0;
    double error;
    private double output;

    private final DoubleSupplier actualSupplier;

    private double min_output = 0;
    private double max_output = 1;
    private double deadband = -1;

    public PID(double P, double I, double D, double max, double min, double deadband, DoubleSupplier actualSupplier){
        this.P = P;
        this.I = I;
        this.D = D;
        this.max_output = max;
        this.min_output = min;
        this.deadband = deadband;
        this.actualSupplier = actualSupplier;
    }

    public double calculate(double actual) {
        error = setpoint - actual; // Error = Target - Actual
        this.integral += (error * .02); // Integral is increased by the error*time (which is .02 seconds using normal
        final double derivative = (error - this.previous_error) / .02;
        this.output = P * error + I * this.integral + D * derivative;
        this.previous_error = error;

        return getOutput();
    }

    public double calculate() {
        error = setpoint - actualSupplier.getAsDouble(); // Error = Target - Actual
        this.integral += (error * .02); // Integral is increased by the error*time (which is .02 seconds using normal
        final double derivative = (error - this.previous_error) / .02;
        this.output = P * error + I * this.integral + D * derivative;
        this.previous_error = error;

        return getOutput();
    }

    public void setGoal(double setpoint){
        this.setpoint = setpoint;
    }

    public double getOutput(){
        if(deadband != -1 && Math.abs(error) < deadband){
            return 0;
        }
        else if(Math.abs(output) > max_output){
            return output > 0 ? max_output : -max_output;
        }
        else if(Math.abs(output) < min_output){
            return output > 0 ? min_output : -min_output;
        }
        else {
            return output;
        }
    }

    public double getError() {
        return error;
    }

    //TODO add integral resetter
    //Like that's ever happening

    @Override
    public String toString() {
        return "PID{" +
                "P=" + P +
                ", I=" + I +
                ", D=" + D +
                ", error=" + error +
                ", output=" + output +
                ", min_output=" + min_output +
                ", max_output=" + max_output +
                ", deadband=" + deadband +
                '}';
    }
}
