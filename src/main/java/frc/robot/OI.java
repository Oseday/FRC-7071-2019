/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import java.lang.Math;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  static int JoyPort0 = 0;
  static int JoyPort1 = 1; 

  Joystick DriverStick = new Joystick(JoyPort0);
  Joystick ArmStick = new Joystick(JoyPort1);

  double ThrustY = 0;
  double ThrustX = 0;
  double RotV = 0;

  static double clamper(double x) {
    if (Math.abs(x)<0.02) {
      return 0;
    }else{
      return x;
    }
  }

  void UpdateAxis() {
    //double dd = System.currentTimeMillis();
    ThrustY = clamper(DriverStick.getRawAxis(1)*-1); //(dd/1000)%5/5;
    ThrustX = clamper(DriverStick.getRawAxis(0)*-1);
    RotV = clamper(DriverStick.getRawAxis(2) - DriverStick.getRawAxis(3));
    //System.out.println("" + ThrustX);
  }


  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
