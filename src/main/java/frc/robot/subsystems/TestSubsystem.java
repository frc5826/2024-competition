package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TestSubsystem extends LoggedSubsystem {
    public Command getCommand(){
        var c = new WaitCommand(1);
        c.addRequirements(this);
        return c;
    }
}
