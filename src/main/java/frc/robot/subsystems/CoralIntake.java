package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.CoralIntakeConstants;

public final class CoralIntake implements Subsystem {

    private final SparkFlex intake = new SparkFlex(
        HardwareConstants.CORAL_INTAKE_CAN, 
        MotorType.kBrushless                                // Specify brushless motor
        );

    public CoralIntake() {
        intake.configure(
            CoralIntakeConstants.INTAKE_CONFIG,             // Motor controller configs
            ResetMode.kResetSafeParameters,                 // Reset only safe parameters
            PersistMode.kPersistParameters                  // Save configs to non-volatile memory
            );
    }

    /**
    * @param velocity sets velo (in %) 
    */
    public void setIntakeVelocity(double velocity) {
        intake.set(velocity);
    }
}
