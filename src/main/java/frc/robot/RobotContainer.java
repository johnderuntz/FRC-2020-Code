/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.DriveCommands.LimeDrive;

//import all the commands 
import frc.robot.commands.DriveCommands.PlayerDrive;
import frc.robot.commands.AutoCommands.SimpleAuto1;
import frc.robot.commands.DriveCommands.drivetrainShifter;
import frc.robot.commands.shootercommand.FeedToWheel;
import frc.robot.commands.shootercommand.ReverseIntake;
import frc.robot.commands.shootercommand.shooterAlign;
import frc.robot.commands.shootercommand.FlyWheel;
import frc.robot.commands.shootercommand.ResetingHoodAngle;
import frc.robot.commands.shootercommand.GroundFeeder;
import frc.robot.commands.shootercommand.GroundFeederShifter;
import frc.robot.commands.LimelightToggle;
//import all the subsystems 
import frc.robot.subsystems.BallFeeder;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FeederToShooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class RobotContainer {


  //Define Subsystems Here
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final FeederToShooter m_conveyor = new FeederToShooter();
  private final Limelight m_limelight = new Limelight();
  private final Shooter m_shooter = new Shooter();
  private final FeederToShooter m_feed = new FeederToShooter();
  private final BallFeeder m_ballfeeder = new BallFeeder();

  //Define Anything Else Here

  //Instanciate the robot controllers 
  public static final XboxController driver = new XboxController(0);
  public static final XboxController operator = new XboxController(1);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_driveSubsystem.resetEncoders();
    m_driveSubsystem.resetHeading();
    m_driveSubsystem.resetOdometry();


    //Assign Default Commands
    m_driveSubsystem.setDefaultCommand(new PlayerDrive
    (
      () -> driver.getX(Hand.kRight),
      () -> driver.getY(Hand.kLeft), m_driveSubsystem)
    );
  }

  private void configureButtonBindings() {
    //About: set the drivers buttons 
    new JoystickButton(driver, Button.kStickRight.value).whenPressed(new drivetrainShifter(m_driveSubsystem));
    new JoystickButton(driver, Button.kB.value).toggleWhenPressed(new GroundFeeder(m_ballfeeder));
    new JoystickButton(driver, Button.kA.value).whenHeld(new LimeDrive(m_driveSubsystem, m_limelight));
    new JoystickButton(driver, Button.kY.value).whenPressed(new GroundFeederShifter(m_ballfeeder));
    new JoystickButton(driver, Button.kStickRight.value).toggleWhenPressed(new LimelightToggle(m_limelight));
    new JoystickButton(driver, Button.kX.value)
      .whenPressed(()-> m_driveSubsystem.ebrake())
      .whenReleased(()-> m_driveSubsystem.no_ebrake());
    new JoystickButton(driver, Button.kBumperLeft.value)
      .whenPressed(()-> m_driveSubsystem.setMaxOutput(.3))
      .whenReleased(()-> m_driveSubsystem.setMaxOutput(1));
    
    
    //About: set the dpad driver buttons 

    
    //About: set the operators buttons 
    new JoystickButton(operator, Button.kA.value).whileHeld(new FeedToWheel(m_conveyor));
    new JoystickButton(operator, Button.kB.value).whenPressed(new ReverseIntake(m_feed));
    new JoystickButton(operator, Button.kBumperLeft.value).whenPressed(new shooterAlign(m_limelight, m_shooter));
    new JoystickButton(operator, Button.kBumperRight.value).whenPressed(new FlyWheel(m_limelight, m_shooter));
    new JoystickButton(operator, Button.kX.value).whenPressed(new ResetingHoodAngle(m_shooter));
    new JoystickButton(operator, Button.kY.value)
      .whenPressed(()-> m_shooter.setPower(1))
      .whenReleased(()-> m_shooter.setPower(0));
    new JoystickButton(operator, Button.kBack.value)
      .whenPressed(()-> m_shooter.setHoodPower(-.6))
      .whenReleased(()-> m_shooter.setHoodPower(0));
    new JoystickButton(operator, Button.kStart.value)
      .whenPressed(()-> m_shooter.setHoodPower(.8))
      .whenReleased(()-> m_shooter.setHoodPower(0));  
    
      //About: set the dpad operator buttons 
    

  }

  public Command getAutonomousCommand() {
    return new SimpleAuto1(m_driveSubsystem, m_limelight, m_shooter, m_feed);
  }
}

