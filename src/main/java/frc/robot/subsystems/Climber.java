package frc.robot.subsystems;

// import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.HardwareConstants;

// NOTE: all this is copy-paste from the Elevator subsystem because I'm assuming its the same kind of motor. If this assumption is poor we might have to redo this
public final class Climber extends SubsystemBase {
    public static record ClimberState(double height, double angle) {}
    
    private final TalonFX climberMain = new TalonFX(HardwareConstants.CLIMBER_CAN);

    private final MotionMagicVoltage climberPositionRequest = new MotionMagicVoltage(0.0)
        .withOverrideBrakeDurNeutral(true)      // Kill motor within control deadband
        .withSlot(0);                           
  
    private final StaticBrake staticBrakeRequest = new StaticBrake();

    public Climber() {
        climberMain.getConfigurator().apply(ClimberConstants.CLIMBER_CONFIG);
      //  climberFollower.getConfigurator().apply(ClimberConstants.CLIMBER_CONFIG);
      //  climberFollower.setControl(new Follower(HardwareConstants.CLIMBER_LEADER_CAN, false));
         
        // Reset the climber's encoders
       resetClimberEncoder();

    }

    /**
     * @param rotations sets climber to set rotations (in rots).
     */
    public void setClimberPosition(double rotations){
        // Sanity check rotations
        rotations = MathUtil.clamp(rotations, ClimberConstants.MIN_CLIMBER, ClimberConstants.MAX_CLIMBER);

        climberMain.setControl(climberPositionRequest.withPosition(rotations));

        // If above doesn't work, consider ditching MotionMagic:
        // climberMain.setControl(rotations);
    }

    public void raiseClimber(){
        climberMain.setControl(climberPositionRequest.withPosition(ClimberConstants.MAX_CLIMBER));

        // If above doesn't work, consider ditching MotionMagic:
        // climberMain.setControl(ClimberConstants.MAX_CLIMBER);        
    }

    public void lowerClimber(){
        climberMain.setControl(climberPositionRequest.withPosition(ClimberConstants.MIN_CLIMBER));

        // If above doesn't work, consider ditching MotionMagic:
        // climberMain.setControl(ClimberConstants.MIN_CLIMBER);        
    }

    /**
     * Stops the climber.
     */
    public void stopClimber() {
        climberMain.setControl(staticBrakeRequest);
    }

    /*
     * Returns climber to default position
     */
    public void commandDefault() {
        climberMain.setControl(climberPositionRequest.withPosition(ClimberConstants.MIN_CLIMBER));
    }

    public void resetClimberEncoder() {
        climberMain.setPosition(0.0);
      //  climberFollower.setPosition(0.0);
      }
}