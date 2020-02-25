/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

//About: import other classes
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class LimeDrive extends CommandBase {
  private final DriveSubsystem drivesubsystem;
  private final Limelight limelight;
  private final DoubleSupplier m_speed; 

  public LimeDrive(DoubleSupplier speed, DriveSubsystem m_drive, Limelight m_limelight) {
    drivesubsystem = m_drive;
    limelight = m_limelight;
    m_speed = speed;

    addRequirements(m_drive);
    addRequirements(m_limelight);
  }

  @Override
  public void initialize() {
    //About: start the brake so you don't overshoot
    drivesubsystem.ebrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Name: Brennan 
    /*
      About :Calculates level of adjustment required to shift the robot to the target on-screen
      Remember to reconfigure Kp and minPower depending on the driving surface,
      esp. before competition
    */
    double headingError = limelight.getHorizontalOffset();
    double steeringAdjust = 0.0f;
    double kP = 0.05;
    double minPower = 0.0;

    if (limelight.validTarget()){
      if(limelight.getHorizontalOffset() > 1){
        steeringAdjust = kP * headingError - minPower;
      }
      else if (headingError < 1){ 
        steeringAdjust = kP *headingError + minPower;
      }
      drivesubsystem.ArcadeDrive(m_speed.getAsDouble(), steeringAdjust);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //About: end the brake mode for standard drive
    drivesubsystem.no_ebrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (limelight.getHorizontalOffset() == 0){
      return true;
    }
    else{
    return false;
    }
  }
}
