package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.StaticBrake;          // API at https://api.ctr-electronics.com/phoenix6/release/java/com/ctre/phoenix6/controls/StaticBrake.html
import com.ctre.phoenix6.hardware.TalonFX;              // API at https://api.ctr-electronics.com/phoenix6/release/java/com/ctre/phoenix6/hardware/TalonFX.html
import com.ctre.phoenix6.controls.VelocityVoltage;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.CoralIntakeConstants;
import frc.robot.Constants.ElevatorConstants;

public final class CoralIntake extends SubsystemBase {

    private final TalonFX coral_intake = new TalonFX(HardwareConstants.CORAL_INTAKE_CAN);

    private final VelocityVoltage velocity_voltage = new VelocityVoltage(0);

    public CoralIntake() {
        coral_intake.getConfigurator().apply(ElevatorConstants.ELEVATOR_CONFIG);
        velocity_voltage.Slot = 0;
    }

    /**
    * @param velocity sets velo (in rotations per second) 
    */
    public void setIntakeVelocity(double velocity) {
        coral_intake.setControl(velocity_voltage.withVelocity(velocity));
    }
}