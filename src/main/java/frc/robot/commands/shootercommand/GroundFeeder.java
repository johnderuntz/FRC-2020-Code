/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import subsystems 
import frc.robot.subsystems.BallFeeder;

public class GroundFeeder extends CommandBase {
  private final BallFeeder m_ballFeeder;

  public GroundFeeder(BallFeeder ball) {
    m_ballFeeder = ball; 
  }

  @Override
  public void initialize() {
  }

  //About: turn the feeder on 
  @Override
  public void execute() {
    m_ballFeeder.feederOn();
    m_ballFeeder.shiftFeeder();
  }

  //About: turn the feedre off
  @Override
  public void end(boolean interrupted) {
    m_ballFeeder.feederOFF();
    m_ballFeeder.shiftFeeder();
  }

  //About: turns the thing off 
  @Override
  public boolean isFinished() {
    return false;
  }
}
