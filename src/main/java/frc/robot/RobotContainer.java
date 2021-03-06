/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.Arrays;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import frc.robot.commands.PlayerDrive;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import frc.robot.commands.PlayerDrive;



public class RobotContainer {


  //Define Subsystems Here
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  //Define Commands Here
  

  //Define Anything Else Here

  public final XboxController driver = new XboxController(0);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_driveSubsystem.resetEncoders();
    m_driveSubsystem.resetHeading();
    m_driveSubsystem.resetOdometry();


    //Assign Default Commands
    m_driveSubsystem.setDefaultCommand(new PlayerDrive(() -> driver.getY(Hand.kLeft),() -> driver.getX(Hand.kRight), m_driveSubsystem));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //Network Tables Debug Numbers
    var m_leftReference = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("left_reference");
    var m_leftMeasurement = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("left_measurement");
    var m_rightReference = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("right_reference");
    var m_rightMeasurement = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("right_measurement");
    //Reset all of the measurement devices
    m_driveSubsystem.resetEncoders();
    m_driveSubsystem.resetHeading();
    m_driveSubsystem.resetOdometry();

    //Generate the Trajectory Config
    TrajectoryConfig config = new TrajectoryConfig(3.97350993, 2); //Max Velocity, Max Acceleration

    config.setKinematics(m_driveSubsystem.getKinematics());

    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      Arrays.asList(
        new Pose2d(), 
        new Pose2d(2, 0, Rotation2d.fromDegrees(0)) //This will drive the Robot Straight for 2 Meters
        ), 
      config
    );

    RamseteController disabledRamsete = new RamseteController() {
      @Override
      public ChassisSpeeds calculate(Pose2d currentPose, Pose2d poseRef, double linearVelocityRefMeters,
              double angularVelocityRefRadiansPerSecond) {
          return new ChassisSpeeds(linearVelocityRefMeters, 0.0, angularVelocityRefRadiansPerSecond);
      }
  };

    RamseteCommand command = new RamseteCommand(
      trajectory,
      m_driveSubsystem::getPose,
      disabledRamsete,//new RamseteController(2.0, 0.7),
      m_driveSubsystem.getFeedForward(),
      m_driveSubsystem.getKinematics(),
      m_driveSubsystem::getWheelSpeeds,
      m_driveSubsystem.getLeftPIDController(),
      m_driveSubsystem.getRightPIDController(),
      (leftVolts, rightVolts) -> {
        m_driveSubsystem.set(leftVolts, rightVolts);

        m_leftMeasurement.setNumber(m_driveSubsystem.getFeedForward().calculate(m_driveSubsystem.getWheelSpeeds().leftMetersPerSecond));
        m_leftReference.setNumber(leftVolts);

        m_rightMeasurement.setNumber(m_driveSubsystem.getFeedForward().calculate(m_driveSubsystem.getWheelSpeeds().rightMetersPerSecond));
        m_rightReference.setNumber(-rightVolts);
    },//m_driveSubsystem::set,
      m_driveSubsystem
    );


    return command;
  }
}

