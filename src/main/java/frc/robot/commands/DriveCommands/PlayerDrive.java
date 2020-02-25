/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.DriveCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;

public class PlayerDrive extends CommandBase {
  private final DriveSubsystem m_drivetrain;
  private final DoubleSupplier m_speed;
  private final DoubleSupplier m_rotation;
  
  public PlayerDrive(DoubleSupplier speed, DoubleSupplier rotation, DriveSubsystem drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_drivetrain = drivetrain;
    m_speed = speed;
    m_rotation = rotation;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.ArcadeDrive(m_speed.getAsDouble(), m_rotation.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.PortedArcadeDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
