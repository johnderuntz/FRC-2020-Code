/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.Constants;
import frc.robot.subsystems.Limelight;

public class Shooter extends SubsystemBase {

  //About: Instanciate the motors for the fly wheel 
  WPI_TalonFX Shooter_1 = new WPI_TalonFX(15);
  WPI_TalonFX Shooter_2 = new WPI_TalonFX(16);

  WPI_TalonSRX hoodAdjuster = new WPI_TalonSRX(20);

  //About: instanciate the limit switch 
  private static DigitalInput hoodLimitSwitch = new DigitalInput(1);

  //About: instanciate the doubles 
  private double velo = 0.0;

  //About: instaciate the subsystems 
  private Limelight m_limelight = new Limelight();

  public Shooter() {
    //About: reset the motors to factory default 
    Shooter_1.configFactoryDefault();
    Shooter_2.configFactoryDefault();
    hoodAdjuster.configFactoryDefault();

    //About: configure the motors 
    configureMotors();
    configClosedLoop();

    //About: create the followers  
    Shooter_2.follow(Shooter_1);
  }

  //---------------------------Place Setters Here-------------------------------

  //Name: Brennan 
  //About: configure the flywheel motors so that they spin at the desired velo 
  private void configureMotors(){
    Shooter_1.setInverted(false);
    Shooter_1.configOpenloopRamp(2.0, 0);
    Shooter_1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0,0);
    Shooter_1.setSensorPhase(true); 
    
