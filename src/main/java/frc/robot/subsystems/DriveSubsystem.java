/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;


public class DriveSubsystem extends SubsystemBase {

      //Motors
      TalonFX leftDrivePrimary = new TalonFX(12);//Change This
      TalonFX leftDriveBack = new TalonFX(13);
      TalonFX rightDrivePrimary = new TalonFX(10);
      TalonFX rightDriveBack = new TalonFX(11);
      //Gyro
      AHRS navx = new AHRS(SPI.Port.kMXP);
      //create the penumatics 
      DoubleSolenoid driveShift = new DoubleSolenoid(0,1);
      //things for motion magic
      StringBuilder _sb = new StringBuilder();
      int _smoothing = 0;
      int _pov = -1;
      private boolean m_configCorrectly = true;
  
      //Ramsete Stuff
      DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(Constants.distBetweenWheelsInches));
      DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading());
  
      SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(Constants.kS, Constants.kV, Constants.kA);
  
      PIDController leftPIDController = new PIDController(Constants.kP, 0.0, 0.0);
  
      PIDController rightPIDController = new PIDController(Constants.kP, 0.0, 0.0);

      Pose2d pose;

  public DriveSubsystem() {
    leftDrivePrimary.setInverted(true);
    leftDriveBack.setInverted(true);
    rightDrivePrimary.setInverted(false);
    rightDriveBack.setInverted(false);

    leftDriveBack.follow(leftDrivePrimary);
    rightDriveBack.follow(rightDrivePrimary);
  }
  //---------------------------Place Setters Here-------------------------------
  public void set(double leftVoltage, double rightVoltage) {
    System.out.println("L: " + leftVoltage);
    System.out.println("R: " + rightVoltage);
    leftDrivePrimary.set(ControlMode.PercentOutput, leftVoltage/12);
    rightDrivePrimary.set(ControlMode.PercentOutput, rightVoltage/12);
  }

  public void configElevatorMagic(){
    //set the motors to factory default 
    leftDrivePrimary.configFactoryDefault();
    leftDriveBack.configFactoryDefault();
    rightDrivePrimary.configFactoryDefault();
    rightDriveBack.configFactoryDefault();

    //Configures the MagEncoders into Relative mode
    leftDrivePrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    rightDrivePrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    leftDrivePrimary.setSelectedSensorPosition(0);
    rightDrivePrimary.setSelectedSensorPosition(0);

    //Set the sensor phase
    leftDrivePrimary.setSensorPhase(false);
    rightDrivePrimary.setSensorPhase(false);

    //Set inverted
    rightDrivePrimary.setInverted(true);
    rightDriveBack.setInverted(true);
    leftDrivePrimary.setInverted(false);
    leftDriveBack.setInverted(false);

    //This makes the slave controllers follow the output values of the master controllers
    leftDriveBack.follow(leftDrivePrimary);
    rightDriveBack.follow(rightDrivePrimary);

    //Enables voltage compensation, it will take the battery voltage into account when trying to drive the robot.
    leftDrivePrimary.enableVoltageCompensation(false);
    rightDrivePrimary.enableVoltageCompensation(false);
    leftDrivePrimary.configVoltageCompSaturation(Constants.voltageSaturation);
    rightDrivePrimary.configVoltageCompSaturation(Constants.voltageSaturation);

    //config the outputs 
    leftDrivePrimary.configNominalOutputForward(0, 0);
    rightDrivePrimary.configNominalOutputForward(0, 0);

    leftDrivePrimary.configPeakOutputForward(.6, 0);
    rightDrivePrimary.configPeakOutputForward(.6, 0);

    //config the neuteral mode 
    leftDrivePrimary.setNeutralMode(NeutralMode.Brake);
    rightDrivePrimary.setNeutralMode(NeutralMode.Brake);
    leftDriveBack.setNeutralMode(NeutralMode.Brake);
    rightDriveBack.setNeutralMode(NeutralMode.Brake);

    //config accel and vcrusie velocity
    leftDrivePrimary.configMotionAcceleration(5000);
    leftDrivePrimary.configMotionCruiseVelocity(70000);
    rightDrivePrimary.configMotionAcceleration(5000);
    rightDrivePrimary.configMotionCruiseVelocity(70000);

    //zero out the things 
    leftDrivePrimary.setSelectedSensorPosition(0, 0, 0);
    rightDrivePrimary.setSelectedSensorPosition(0, 0, 0);

    //set the PID values for the master motor controllers 
    rightDrivePrimary.config_kP(0, 0.2);
    rightDrivePrimary.config_kI(0, 0);
    rightDrivePrimary.config_kD(0, 0);
    rightDrivePrimary.config_kF(0, 0.2);

    leftDrivePrimary.config_kP(0, 0.2);
    rightDrivePrimary.config_kI(0, 0);
    leftDrivePrimary.config_kD(0, 0);
    leftDrivePrimary.config_kF(0, 0.2);

    rightDrivePrimary.configVoltageCompSaturation(12.0);
    leftDrivePrimary.configVoltageCompSaturation(12.0);    

    m_configCorrectly = false;
  }


  //---------------------------Place Getters Here-------------------------------

  //Name: Jack DeRuntz
  //About: Gets the angle of the robot
  public double getYaw(){
    return -navx.getAngle();
  }
  //Name: Jack DeRuntz
  //About: Gets the angle of the robot
  public Rotation2d getHeading(){
    return Rotation2d.fromDegrees(Math.IEEEremainder(getYaw(), 360.0d));
  }

  public PIDController getLeftPIDController() {
    return leftPIDController;
  }

  public PIDController getRightPIDController() {
    return rightPIDController;
  }

  public SimpleMotorFeedforward getFeedForward() {
    return feedForward;
  }

  public DifferentialDriveKinematics getKinematics() {
    return kinematics;
  }

  public Pose2d getPose() {
    return pose;
  }

  public double getLeftEncoderDistanceMeters(){
    double leftDistance;
    leftDistance = leftDrivePrimary.getSelectedSensorPosition() / Constants.encoderTicksPerRev * Constants.gearRatio * Units.inchesToMeters(Constants.wheelCircumferenceInches);
    return leftDistance;
  }

  public double getRightEncoderDistanceMeters(){
    double rightDistance;
    rightDistance = rightDrivePrimary.getSelectedSensorPosition() / Constants.encoderTicksPerRev * Constants.gearRatio * Units.inchesToMeters(Constants.wheelCircumferenceInches);
    return rightDistance;
  }

  public double getLeftEncoderVelocityMS(){
    double leftSpeed;
    leftSpeed = leftDrivePrimary.getSelectedSensorVelocity() * Constants.gearRatio * 10.0 / Constants.encoderTicksPerRev * Units.inchesToMeters(Constants.wheelCircumferenceInches);
    return leftSpeed;
  }

  public double getRightEncoderVelocityMS(){
    double rightSpeed;
    rightSpeed = rightDrivePrimary.getSelectedSensorVelocity() * Constants.gearRatio * 10.0 / Constants.encoderTicksPerRev * Units.inchesToMeters(Constants.wheelCircumferenceInches);
    return rightSpeed;
  }


  //---------------------------Place Others Here--------------------------------
  public void resetEncoders() {
    leftDrivePrimary.setSelectedSensorPosition(0);
    rightDrivePrimary.setSelectedSensorPosition(0);
  }

  public void resetHeading() {
    navx.reset();
  }

  public void resetOdometry() {
    odometry.resetPosition(new Pose2d(), getHeading());
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds(){
    return new DifferentialDriveWheelSpeeds(
      getLeftEncoderVelocityMS(),
      getRightEncoderVelocityMS()
      );
  }

  //Name: Brennan 
  //About: create the ebrake
  public void ebrake(){
    leftDrivePrimary.setNeutralMode(NeutralMode.Brake);
    rightDrivePrimary.setNeutralMode(NeutralMode.Brake);
    leftDriveBack.setNeutralMode(NeutralMode.Brake);
    rightDriveBack.setNeutralMode(NeutralMode.Brake);
  }

  //Name: Brennan 
  //About: revert the ebrake
  public void no_ebrake(){
    leftDrivePrimary.setNeutralMode(NeutralMode.Coast);
    rightDrivePrimary.setNeutralMode(NeutralMode.Coast);
    leftDriveBack.setNeutralMode(NeutralMode.Coast);
    rightDriveBack.setNeutralMode(NeutralMode.Coast);
  }

  //Name: Brennan
  //About: shift the pistons on and off to switch from drive to elevator mode
  public void shiftPiston(){
    switch (driveShift.get()){
      case kOff:
        driveShift.set(DoubleSolenoid.Value.kForward);
       break;
      case kForward:
        driveShift.set(DoubleSolenoid.Value.kReverse);
       break;
      case kReverse:
        driveShift.set(DoubleSolenoid.Value.kForward);
        break;
    }
  }

  //Name: Brennan 
  //About: rotate the drive motors for elevator control using magic motion
  public void elevatorMotionMagic(double wheelrotRight, double wheelrotLeft){
    if(m_configCorrectly){
      configElevatorMagic();
    }

    double targetPosition =  Constants.encoderTicksPerRev * wheelrotRight * Constants.kGearRatio; //10.
    double targetPosition2 = Constants.encoderTicksPerRev * wheelrotLeft * Constants.kGearRatio;
  
    rightDrivePrimary.set(ControlMode.MotionMagic, targetPosition);
    leftDrivePrimary.set(ControlMode.MotionMagic, targetPosition2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    pose = odometry.update(getHeading(), getLeftEncoderDistanceMeters(), getRightEncoderDistanceMeters());
    
    SmartDashboard.putNumber("left encoder pos", leftDrivePrimary.getSelectedSensorPosition());
    SmartDashboard.putNumber("right encoder pos", rightDrivePrimary.getSelectedSensorPosition());

    SmartDashboard.putNumber("heading", getYaw());

    SmartDashboard.putNumber("left encoder vel", leftDrivePrimary.getSelectedSensorVelocity());
    SmartDashboard.putNumber("right encoder vel", rightDrivePrimary.getSelectedSensorVelocity());

    SmartDashboard.putNumber("X pose", odometry.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("Y pose", odometry.getPoseMeters().getTranslation().getY());
  }

  //----------------------------------------------------------------------------------------------------//
  //----------------------------------PLEASE DO NOT TOUCH THIS------------------------------------------//
  //----------------------------------------------------------------------------------------------------//

  /*
  I ported the WPI Arcade Drive so that we do not have to use the WPI_TalonFX class. I think that it
  will help with all of the problems we were having. Hopefully thats the only thing y'all need.
  https://www.chiefdelphi.com/t/paper-arcade-drive/168720
  */
  public void PortedArcadeDrive(double xSpeed, double zRotation){

    double m_rightSideInvertMultiplier = -1.0;
    double m_maxOutput = 1;
    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
    
    double leftMotorOutput;
    double rightMotorOutput;

    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0); //Clamp Joysticks to Max of 1 and -1
    zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);

    //Divide the different speeds into four quadrants to linear interpolate
    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      } else {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      }
    }
    //Send the outputs to the motor controllers
    leftDrivePrimary.set(ControlMode.PercentOutput, MathUtil.clamp(leftMotorOutput, -1.0, 1.0) * m_maxOutput);
    double maxOutput = m_maxOutput * m_rightSideInvertMultiplier;
    rightDrivePrimary.set(ControlMode.PercentOutput, MathUtil.clamp(rightMotorOutput, -1.0, 1.0) * maxOutput);
  }

}