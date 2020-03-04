/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//import commands 
import frc.robot.commands.DriveCommands.MotionMagic;
import frc.robot.commands.DriveCommands.TimedLimeDrive;
import frc.robot.commands.shootercommand.GroundFeeder;
import frc.robot.commands.shootercommand.AutoShooter.AlignandShoot;

//Import the subsystems 
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederToShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.BallFeeder;

public class BaseAndTrench extends SequentialCommandGroup {
  
  public BaseAndTrench(DriveSubsystem drive, Limelight lime, Shooter shoot, FeederToShooter FtoS, BallFeeder ball) {
    
    super(
      new AlignandShoot(lime, shoot, FtoS, 0.00, 0.00, 3.00),
      new MotionMagic(0.4, -0.4, drive),
      new ParallelCommandGroup(
        new GroundFeeder(ball),
        new MotionMagic(7.0, 7.0, drive)
      ),
      new MotionMagic(-7.0, -7.0, drive),
      new TimedLimeDrive(drive, lime),
      new AlignandShoot(lime, shoot, FtoS, 0.00, 0.00, 2.00)
    );
  }
}
