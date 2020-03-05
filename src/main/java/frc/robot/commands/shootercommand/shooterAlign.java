/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import the subsystem
import frc.robot.subsystems.Shooter;

public class shooterAlign extends CommandBase {
  private final Shooter m_shooter;

  public shooterAlign(Shooter shooter) {
    m_shooter = shooter;

    addRequirements(m_shooter);
  }


  @Override
  public void initialize() {
  }

  //About: set the hood angle 
  @Override
  public void execute() { 
    m_shooter.shiftTheHood();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
