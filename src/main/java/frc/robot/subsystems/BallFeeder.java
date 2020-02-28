/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BallFeeder extends SubsystemBase {

  public WPI_TalonSRX feeder_1 = new WPI_TalonSRX(21);
  
  private DoubleSolenoid ballFeedShift1 = new DoubleSolenoid(1,6);
  private DoubleSolenoid ballFeedShift2 = new DoubleSolenoid(2,5);

  public BallFeeder() {

  feeder_1.set(ControlMode.PercentOutput, 0);
  feeder_1.setNeutralMode(NeutralMode.Brake);
    
  }

  //Name: Brennan 
  //About: will shift the pistion on and off 
  public void shiftFeeder(){
    switch (ballFeedShift1.get()){
      case kOff:
        ballFeedShift1.set(DoubleSolenoid.Value.kForward);
       break;
      case kForward:
        ballFeedShift1.set(DoubleSolenoid.Value.kReverse);
       break;
      case kReverse:
        ballFeedShift1.set(DoubleSolenoid.Value.kForward);
        break;
    }
  }

  //Name: Brennan 
  //About: will shift the pistion on and off 
  public void shiftFeeder2(){
    switch (ballFeedShift2.get()){
      case kOff:
        ballFeedShift2.set(DoubleSolenoid.Value.kForward);
       break;
      case kForward:
        ballFeedShift2.set(DoubleSolenoid.Value.kReverse);
       break;
      case kReverse:
        ballFeedShift2.set(DoubleSolenoid.Value.kForward);
        break;
    }
  }

  //Name: Brennan 
  //About: will turn the feeder on 
  public void feederOn(){
    feeder_1.set(1.0);
    return;
  }

  //Name: Brennan 
  //About: turns the feeder off
  public void feederOFF(){
    feeder_1.set(0.0);
  }

  //Name: Brennan 
  //About: reverses the feeder 
  public void feederBACK(){
    feeder_1.set(-1.0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
