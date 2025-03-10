package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Elevator.ElevatorState;

public final class Constants {
    public static final class DriveConstants {
        public static final double MAX_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);       // kSpeedAt12Volts desired top speed
        // NOTE: I don't know where RotationsPerSecond comes from, that will probably break
        public static final double MAX_ANG_RATE = RotationsPerSecond.of(0.75).in(RadiansPerSecond);   // 3/4 of a rotation per second max angular velocity
    }

    public static final class ElevatorConstants {
        public static final double ELEVATOR_RATIO = 7.75;   // Gear ratio for elevator
        public static final double MAX_ELEVATOR = 4.5;      // Maximum elevator position, rotations
        public static final double MIN_ELEVATOR = 0.0;      // Minimum elevator position, rotations
 
        private static final MotionMagicConfigs ELEVATOR_MM_CONFIGS = new MotionMagicConfigs()
          .withMotionMagicCruiseVelocity(3.0)           // Maximum velocity for motor motion
          .withMotionMagicAcceleration(5.5);            // Maximum acceleration for motor motion
          
        // NOTE: all following configs are probably not something you care about, if they cause issues stub 'em all out
        private static final Slot0Configs ELEVATOR_MOTOR_GAINS = new Slot0Configs()
          .withKP(3.596).withKI(0.0).withKD(0.0)                                    // PID controller gains for elevator motor
          .withKS(0.1103).withKV(0.92196).withKA(0.0019)                            // Friction, velocity, and acceleration feedforward gains
          .withKG(0.5609).withGravityType(GravityTypeValue.Elevator_Static);        // Gravity compensation gains
    
        private static final CurrentLimitsConfigs ELEVATOR_MOTOR_CURRENT = new CurrentLimitsConfigs()
        .withSupplyCurrentLimit(50).withSupplyCurrentLimitEnable(true)          // Max allowable supply current
        .withStatorCurrentLimit(70).withStatorCurrentLimitEnable(true);         // Max allowable stator current
    
        private static final FeedbackConfigs ELEVATOR_ENCODER = new FeedbackConfigs()
          .withSensorToMechanismRatio(ELEVATOR_RATIO)                           // Sets ratio between encoder sensor and elevator mechanism
          .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);     // Sets source of feedback
    
        private static final MotorOutputConfigs ELEVATOR_OUTPUT = new MotorOutputConfigs()
          .withInverted(InvertedValue.CounterClockwise_Positive)
          .withNeutralMode(NeutralModeValue.Brake);
    
        public static final TalonFXConfiguration ELEVATOR_CONFIG = new TalonFXConfiguration()
          .withSlot0(ELEVATOR_MOTOR_GAINS)
          .withMotionMagic(ELEVATOR_MM_CONFIGS)
          .withCurrentLimits(ELEVATOR_MOTOR_CURRENT)
          .withFeedback(ELEVATOR_ENCODER)
          .withMotorOutput(ELEVATOR_OUTPUT);
    }

    public static final class ElevatorPositions {

        // NOTE: this is a pretty clever way to repeatably command the elevator to set heights around the track
        public static final ElevatorState STOW = new ElevatorState(0.15, 0.4);
        public static final ElevatorState INTAKE = new ElevatorState(1.0, 0.3);
        public static final ElevatorState PROCESSOR = new ElevatorState(0.1, 0.2);
    
        public static final ElevatorState L1 = new ElevatorState(0.05, 0.255);
        public static final ElevatorState L2 = new ElevatorState(1.4, 0.11); 
        public static final ElevatorState L3 = new ElevatorState(2.6, 0.06);
        public static final ElevatorState L4 = new ElevatorState(4.45, -0.02);
    
        public static final ElevatorState ALGAE_LOW = new ElevatorState(0.55, 0.2);
        public static final ElevatorState ALGAE_HIGH = new ElevatorState(1.65, 0.2);
    }

    public static final class ClimberConstants {
      public static final double CLIMBER_RATIO = 7.75;   // Gear ratio for climber
      public static final double MAX_CLIMBER = 4.5;      // Maximum climber position, rotations
      public static final double MIN_CLIMBER = 0.0;      // Minimum climber position, rotations

      private static final MotionMagicConfigs CLIMBER_MM_CONFIGS = new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(3.0)           // Maximum velocity for motor motion
        .withMotionMagicAcceleration(5.5);            // Maximum acceleration for motor motion
        
      // NOTE: all following configs are probably not something you care about, if they cause issues stub 'em all out
      private static final Slot0Configs CLIMBER_MOTOR_GAINS = new Slot0Configs()
        .withKP(3.596).withKI(0.0).withKD(0.0)                                    // PID controller gains for climber motor
        .withKS(0.1103).withKV(0.92196).withKA(0.0019)                            // Friction, velocity, and acceleration feedforward gains
        .withKG(0.5609).withGravityType(GravityTypeValue.Climber_Static);        // Gravity compensation gains
  
      private static final CurrentLimitsConfigs CLIMBER_MOTOR_CURRENT = new CurrentLimitsConfigs()
      .withSupplyCurrentLimit(50).withSupplyCurrentLimitEnable(true)          // Max allowable supply current
      .withStatorCurrentLimit(70).withStatorCurrentLimitEnable(true);         // Max allowable stator current
  
      private static final FeedbackConfigs CLIMBER_ENCODER = new FeedbackConfigs()
        .withSensorToMechanismRatio(CLIMBER_RATIO)                           // Sets ratio between encoder sensor and climber mechanism
        .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor);     // Sets source of feedback
  
      private static final MotorOutputConfigs CLIMBER_OUTPUT = new MotorOutputConfigs()
        .withInverted(InvertedValue.CounterClockwise_Positive)
        .withNeutralMode(NeutralModeValue.Brake);
  
      public static final TalonFXConfiguration CLIMBER_CONFIG = new TalonFXConfiguration()
        .withSlot0(CLIMBER_MOTOR_GAINS)
        .withMotionMagic(CLIMBER_MM_CONFIGS)
        .withCurrentLimits(CLIMBER_MOTOR_CURRENT)
        .withFeedback(CLIMBER_ENCODER)
        .withMotorOutput(CLIMBER_OUTPUT);
    }

    public static final class CoralIntakeConstants {
      
    }

    public static final class JoystickConstants {
        public static final double DEADBAND = 0.1;      // Tune as needed
    }

    public static final class HardwareConstants {
        // NOTE: Clean up following constant values based on hardware configuration on robot

        public static final int PDH_CAN = 1;
    
        // Swerve Drive Hardware
        public static final int FR_DRIVE_KRAKEN_CAN = 2;
        public static final int FR_STEER_NEO_CAN = 3;
        public static final int FR_STEER_CANCODER_CAN = 4;
        public static final int RR_DRIVE_KRAKEN_CAN = 5;
        public static final int RR_STEER_NEO_CAN = 6;
        public static final int RR_STEER_CANCODER_CAN = 7;
        public static final int RL_DRIVE_KRAKEN_CAN = 8;
        public static final int RL_STEER_NEO_CAN = 9;
        public static final int RL_STEER_CANCODER_CAN = 10;
        public static final int FL_DRIVE_KRAKEN_CAN = 11;
        public static final int FL_STEER_NEO_CAN = 12;
        public static final int FL_STEER_CANCODER_CAN = 13;
    
        // Elevator Hardware
        public static final int ELEVATOR_LEADER_CAN = 14;
        public static final int ELEVATOR_FOLLOWER_CAN = 15;
        public static final int MANIPULATOR_PIVOT_CAN = 16;
    
        // Climber Hardware
        public static final int CLIMBER_CAN = 17;
    
        // Manipulator Hardware 
        public static final int CORAL_INTAKE_CAN = 19;
        public static final int MANIPULATOR_SENSORS_CAN = 20; // Spark Max, no motor
    
        // CAN Busses
        public static final CANBus RIO_CAN = new CANBus("rio");
        public static final CANBus CANIVORE = new CANBus("CANivore");
    
      }

    public static final String MotorsIDs = null;

}
