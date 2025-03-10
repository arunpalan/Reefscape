package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// also see Command get imported... if the above gives issues, try this
// import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.util.Util;
import frc.robot.Constants.JoystickConstants;

public class DefaultElevatorCommand extends CommandBase {
    private final Elevator elevator;
    private final XboxController operator;

    public DefaultElevatorCommand(Elevator elevator, XboxController operator) {
        this.elevator = elevator;
        this.operator = operator;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        // Determine whether left joystick is positive or negative to raise or lower elevator
        double elevator_value = Util.applyDeadband(operator.getLeftX(), JoystickConstants.DEADBAND);

        if (elevator_value > 0.0) {
            elevator.raiseElevator();
        } else if (elevator_value < 0.0) {
            elevator.lowerElevator();
        } else {
            elevator.commandDefault();
            // Can change this to stop elevator depending on operator preference
            // elevator.stopElevator();
        }
        
    }

}
