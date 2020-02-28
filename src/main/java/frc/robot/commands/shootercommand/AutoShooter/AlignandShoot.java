/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shootercommand.AutoShooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

//About: import the required subsystems 
import frc.robot.subsystems.FeederToShooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class AlignandShoot extends CommandBase {
  //About: set the requried subsystems 
  private final Limelight m_limelight;
  private final Shooter m_shooter;
  private final FeederToShooter m_feedertoshooter;
  private final Double m_time;

  //create the doubles for time 
  double length;
  double endtime;
  public AlignandShoot(Limelight lime, Shooter shoot, FeederToShooter FandS, Double speed, Double angle, Double time) {
    m_limelight = lime;
    m_shooter = shoot;
    m_feedertoshooter = FandS;
    m_time = time;

    //About: add the required subsystems
    addRequirements(m_limelight);
    addRequirements(m_shooter);
    addRequirements(m_feedertoshooter);

    //start the timer
    long startTime = System.currentTimeMillis();
    length = m_time * 1000;
    endtime = startTime + length;
  }

  //About: configure the motors for the proper mode 
  @Override
  public void initialize() {
    m_limelight.setLED(3);
    m_shooter.configClosedLoop();
    m_shooter.ConfigPositonangle();
    System.out.println("Starting to Shoot");
  }


  //About: set the angle and fire when it reaches the desired RPM
  @Override
  public void execute() {
    m_shooter.setShootSpeed(m_limelight.getShooterVelocity());
    if(m_shooter.getTotalShooterRPM() >= (m_limelight.getShooterVelocity()-100)){
      m_feedertoshooter.intaketoShooter();
    }

    m_shooter.changeHoodPosition(m_shooter.hoodAngleTable());
    if ((m_shooter.getHoodAngle() <= (m_shooter.getHoodAngle()-0.2)) && (m_shooter.getHoodAngle() <= (m_shooter.getHoodAngle() + 0.2))){
      m_shooter.setHoodPower(0);
    }  
  }

  //About: turn off the conveyor motors to the robot 
  @Override
  public void end(boolean interrupted) {
    m_feedertoshooter.Stop();
    m_shooter.setHoodPower(0);
    m_shooter.setPower(0);
    System.out.println("Stopping the Shooter");
  }

  //About: turns off the command when the time runs out
  @Override
  public boolean isFinished() {
    if(System.currentTimeMillis() >= endtime){
      System.out.println("The time has ended");
      return true;
    }
    return false;
  }
}