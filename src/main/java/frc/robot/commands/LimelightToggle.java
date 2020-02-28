/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

//About:Import subsystems
import frc.robot.subsystems.Limelight;

public class LimelightToggle extends CommandBase {
  private final Limelight m_limelight;

  public LimelightToggle(Limelight limelight) {
    m_limelight = limelight;

    addRequirements(m_limelight);
  }

 
  @Override
  public void initialize() {
  }

  //About: will toggle the limelight on 
  @Override
  public void execute() {
    m_limelight.setLED(3);
  }

  //About: will toggle the limelight off
  @Override
  public void end(boolean interrupted) {
    m_limelight.setLED(1);
  }


  //About: set when the limelight will turn off 
  @Override
  public boolean isFinished() {
    return false;
  }
}