    Shooter_2.setInverted(true);
    Shooter_2.configOpenloopRamp(2.0, 0);
    Shooter_2.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0,0);
    Shooter_2.setSensorPhase(true); 
  }

  //Name: Brennan 
  //About: configure the flywheels and set the PID loops(create the horizontal asympote for the velocity to get to) for the most optimal velocity control  
  public void configClosedLoop(){
    Shooter_1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    Shooter_2.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

    Shooter_1.configClosedloopRamp(0.5);
    Shooter_2.configClosedloopRamp(0.5);
    Shooter_1.configAllowableClosedloopError(0, 10);
    Shooter_2.configAllowableClosedloopError(0, 10);

    //About: how to calculate kF https://phoenix-documentation.readthedocs.io/en/latest/ch16_ClosedLoop.html#calculating-velocity-feed-forward-gain-kf
    Shooter_1.config_kF(0, m_limelight.getShooterVelocity()*Constants.ShooterConstants.kRPMtoTicks);
    Shooter_1.config_kP(0, 0.002);
    Shooter_1.config_kI(0, 0);
    Shooter_1.config_kD(0, 0);

    Shooter_2.config_kF(0, m_limelight.getShooterVelocity()*Constants.ShooterConstants.kRPMtoTicks);
    Shooter_2.config_kP(0, 0.002);
    Shooter_2.config_kI(0, 0);
    Shooter_2.config_kD(0, 0);

    
  }

  //Name: Brennan 
  //About: Configure the hood motor to prepare it for positon control
  public void configHoodClosedLoop(){

    //configure the voltage 
    hoodAdjuster.configVoltageCompSaturation(12.0,0);
    hoodAdjuster.enableVoltageCompensation(true);

    //set the limits of the shooter 
    hoodAdjuster.configForwardSoftLimitThreshold(1024);
    hoodAdjuster.configNominalOutputForward(0);

    //set the speed limits 
    hoodAdjuster.configPeakOutputForward(0.4);
    hoodAdjuster.configPeakOutputReverse(-0.4);

    //tell it how long it should take to get there 
    hoodAdjuster.configClosedloopRamp(0.10);
    
    //set the motor into positon mode and set the encoder
    hoodAdjuster.set(ControlMode.Position, 0.0);
    hoodAdjuster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

    //configure the PID loop 
    hoodAdjuster.config_kP(0, 0.2);
    hoodAdjuster.config_kI(0, 0);
    hoodAdjuster.config_kD(0, 0);
    hoodAdjuster.config_kF(0, .2);

    //zero out the position 
    hoodAdjuster.setSelectedSensorPosition(0,0,0);
  }

  //Name: Brennan
  //About: configure the motors for percent output mode
  public void setPowerMode(){
    Shooter_1.configNominalOutputForward(0.0, 0);
    Shooter_1.configNominalOutputReverse(0.0, 0);
    Shooter_1.setNeutralMode(NeutralMode.Coast);

    Shooter_2.configNominalOutputForward(0.0, 0);
    Shooter_2.configNominalOutputReverse(0.0, 0);
    Shooter_2.setNeutralMode(NeutralMode.Coast);

  }

  //Name: Brennan 
  //About: configure the hood motor's self made position mode 
  public void ConfigPositonangle(){

    //configure the voltage 
    hoodAdjuster.configVoltageCompSaturation(12.0,0);
    hoodAdjuster.enableVoltageCompensation(true);

    //set the limits of the shooter 
    hoodAdjuster.configReverseSoftLimitThreshold(1024);
    hoodAdjuster.configNominalOutputForward(0);

    //set the speed limits 
    hoodAdjuster.configPeakOutputForward(1);
    hoodAdjuster.configPeakOutputReverse(-1);

    hoodAdjuster.setInverted(true);

    //tell it how long it should take to get there 
    hoodAdjuster.configClosedloopRamp(0.10);
    
    //set the motor into positon mode and set the encoder
    hoodAdjuster.set(ControlMode.PercentOutput, 0.0);
    hoodAdjuster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);

  }

  //---------------------------Place Getters Here-------------------------------

  //Name: Brennan 
  //About: get the velocities for the shooter 
  public int getShooter_1Velo(){
    return Shooter_1.getSelectedSensorVelocity();
  }

  //Name: Brennan 
  //About: get the velocities for the shooter 
  public int getShooter_2Velo(){
    return Shooter_2.getSelectedSensorVelocity();
  }

  //Name: Brennan 
  //About: get the velocities for the shooter 
  public double getTotalShooterRPM(){
    return ((Shooter_1.getSelectedSensorVelocity() + Shooter_2.getSelectedSensorVelocity())/2) * Constants.ShooterConstants.kTickstoRPM;
  }

  //Name: Brennan 
  //About: get the state of the limit switch 
  public boolean getHoodLimitSwitch(){
    return hoodLimitSwitch.get();
  }

  //Name: Brennan 
  //About: get the angle of the hood motor in degrees using the CTRE mag encoder 
  public double getHoodAngle(){
    return hoodAdjuster.getSelectedSensorPosition() * -0.087890625;
  }

  //Name: Jack
  //About: Configures the kF for the shooter as long as all the constants are correct 
  public double getShooterkF(double RPM){ 
    double maxRPM = 19700;
    double percentOutput = RPM/maxRPM;

    double kF = (percentOutput*m_limelight.getShooterVelocity())/RPM;
    
    return kF;
  }

  //Name: Brennan
  //About: Configures the kF for the hood motor 
  public double HoodKF(){
    double Velo = (1024/36);
    double percentOut = 100;
    double Kf = (percentOut*1023)/Velo;

    return Kf; 
  }

  //Name: Brennan 
  //About: use a reference table to find the optimal shooter angle @ set distances (units feet and degrees)
  public double hoodAngleTable(){
    double setAngle = 0;

    if ((m_limelight.distanceToTarget() >= 0 ) && (m_limelight.distanceToTarget() < .5 )){
      setAngle = 87;
    }
    else if ((m_limelight.distanceToTarget() >= .5 ) && (m_limelight.distanceToTarget() < 3 )){
      setAngle = 63;
    }
    else if ((m_limelight.distanceToTarget() >= 3 ) && (m_limelight.distanceToTarget() < 4.5 )){
      setAngle = 59;
    }
    else if ((m_limelight.distanceToTarget() >= 4.5 ) && (m_limelight.distanceToTarget() < 7.5 )){
      setAngle = 29;
    }
    else if ((m_limelight.distanceToTarget() >= 7.5 ) && (m_limelight.distanceToTarget() < 9.5 )){
      setAngle = 35;
    }
    else if ((m_limelight.distanceToTarget() >= 9.5 ) && (m_limelight.distanceToTarget() < 11.5 )){
      setAngle = 34;
    }
    else if ((m_limelight.distanceToTarget() >= 11.5 ) && (m_limelight.distanceToTarget() < 13.5 )){
      setAngle = 30;
    }
    else if ((m_limelight.distanceToTarget() >= 13.5 ) && (m_limelight.distanceToTarget() < 14.5 )){
      setAngle = 29; //tech 28.5
    }
    else if ((m_limelight.distanceToTarget() >= 14.5 ) && (m_limelight.distanceToTarget() < 15.5 )){
      setAngle = 28;
    }
    else if ((m_limelight.distanceToTarget() >= 15.5 ) && (m_limelight.distanceToTarget() < 17.5 )){
      setAngle = 22; //tech 22.5
    }
    else if ((m_limelight.distanceToTarget() >= 17.5 ) && (m_limelight.distanceToTarget() < 20 )){
      setAngle = 23;
    }
    System.out.println("Set Shooter velocity" + setAngle);
    
    return setAngle;
  }

   //---------------------------Place Others Here-------------------------------

  //Name: Brennan 
  //About: Useing velocity mode set the angular velocity of the shooter 
  public void setShootSpeed(double velocity){
    velo = velocity;
    Shooter_1.set(TalonFXControlMode.Velocity, velo);
    Shooter_2.set(TalonFXControlMode.Velocity, velo);
  }

  //Name: Brennan 
  //About: Using Percent Output set the power for the shooter 
  public void setPower(double power){
    setPowerMode();

    Shooter_1.set(ControlMode.PercentOutput, power);
    Shooter_2.set(ControlMode.PercentOutput, power);
  }

  //Name: Brennan 
  //About: Reset the hood angle based on the limit switch 
  public void resetHoodwithLimit(){
    boolean limit = hoodLimitSwitch.get(); 

    while(!limit){

      hoodAdjuster.set(ControlMode.PercentOutput, 1.0);
      if(limit){

        hoodAdjuster.set(ControlMode.PercentOutput, 0);
        hoodAdjuster.setSelectedSensorPosition(0);
      }
    }

    if(limit){

      hoodAdjuster.set(ControlMode.PercentOutput, 0);
      hoodAdjuster.setSelectedSensorPosition(0);
    }
  }

  //Name: Brennan 
  //About: reset the angle of the encoder 
  public void resetHoodEncoder(){
    hoodAdjuster.setSelectedSensorPosition(0);
  }

  //Name: Brennan 
  //About: Using percent output to set the power of the hoodmotor 
  public void setHoodPower(double power) {
    hoodAdjuster.set(ControlMode.PercentOutput, 0);
  }

  //Name: Brennan, Dante
  //About: set the hood angle with the position control mode
  public void setHoodWithAngle(double feedforward){
    hoodAdjuster.set(ControlMode.Position, feedforward * 0.08789062);
  }

  //Name: Brennan 
  //About: Creates its own kinda position mode and makes it hit a certian position 
  public void changeHoodPosition(double position){
    double angle = getHoodAngle();
    double target = position;
    double error = angle - target;
    double kP = 0.1;

    double speed = kP * error;

    hoodAdjuster.set(ControlMode.PercentOutput, speed);
  }
  
  @Override
  public void periodic() {
    //About: Display all of the values you want to monitor 
    SmartDashboard.putNumber("Shooter Velocity", getTotalShooterRPM());
    SmartDashboard.putNumber("Hood Angle", getHoodAngle());
    SmartDashboard.putBoolean("Hood Limit Switch", getHoodLimitSwitch());
  }
}