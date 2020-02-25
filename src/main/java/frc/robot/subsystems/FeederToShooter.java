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
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class FeederToShooter extends SubsystemBase {

  //About: instanciate the Talons
  public final TalonSRX conveyor_one = new TalonSRX(35);
  public final TalonSRX conveyor_two = new TalonSRX(32);
  public final TalonFX converyor_up = new TalonFX(31);

  public FeederToShooter() {
    converyor_up.setNeutralMode(NeutralMode.Coast);
  }

  //Name: Brennan
  //About: make the "power cell" go forward 
  public void intaketoShooter(){
    conveyor_one.set(ControlMode.PercentOutput, .4);
    conveyor_two.set(ControlMode.PercentOutput, -.5);
    converyor_up.set(ControlMode.PercentOutput, .6);
    return;
  }

  //Name: Brennan 
  //About: make the motors go backward
  public void reverseIntake(){
    conveyor_one.set(ControlMode.PercentOutput, -0.4);
    conveyor_two.set(ControlMode.PercentOutput, 0.2);
    converyor_up.set(ControlMode.PercentOutput, -0.4);
  }

  //Name: Brennan 
  //About: stop the motors  
  public void Stop(){
    conveyor_one.set(ControlMode.PercentOutput, 0.0);
    conveyor_two.set(ControlMode.PercentOutput, 0.0);
    converyor_up.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
  }
}
