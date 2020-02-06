/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class FeederToShooter extends SubsystemBase {

  //About: instanciate the Talons
  public final TalonSRX conveyor_one = new TalonSRX(35);
  public final TalonSRX conveyor_two = new TalonSRX(32);
  public final TalonSRX conveyor_three = new TalonSRX(33);
  public final TalonSRX conveyor_four = new TalonSRX(34);
  public final TalonFX converyor_up = new TalonFX(31);

  public FeederToShooter() {


  }

  //About: make the "power cell" go forward 
  public void intaketoShooter(){
    conveyor_one.set(ControlMode.PercentOutput, .5);
    conveyor_two.set(ControlMode.PercentOutput, .5);
    conveyor_three.set(ControlMode.PercentOutput, .5);
    conveyor_four.set(ControlMode.PercentOutput, -.7);
    converyor_up.set(ControlMode.PercentOutput, .3);
    return;
  }

  //make go backward
  public void reverseIntake(){
    conveyor_one.set(ControlMode.PercentOutput, -0.5);
    conveyor_two.set(ControlMode.PercentOutput, -0.5);
    conveyor_three.set(ControlMode.PercentOutput, -0.5);
    conveyor_four.set(ControlMode.PercentOutput, 0.7);
    converyor_up.set(ControlMode.PercentOutput, -0.3);
  }

  //stop 
  public void shitStop(){
    conveyor_one.set(ControlMode.PercentOutput, 0.0);
    conveyor_two.set(ControlMode.PercentOutput, 0.0);
    conveyor_three.set(ControlMode.PercentOutput, 0.0);
    conveyor_four.set(ControlMode.PercentOutput, 0.0);
    converyor_up.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
  }
}