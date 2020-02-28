/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

//About: Import subsystems 
import frc.robot.subsystems.BallFeeder;

public class GroundFeederShifter extends CommandBase {
  private final BallFeeder m_ballfeeder;

  public GroundFeederShifter(BallFeeder ballfeeder) {
    m_ballfeeder = ballfeeder;

    addRequirements(m_ballfeeder);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_ballfeeder.shiftFeeder();
    m_ballfeeder.shiftFeeder2();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
