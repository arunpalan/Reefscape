package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// also see Command get imported... if the above gives issues, try this
// import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.util.Util;
import frc.robot.Constants.JoystickConstants;

public class ClimberCommand extends CommandBase {
    private final Climber climber;
    private final XboxController operator;

    public ClimberCommand(Climber climber, XboxController operator) {
        this.climber = climber;
        this.operator = operator;
        addRequirements(climber);
    }

    @Override
    public void execute() {
        // Determine whether right joystick is positive or negative to raise or lower climber
        double climber_value = Util.applyDeadband(operator.getRightX(), JoystickConstants.DEADBAND);

        if (climber_value > 0.0) {
            climber.raiseClimber();
        } else if (climber_value < 0.0) {
            climber.lowerClimber();
        } else {
            climber.commandDefault();
            // Can change this to stop climber depending on operator preference
            // climber.stopClimber();
        }
        
    }

}
