/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;

public class drivetrainShifter extends CommandBase {
  private final DriveSubsystem drivesubsystem;

  public drivetrainShifter(DriveSubsystem drive) {
    drivesubsystem = drive;
    addRequirements(drivesubsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    //Name: Brennan 
    //About: enable the piston to shift between drive and elevator
    drivesubsystem.shiftPiston();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
