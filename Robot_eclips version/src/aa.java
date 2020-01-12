package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//Import all the packages and libraries and stuff

public class Robot extends TimedRobot {

  //Set all the variables
  private DifferentialDrive _myBot_right; //This will store and control information for the right motors of the bot
  private DifferentialDrive _myBot_left; //This will store and control information for the left motors of the bot
  private SpeedController _myBot_ball; //This will store and control information for the ball throwing motor
  private Joystick _myBot_joystick; //This will store the port that the joysticks on
  private Double _right_value; //What to write to the right motors
  private Double _left_value; //what to write to the left motors

  @Override
  public void robotInit() {

    _myBot_right = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    //This sets myBot_drive to a class with the motor on PWM pins 0 and 1
    
    _myBot_left = new DifferentialDrive(new PWMVictorSPX(2), new PWMVictorSPX(3));
    //This sets _myBot_left to a class with the motor on PWM pins 2 and 3
    
    _myBot_joystick = new Joystick(0);
    //Set the port for the joystick

    _myBot_ball = new PWMVictorSPX(4);
    //Assign the ball motor to PWM pin 4
  }

  @Override
  public void teleopPeriodic() {
    
    //Everything below here is to control the motors

    if(_myBot_joystick.getY() > 0 && _myBot_joystick.getX() > 0){ //If the stick is in the upper right quadrent

      //right motor is the joysticks y minus its x, making it slow down
      _right_value = _myBot_joystick.getY() - _myBot_joystick.getX();

      //left motor is the joysticks y
      _left_value = _myBot_joystick.getY();

      //This enables another driving style, so that no matter what, if y is positive,
      //the bot won't go backwards and vice versa
      //if(_right_value < 0) _right_value = 0.0;
    
    }

    if(_myBot_joystick.getY() > 0 && _myBot_joystick.getX() < 0){ //If the stick is in the upper left quadrent
    
      //right value is the y of the joystick
      _right_value = _myBot_joystick.getY();

      //the left motor is the y plus the x(where x is negative) making it slow down
      _left_value = _myBot_joystick.getY() + _myBot_joystick.getX();

      //This enables another driving style, so that no matter what, if y is positive,
      //the bot won't go backwards and vice versa
      //if(_left_value < 0) _right_value = 0.0;
    
    }

    if(_myBot_joystick.getY() < 0 && _myBot_joystick.getX() > 0){ //lower right quadrent
    
      //right motor is the y of the joystick plus the x(where x is positive) making it slow down
      _right_value = _myBot_joystick.getY() + _myBot_joystick.getX();

      //left motor is the y value
      _left_value = _myBot_joystick.getY();

      //This enables another driving style, so that no matter what, if y is positive,
      //the bot won't go backwards and vice versa
      //if(_left_value > 0) _right_value = 0.0;

    }

    if(_myBot_joystick.getY() < 0 && _myBot_joystick.getX() < 0){ //Lower left quadrent
    
      //right motor is the y of the joystick
      _right_value = _myBot_joystick.getY();

      //left motor is the y of the joystick minus the x(where x is negative), resulting in it slowing down
      _left_value = _myBot_joystick.getY() - _myBot_joystick.getX();

      //This enables another driving style, so that no matter what, if y is positive,
      //the bot won't go backwards and vice versa
      //if(_left_value > 0) _right_value = 0.0;

    }

    //Write the left and right values to the motors
    _myBot_right.tankDrive(_right_value, _right_value);
    _myBot_left.tankDrive(_left_value, _left_value);
    
    //Turn the ball motor on if the trigger is being pushed
    if(_myBot_joystick.getTrigger() == true) _myBot_ball.set(1);

  }
}