/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    public static final double distBetweenWheelsInches = 28;//26.84603809585759;
    public static final double gearRatio = 1 / 8.45; //need to double check 
    public static final double wheelDiameterInches = 6;//18;
    public static final double wheelCircumferenceInches = wheelDiameterInches * Math.PI;
    public static final double encoderTicksPerRev = 2048;
    public static final int voltageSaturation = 10;
    public static final double kGearRatio = 8.45;

    public static final double kS = 0.358;
    public static final double kV = 3.02;
    public static final double kA = 0.249;
    public static final double kP = 0.0;//0.0405;
    public static final double kD = 0.0;


    //About: create a seperate class to store all of the limelight variables 
    public static final class LimelightConstants{

    //About: Set the camera height and goal height for distance calculations 
    public static final double cameraHeight = 2.583; //Camera height in meters
    public static final double goalHeight = 8.1875; //Hatch panel height in meters
    public static final double mountAngle = .436332; //26.3258

    }
}
