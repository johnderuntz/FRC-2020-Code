/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//import commands 
import frc.robot.commands.DriveCommands.MotionMagic;
import frc.robot.commands.DriveCommands.TimedLimeDrive;
import frc.robot.commands.shootercommand.AutoShooter.AlignandShoot;

//Import the subsystems 
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederToShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;


public class SimpleAuto2 extends SequentialCommandGroup {
  
  public SimpleAuto2(DriveSubsystem drive, Limelight lime, Shooter shoot, FeederToShooter FtoS) {

    super(
      new TimedLimeDrive(drive, lime),
      new AlignandShoot(lime, shoot, FtoS, 1.00, 0.00, 5.00),
      new MotionMagic(-2.0, -2.0, drive)  
    );
  }
}
