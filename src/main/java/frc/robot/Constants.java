package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.units.LinearVelocityUnit;
import frc.robot.generated.TunerConstants;

import static edu.wpi.first.units.Units.*;

public final class Constants {

/* ----- BALL INTAKE ----- */
  public static final class BallIntakeConstants {
    public static final double MAX_INTAKE_VEL = 1.0;                        // Maximum speed of ball intake, in % from 0.0 to 1.0

    public static final SparkBaseConfig INTAKE_CONFIG = new SparkFlexConfig()
        .idleMode(IdleMode.kBrake)                                          // Apply brakes when not actively driven
        .inverted(false)                                                    // Invert direction of motor rotation
        .smartCurrentLimit(60);                                             // Current limit of motor, in amps
  }

/* ----- CLIMBER ----- */
  public static final class ClimberConstants {
    public static final double MAX_CLIMBER = 4.5;                           // Maximum climber position, rotations
    public static final double MIN_CLIMBER = 0.0;                           // Minimum climber position, rotations

    private static final MotionMagicConfigs CLIMBER_MM_CONFIGS = new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(3.0)                                 // Maximum velocity for motor motion
        .withMotionMagicAcceleration(5.5);                                  // Maximum acceleration for motor motion

    private static final MotorOutputConfigs CLIMBER_OUTPUT = new MotorOutputConfigs()
        .withInverted(InvertedValue.CounterClockwise_Positive)              // Defines positive spin direction of motor
        .withNeutralMode(NeutralModeValue.Brake);                           // Apply brakes when not actively driven

    public static final TalonFXConfiguration CLIMBER_CONFIG = new TalonFXConfiguration()
        .withMotionMagic(CLIMBER_MM_CONFIGS)
        .withMotorOutput(CLIMBER_OUTPUT);
  }

/* ----- CORAL INTAKE ----- */
  public static final class CoralIntakeConstants {
    public static final double MAX_INTAKE_VEL = 1.0;                       // Maximum speed of coral intake, in rotations per second
    
    private static final Slot0Configs CORAL_GAINS = new Slot0Configs()
        .withKP(0.11)
        .withKI(0.48)
        .withKD(0.01)
        .withKV(0.12);

    private static final MotorOutputConfigs CORAL_OUTPUT = new MotorOutputConfigs()
        .withInverted(InvertedValue.CounterClockwise_Positive)              // Defines positive spin direction of motor
        .withNeutralMode(NeutralModeValue.Brake);                           // Apply brakes when not actively driven

    public static final TalonFXConfiguration CORAL_CONFIG = new TalonFXConfiguration()
        .withSlot0(CORAL_GAINS)
        .withMotorOutput(CORAL_OUTPUT);
  }

/* ----- ELEVATOR ----- */
  public static final class ElevatorConstants {
    // public static final double ELEVATOR_RATIO = 7.75;                       // Gear ratio for elevator
    public static final double MAX_ELEVATOR = 4.5;                          // Maximum elevator position, rotations
    public static final double MIN_ELEVATOR = 0.0;                          // Minimum elevator position, rotations

    private static final MotionMagicConfigs ELEVATOR_MM_CONFIGS = new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(3.0)                                 // Maximum velocity for motor motion
        .withMotionMagicAcceleration(5.5);                                  // Maximum acceleration for motor motion

    private static final MotorOutputConfigs ELEVATOR_OUTPUT = new MotorOutputConfigs()
        .withInverted(InvertedValue.CounterClockwise_Positive)              // Defines positive spin direction of motor
        .withNeutralMode(NeutralModeValue.Brake);                           // Apply brakes when not actively driven

    public static final TalonFXConfiguration ELEVATOR_CONFIG = new TalonFXConfiguration()
        .withMotionMagic(ELEVATOR_MM_CONFIGS)
        .withMotorOutput(ELEVATOR_OUTPUT);
  }

  public static final class HardwareConstants {
    // NOTE: Clean up following constant values based on hardware configuration on
    // robot

    public static final int PDH_CAN = 1;

    // Swerve Drive Hardware
    public static final int FR_DRIVE_KRAKEN_CAN = 1;
    public static final int FR_STEER_NEO_CAN = 3;
    public static final int FR_STEER_CANCODER_CAN = 41;
    public static final int RR_DRIVE_KRAKEN_CAN = 6;
    public static final int RR_STEER_NEO_CAN = 7;
    public static final int RR_STEER_CANCODER_CAN = 40;
    public static final int RL_DRIVE_KRAKEN_CAN = 0;
    public static final int RL_STEER_NEO_CAN = 4;
    public static final int RL_STEER_CANCODER_CAN = 43;
    public static final int FL_DRIVE_KRAKEN_CAN = 2;
    public static final int FL_STEER_NEO_CAN = 5;
    public static final int FL_STEER_CANCODER_CAN = 0;

    // Elevator Hardware
    public static final int ELEVATOR_LEADER_CAN = 20;
    public static final int ELEVATOR_FOLLOWER_CAN = 21;
    public static final int ELEVATOR_LIMIT_SWITCH_DIO = 0;

    // Climber Hardware
    public static final int CLIMBER_CAN = 33;

    // Manipulator Hardware
    public static final int CORAL_INTAKE_CAN = 31;
    public static final int MANIPULATOR_SENSORS_CAN = 8; // Spark Max, no motor

  }

  public static final String MotorsIDs = null;


  /* ----- JOYSTICK ----- */
  public static final class JoystickConstants {
    public static final double DEADBAND = 0.1; // Tune as needed
  }
}