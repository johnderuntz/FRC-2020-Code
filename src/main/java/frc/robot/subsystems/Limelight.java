/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import java.lang.Math;

public class Limelight extends SubsystemBase {
  //About: Instance NetworkTable to obtain realtime data from the Limelight
  public NetworkTable ll = NetworkTableInstance.getDefault().getTable("limelight");

  public NetworkTableEntry target = ll.getEntry("tv");
  public NetworkTableEntry horizontalOffset = ll.getEntry("tx");
  public NetworkTableEntry verticalOffset = ll.getEntry("ty");
  public NetworkTableEntry targetArea = ll.getEntry("ta");
  public NetworkTableEntry skew = ll.getEntry("ts");
  public NetworkTableEntry led = ll.getEntry("ledMode");
  public NetworkTableEntry cameraMode = ll.getEntry("camMode");
  
  public Limelight() {

  }

  //---------------------------Place Getters Here-------------------------------

  //Name: Matthew, Brennan  
  //About: Returns x offset angle of the robot from the target
  public double getHorizontalOffset(){
    return horizontalOffset.getDouble(0.0);
  }
  
  //Name: Matthew, Brennan 
  //About: Returns y offset angle of the robot from the target
  public double getVerticalOffset(){
    return verticalOffset.getDouble(0.0);
  }
  
  //Name: Brennan 
  //About: Returns the angle difference from the robot to the target 
  public double getSkew(){
    return skew.getDouble(0.0);
  }

  //Name: Matthew, Brennan 
  //About: Returns if the limelight has a valid target or not 
  public Boolean validTarget(){
    //Returns true if target is in frame, false is no valid target
    if (target.getDouble(0) == 1) return true;
    else return false;
  }

  //Name: Matthew, Brennan 
  //About: Returns the largest target the limelight can see 
  public double LargestTarget(){
    return targetArea.getDouble(0.0);
  }

  //Name: Brennan, Dante(aka math man)  
  //About: calc the distance to the target
  public double distanceToTarget(){
    double offsetAngle = verticalOffset.getDouble(0);
    System.out.println("Distance to target:" + (Constants.LimelightConstants.goalHeight-Constants.LimelightConstants.cameraHeight)/Math.tan(Constants.LimelightConstants.mountAngle + ((offsetAngle*2*Math.PI)/360)));
    return (Constants.LimelightConstants.goalHeight-Constants.LimelightConstants.cameraHeight)/Math.tan(Constants.LimelightConstants.mountAngle + ((offsetAngle*2*Math.PI)/360));
  }

  //Name: Brennan, Dante(aka math man)
  //About: Uses the distance to find the optimal shooter velocity at that range 
  public double getShooterVelocity(){
    double setVelocity = 0;

    if ((distanceToTarget() >= 0 ) && (distanceToTarget() < .5 )){
      setVelocity = 2178.19;
    }
    else if (( distanceToTarget() >= .5) && ( distanceToTarget() < 3)){
      setVelocity = 2487.89;
    }
    else if (( distanceToTarget() >= 3) && ( distanceToTarget() < 4.5)){
      setVelocity = 2717.19;
    }
    else if ((distanceToTarget() >= 4.5) &&( distanceToTarget() < 7.5)){
      setVelocity = 4929.93;
    }
    else if ((distanceToTarget() >= 7.5) &&( distanceToTarget() < 9.5)){
      setVelocity = 4012.73;
    }
    else if ((distanceToTarget() >= 9.5) &&( distanceToTarget() < 11.5)){
      setVelocity = 4127.38;
    }
    else if ((distanceToTarget() >= 11.5) &&( distanceToTarget() < 13.5)){
      setVelocity = 4643.31;
    }
    else if ((distanceToTarget() >= 13.5) &&( distanceToTarget() < 14.5)){
      setVelocity = 4929.93;
    }
    else if ((distanceToTarget() >= 14.5) &&( distanceToTarget() < 15.5)){
      setVelocity = 5044.58;
    }
    else if ((distanceToTarget() >= 15.5) &&( distanceToTarget() < 17.5)){
      setVelocity = 6305.73;
    }
    else if ((distanceToTarget() >= 17.5) &&( distanceToTarget() < 20.0)){
      setVelocity = 6191.08;
    }
    else{
      setVelocity = 0;
    }

    System.out.println("Set Shooter velocity" + setVelocity);
    return setVelocity;
  }

  //---------------------------Place Setters Here-------------------------------
  
  //Name: Matthew, Brennan 
  //About: sets the LED from off to on 
  public void setLED(int mode){
    //Sets LED mode (1: Off, 2: Blink, 3: On)
    switch (mode){
      case 1:
        led.setDouble(1);
        break;
      case 2:
        led.setDouble(2);
        break;
      case 3:
        led.setDouble(3);
        break;
      default:
        led.setDouble(0);
        break;
    }
    return;
  }

  //Name: Matthew 
  //About: Toggles vision processing on the Limelight (0: On, 1: Off with Driver Mode for increased exposure)
  public void camMode(int mode){
    if (mode == 1) cameraMode.setDouble(1);
    else cameraMode.setDouble(0);
    return;
  }

  //---------------------------Place Others Here-------------------------------
  
  //Name: Matthew, Brennan 
  //About: Rotates the robot to the target based on the horizontal offset
  public double rotatetoTarget(double PID){
    //Calculates power necessary to shift drivetrain and align with the target
    double power = horizontalOffset.getDouble(0)*PID;
    return power;
  }

  

  @Override
  public void periodic() {
    SmartDashboard.getNumber("Distance to Target", distanceToTarget());
  }
}
