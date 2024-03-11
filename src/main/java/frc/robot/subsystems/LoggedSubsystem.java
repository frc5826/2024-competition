package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LoggedSubsystem extends SubsystemBase {
    StringPublisher currentCommand = NetworkTableInstance.getDefault().
            getStringTopic(getName()).publish();

    @Override
    public void periodic() {
        if (getCurrentCommand()==null) {
            currentCommand.set("");
        } else if (getCurrentCommand().getName().
                equals(currentCommand.getTopic().getEntry("").get())) {
            currentCommand.set(getCurrentCommand().getName());
        }
    }
}
