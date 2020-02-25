/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import subsystems 
import frc.robot.subsystems.Shooter;

public class ResetingHoodAngle extends CommandBase {
  private final Shooter m_shooter;
  private boolean hood_isFinished;

  public ResetingHoodAngle(Shooter shoot) {
    m_shooter = shoot;

    addRequirements(m_shooter);
  }

  //About: configure the motors 
  @Override
  public void initialize() {
    m_shooter.configHoodClosedLoop();
    hood_isFinished = false;
  }

  //About: set the hood position back to zero
  @Override
  public void execute() {
    m_shooter.resetHoodwithLimit();
    System.out.println("Hood is moving down");

    if(m_shooter.getHoodLimitSwitch()){
      System.out.println("The Hood has hit the limit switch");
      hood_isFinished = true;
    }
    else if (m_shooter.getHoodAngle() == 0){
      System.out.println("The Hood has reached angle 0");
      hood_isFinished = true;
    }
  }

  //About: set the hood power back down to zero 
  @Override
  public void end(boolean interrupted) {
    m_shooter.setHoodPower(0);
    m_shooter.resetHoodEncoder();
  }

  //About: stop the hood
  @Override
  public boolean isFinished() {
    return hood_isFinished;
  }
}
