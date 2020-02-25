/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import drive subsystem 
import frc.robot.subsystems.DriveSubsystem;

public class MotionMagic extends CommandBase {
 private final DriveSubsystem m_drive;
 private final Double m_right;
 private final Double m_left;

 public boolean magic_isFinished;
  public MotionMagic(Double right, Double left, DriveSubsystem drive) {
    m_drive = drive;
    m_right = right;
    m_left = left; 

    addRequirements(m_drive);
  }

  //About: Configure the Motors for motion magic  
  @Override
  public void initialize() {
    m_drive.setMotionMagicMode();
    m_drive.resetEncoders();
    magic_isFinished = false;
  }

  //About: make the motion magic go to the desired wheel rotations specified
  @Override
  public void execute() {
    if(!magic_isFinished){
      m_drive.elevatorMotionMagic(m_right, m_left);
    }

    if((Math.abs(m_drive.getRightEncoderDistanceMeters()*2) >= Math.abs(m_right)) &&
    (Math.abs(m_drive.getLeftEncoderDistanceMeters()*2) >= Math.abs(m_left))){
      magic_isFinished = true;  
    }
  }

  //About: reconfig it back to the standard drive train 
  @Override
  public void end(boolean interrupted) {
    m_drive.configStandardDrive();
  }

  //About: ends when magic is finished returns true
  @Override
  public boolean isFinished() {
    return magic_isFinished;
  }
}
