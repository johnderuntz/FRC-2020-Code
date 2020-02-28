/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import the subsystems
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class shooterAlign extends CommandBase {
  private final Limelight m_limelight;
  private final Shooter m_shooter;
  private boolean hood_isFinished;

  public shooterAlign(Limelight limelight, Shooter shooter) {
    m_limelight = limelight;
    m_shooter = shooter;

    addRequirements(m_limelight);
    addRequirements(m_shooter);
  }


  //About: Configure the motors for the change in position 
  @Override
  public void initialize() {
    m_limelight.setLED(3);
    m_shooter.ConfigPositonangle();
    hood_isFinished = false;

  }

  //About: set the hood angle 
  @Override
  public void execute() { 
    double kangle = m_shooter.hoodAngleTable();
    m_shooter.changeHoodPosition(kangle);
    
    if ((m_shooter.getHoodAngle() <= (m_shooter.getHoodAngle() - 0.2)) && (m_shooter.getHoodAngle() <= (m_shooter.getHoodAngle() + 0.2))){
      hood_isFinished = true;
      System.out.println("it has reached its angle");
    }
  }

  //About: when the hood finishes set the power to zero 
  @Override
  public void end(boolean interrupted) {
    m_limelight.setLED(3);
    m_shooter.setHoodPower(0);
  }

  @Override
  public boolean isFinished() {
    return hood_isFinished;
  }
}
