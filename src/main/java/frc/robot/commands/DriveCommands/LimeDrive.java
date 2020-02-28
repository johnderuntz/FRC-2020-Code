/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class LimeDrive extends CommandBase{
  private final DriveSubsystem drivetrain;
  private final Limelight limelight;
  /*
  Uses data from the Limelight subsystem to correct
  the robot's horizontal offset from the target
  */
  public LimeDrive(DriveSubsystem drive, Limelight lime){
    drivetrain = drive;
    limelight = lime;

    addRequirements(drive);
    addRequirements(lime);
  }

  @Override
  public void initialize(){
    limelight.setLED(3);
  }

  @Override
  public void execute(){
    /*
      Calculates level of adjustment required to shift the robot to the target on-screen
      Remember to reconfigure Kp and minPower depending on the driving surface,
      esp. before competition
    */
    double headingError = limelight.getHorizontalOffset() + 5;
    double steeringAdjust = 0.0f;
    double Kp = 0.01;
    double minPower = 0.32;

  
    if (limelight.validTarget()){
      if (headingError > 1.3){
        steeringAdjust = Kp * headingError + minPower;
      }else if (headingError < 1.0){
        steeringAdjust = Kp * headingError - minPower;
      }
    }

    drivetrain.ArcadeDrive(steeringAdjust, RobotContainer.driver.getRawAxis(1));
    Timer.delay(.005);
  }

  @Override
  public void end(boolean interrupted){
    limelight.setLED(1);
    System.out.println("Aligned with the target");
  }

  @Override
  public boolean isFinished(){
    if (((limelight.getHorizontalOffset() - 5) >= -0.1 && (limelight.getHorizontalOffset() - 5) <= 0.1)){
      return true;
    }
    else{
    return false;
    }
  }
  
}