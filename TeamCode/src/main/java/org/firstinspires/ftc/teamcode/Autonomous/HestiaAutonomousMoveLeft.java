//This code will make the robot park and nothing else
package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "HestiaAutonomousMoveLeft")

public class HestiaAutonomousMoveLeft extends LinearOpMode {
        //calls all the motors
        DcMotor frontLeftMotor;
        DcMotor frontRightMotor;
        DcMotor backLeftMotor;
        DcMotor backRightMotor;
        
        //all code listed after here happens when opmode is active.
        public void runOpMode(){
            waitForStart();
            //maps the motors to what they are named on the drivers hub
            frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
            frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
            backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
            backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
  
            //sets power to all the motors so that it moves left
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(1100);
            //sets power to all the motors to move backwards a small amount
            frontRightMotor.setPower(.4);
            frontLeftMotor.setPower(-.4);
            backRightMotor.setPower(.4);
            backLeftMotor.setPower(-.4);
            sleep(200);
            //sets the power of the motors to zero
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            //causes this to happen for 29 seconds (which is the whole duration of autonomous)
            sleep(29000);
            telemetry.update();
     }
}
