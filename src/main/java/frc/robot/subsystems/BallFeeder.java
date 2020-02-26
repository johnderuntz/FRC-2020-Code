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
  
  private DoubleSolenoid ballFeedShift = new DoubleSolenoid(2,3);

  public BallFeeder() {

  feeder_1.set(ControlMode.PercentOutput, 0);
  feeder_1.setNeutralMode(NeutralMode.Brake);
    
  }

  //Name: Brennan 
  //About: will shift the pistion on and off 
  public void shiftFeeder(){
    switch (ballFeedShift.get()){
      case kOff:
        ballFeedShift.set(DoubleSolenoid.Value.kForward);
       break;
      case kForward:
        ballFeedShift.set(DoubleSolenoid.Value.kReverse);
       break;
      case kReverse:
        ballFeedShift.set(DoubleSolenoid.Value.kForward);
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
