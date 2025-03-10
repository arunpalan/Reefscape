// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.BallIntake;
// import frc.robot.command.AlignToAprilTag;

import frc.robot.commands.*;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.CoralIntakeConstants;

import frc.robot.util.Util;

public class RobotContainer {

    /* Setting up bindings for necessary control of the swerve drive platform */
    // NOTE: moved MAX_SPEED and MAX_ANG_RATE over to Constants file, this may break things
    // Good practice to declutter this class though
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(DriveConstants.MAX_SPEED * 0.1).withRotationalDeadband(DriveConstants.MAX_ANG_RATE * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    // Instantiate subsystems
    private final Elevator elevator = new Elevator();
    private final Climber climber = new Climber();
    private final CoralIntake coral = new CoralIntake();
    private final BallIntake ball = new BallIntake();

    private final Telemetry logger = new Telemetry(DriveConstants.MAX_SPEED);

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();


    public RobotContainer() {
        configureBindings();
    }

    /**
    * Use this method to define your trigger->command mappings.
    */
    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-driver.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        driver.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driver.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driver.back().and(driver.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driver.back().and(driver.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driver.start().and(driver.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driver.start().and(driver.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        
        //lines up robot with closest april tag
        //driver.x().whileTrue(getAutonomousCommand(new LimelightHelpers));
        
        // reset the field-centric heading on left bumper press
        driver.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        // Runs elevator to raise/lower or return to default state based on driver left joystick
        // NOTE: There is a clever way to do this with lambda expressions and a RepeatCommand class but choosing not to implement that because it's trickier
        // See: https://github.com/FRC7153/2025-Reefscape/blob/main/src/main/java/frc/robot/RobotContainer.java and https://github.com/FRC7153/2025-Reefscape/blob/main/src/main/java/frc/robot/commands/ManipulatorCommand.java
        elevator.setDefaultCommand(new ElevatorCommand(elevator, operator));

        // Runs climber to raise/lower or return to default state based on driver left joystick
        // NOTE: There is a clever way to do this with lambda expressions and a RepeatCommand class but choosing not to implement that because it's trickier
        // See: https://github.com/FRC7153/2025-Reefscape/blob/main/src/main/java/frc/robot/RobotContainer.java and https://github.com/FRC7153/2025-Reefscape/blob/main/src/main/java/frc/robot/commands/ManipulatorCommand.java
        climber.setDefaultCommand(new ClimberCommand(climber, operator));        

        // Runs coral intake while left trigger is pressed
        operator.leftTrigger().whileTrue(coral.setIntakeVelocity(CoralIntakeConstants.MAX_INTAKE_VEL));

        // Runs coral intake in reverse while right trigger is pressed
        operator.rightTrigger().whileTrue(coral.setIntakeVelocity(-CoralIntakeConstants.MAX_INTAKE_VEL));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
