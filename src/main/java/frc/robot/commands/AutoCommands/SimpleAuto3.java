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
import frc.robot.commands.shootercommand.AutoShooter.AlignandShoot;

//Import the subsystems 
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederToShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;


public class SimpleAuto3 extends SequentialCommandGroup {
  
  public SimpleAuto3(DriveSubsystem drive, Limelight lime, Shooter shoot, FeederToShooter FtoS) {

    super(
      new AlignandShoot(lime, shoot, FtoS, 0.00, 0.00, 4.00),
      new MotionMagic(-4.0, -4.0, drive)  
    );
  }
}
