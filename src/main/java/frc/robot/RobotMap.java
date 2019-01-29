/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;

//  1/sqrt(2) = 0.7071067812


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  static double sqrt2 = 0.7071067812;

  static double[] FRPV = {-sqrt2, -sqrt2}; //Positional Vectors
  static double[] FLPV = {-sqrt2, sqrt2};
  static double[] BRPV = {sqrt2, -sqrt2};
  static double[] BLPV = {sqrt2, sqrt2};
  
  static double[] FRRV = {1, 0}; //Rotational Vectors
  static double[] FLRV = {1, 0};
  static double[] BRRV = {-1, 0};
  static double[] BLRV = {-1, 0};

  static int FRMotorCh = 3; //Channels
  static int FLMotorCh = 1;
  static int BRMotorCh = 2;
  static int BLMotorCh = 0;

  VictorSP FRMotor = new VictorSP(FRMotorCh); //Motor Controllers 
  VictorSP FLMotor = new VictorSP(FLMotorCh);
  VictorSP BRMotor = new VictorSP(BRMotorCh);
  VictorSP BLMotor = new VictorSP(BLMotorCh);

}
