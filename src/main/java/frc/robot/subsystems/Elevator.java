package frc.robot.subsystems;

// import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.HardwareConstants;

public final class Elevator implements Subsystem {
    public static record ElevatorState(double height, double angle) {}
    
    private final TalonFX elevatorMain = new TalonFX(HardwareConstants.ELEVATOR_LEADER_CAN, HardwareConstants.CANIVORE);
    private final TalonFX elevatorFollower = new TalonFX(HardwareConstants.ELEVATOR_FOLLOWER_CAN, HardwareConstants.CANIVORE);

    private final MotionMagicVoltage elevatorPositionRequest = new MotionMagicVoltage(0.0)
        .withOverrideBrakeDurNeutral(true)      // Kill motor within control deadband
        .withSlot(0);                           
  
    private final StaticBrake staticBrakeRequest = new StaticBrake();

    public Elevator() {
        elevatorMain.getConfigurator().apply(ElevatorConstants.ELEVATOR_CONFIG);
        elevatorFollower.getConfigurator().apply(ElevatorConstants.ELEVATOR_CONFIG);
        elevatorFollower.setControl(new Follower(HardwareConstants.ELEVATOR_LEADER_CAN, false));
          
        // Reset the elevator's encoders
        resetElevatorEncoder();

    }

    /**
     * @param rotations sets elevator to set rotations (in rots).
     */
    public void setElevatorPosition(double rotations){
        // Sanity check rotations
        rotations = MathUtil.clamp(rotations, ElevatorConstants.MIN_ELEVATOR, ElevatorConstants.MAX_ELEVATOR);

        elevatorMain.setControl(elevatorPositionRequest.withPosition(rotations));

        // If above doesn't work, consider ditching MotionMagic:
        // elevatorMain.setControl(rotations);
    }

    public void raiseElevator(){
        elevatorMain.setControl(elevatorPositionRequest.withPosition(ElevatorConstants.MAX_ELEVATOR));

        // If above doesn't work, consider ditching MotionMagic:
        // elevatorMain.setControl(ElevatorConstants.MAX_ELEVATOR);        
    }

    public void lowerElevator(){
        elevatorMain.setControl(elevatorPositionRequest.withPosition(ElevatorConstants.MIN_ELEVATOR));

        // If above doesn't work, consider ditching MotionMagic:
        // elevatorMain.setControl(ElevatorConstants.MAX_ELEVATOR);        
    }

    /**
     * Stops the elevator.
     */
    public void stopElevator() {
        elevatorMain.setControl(staticBrakeRequest);
    }

    /*
     * Returns elevator to default position
     */
    public void commandDefault() {
        elevatorMain.setControl(elevatorPositionRequest.withPosition(ElevatorConstants.MIN_ELEVATOR));
    }

    public void resetElevatorEncoder() {
        elevatorMain.setPosition(0.0);
        elevatorFollower.setPosition(0.0);
      }
}
