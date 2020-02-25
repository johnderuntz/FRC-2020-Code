/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//About: instanciate subsystems 
import frc.robot.subsystems.FeederToShooter;

public class ReverseIntake extends CommandBase {
  private final FeederToShooter m_feed;

  public ReverseIntake(FeederToShooter feed) {
    m_feed = feed;
    addRequirements(feed);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    //Name: Brennan 
    //About: turn the convey on
    m_feed.reverseIntake();
  }
  
  @Override
  public void end(boolean interrupted) {
    //Name: Brennan 
    //About: turn the convey off
    m_feed.Stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
