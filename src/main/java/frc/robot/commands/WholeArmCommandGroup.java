package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LegSubsystem;

import java.util.Optional;

public class WholeArmCommandGroup extends SequentialCommandGroup {

    public WholeArmCommandGroup(LegSubsystem legSubsystem, Optional<Double> armAngle, Optional<Double> extensionMeters, Optional<Double> ankleAngle, MoveFirst order) {
        switch (order){
            case ROTATE:
                addCommands(
                    new RotateArmToAngleCommand(armAngle, legSubsystem),
                    new WaitCommand(5),
                    new ExtendToLengthCommand(extensionMeters, legSubsystem),
                    new WaitCommand(5),
                    new RotateAnkleCommand(ankleAngle, legSubsystem)
                );
                break;
            case EXTEND:
                addCommands(
                    new ExtendToLengthCommand(extensionMeters, legSubsystem),
                    new WaitCommand(5),
                    new RotateArmToAngleCommand(armAngle, legSubsystem),
                    new WaitCommand(5),
                    new RotateAnkleCommand(ankleAngle, legSubsystem)
                );
                break;
            case TOP_DOWN:
                //TODO MAKE THIS
                break;
        }
    }

    public enum MoveFirst{
        ROTATE,
        EXTEND,
        TOP_DOWN
    }

}
