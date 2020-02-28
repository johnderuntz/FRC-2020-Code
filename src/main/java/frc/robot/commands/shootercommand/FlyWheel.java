/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;


public class FlyWheel extends CommandBase {
  private final Shooter m_shooter;
  private final Limelight m_limelight;

  public FlyWheel(Limelight limelight, Shooter shoot) {
    m_shooter = shoot;
    m_limelight = limelight;
    addRequirements(m_shooter);
    addRequirements(m_limelight);
  }

  @Override
  public void initialize() {
    m_limelight.setLED(3);
  }

  @Override
  public void execute() {
    //Name: Brennan
    //About: convert the velocity into encoder ticks and account for the gear ratio
    double convertedVelocity = (m_limelight.getShooterVelocity() * Constants.ShooterConstants.flyGearRatio);

    //About: activates the flywheels and sets the shooter velocity 
    m_shooter.setShootSpeed(convertedVelocity);
  }

  @Override
  public void end(boolean interrupted) {

    //About: turn the flywheels off
    m_shooter.setPower(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
