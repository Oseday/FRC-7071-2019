/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.cameraserver.CameraServer;

import java.lang.Math;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;

  public static RobotMap drives;

  public static class V { //Vector class
    static double[] New(double x, double y){
      double[] nv = {x, y};
      return nv;
    }

    static double[] Add(double[] v1, double[] v2) {
      return New(v1[0]+v2[0], v1[1]+v2[1]);
    }
  
    static double Dot(double[] v1, double[] v2) {
      return v1[0]*v2[0]+v1[1]*v2[1];
    }
  
    static double[] Mul(double[] v1, double n) {
      return New(v1[0]*n, v1[1]*n);
    }    

    static double CalcThrust(double[] PV, double[] RV){
      return V.Dot(PV,V.Add(V.New(m_oi.ThrustX, m_oi.ThrustY),V.Mul(RV,m_oi.RotV)));
    }
  }

  static double max(double x, double y) {
    return Math.max(x,y);
  }
  
  static double abs(double x) {
    return Math.abs(x);
  }

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**Tama
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    drives = new RobotMap();
    
    SmartDashboard.putData("Auto mode", m_chooser);
    //SmartDashboard.
    CameraServer.getInstance().startAutomaticCapture();

    System.out.println("Robot Start Done");
  }


  public static double TorqueMap(double x) {
    if (x < 0) {
      return -Math.sqrt(-x)/3;
    }else{
      return Math.sqrt(x)/3;
    }
  } 

  @Override
  public void robotPeriodic() {
    m_oi.UpdateAxis(); //Map the joy stick keys to class' variables

    double[] t = {
      V.CalcThrust(RobotMap.FRPV, RobotMap.FRRV)/RobotMap.sqrt2, //Calculate thrust to be applied 
      V.CalcThrust(RobotMap.FLPV, RobotMap.FLRV)/RobotMap.sqrt2,
      V.CalcThrust(RobotMap.BRPV, RobotMap.BRRV)/RobotMap.sqrt2,
      V.CalcThrust(RobotMap.BLPV, RobotMap.BLRV)/RobotMap.sqrt2,
    };
    

    double mx = max(1,max(max(abs(t[0]),abs(t[1])),max(abs(t[2]),abs(t[3])))); //Get maximum absolute value that's above 1 or 1

    t[0]=t[0]/mx; //Divide each calculated pos to max value in order to map them to values in between -1 to 1
    t[1]=t[1]/mx;
    t[2]=t[2]/mx;
    t[3]=t[3]/mx;

    for (int i = 0; i <= 3; i++) {
      t[i]=TorqueMap(t[i]);
    }

    /** 
    System.out.println("ThrustY: " + m_oi.ThrustY);
    System.out.println("Wheel 0: " + t[0]);
    System.out.println("Wheel 1: " + t[1]);
    System.out.println("Wheel 2: " + t[2]);
    System.out.println("Wheel 3: " + t[3]);
    System.out.println(" "); 
    */

    drives.FRMotor.set(t[0]);
    drives.FLMotor.set(t[1]);
    drives.BRMotor.set(t[2]);
    drives.BLMotor.set(t[3]);
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    drives.FRMotor.set(0);
    drives.FLMotor.set(0);
    drives.BRMotor.set(0);
    drives.BLMotor.set(0);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
