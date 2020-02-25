/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DrivingWithGyro extends CommandBase {
  private final Double m_angle;
  private final Double m_time;
  private final DriveSubsystem m_drive;

  double length;
  double endtime;
   
  public DrivingWithGyro(Double angle, Double time, DriveSubsystem drive) {
    m_angle = angle;
    m_time = time;
    m_drive = drive;

    long startTime = System.currentTimeMillis();
    length = m_time * 10000;
    endtime = startTime + length;
  }

  //About: config the motors for proper drives 
  @Override
  public void initialize() {
    m_drive.configStandardDrive();
    m_drive.ebrake();
  }

  //About: set the angle you want the robot to align to 
  @Override
  public void execute() {
    m_drive.driveWithGyro(m_angle);
  }

  //About: when the command ends make the robot stop and turn the e brake back off
  @Override
  public void end(boolean interrupted) {
    m_drive.no_ebrake();
    m_drive.ArcadeDrive(0, 0);
  }

  //About: make the command end when it reaches its target angle 
  @Override
  public boolean isFinished() {
    if(m_drive.getYaw() == m_angle){
      System.out.println("The robot is aligned with the angle");
      return true;
    }
    else if (System.currentTimeMillis() >= endtime){
      return true;
    }
    return false;
  }
}
