package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@TeleOp (name = "HestiaTeleOpRobotOne")

public class HestiaTeleOpRobotOne extends OpMode {
    //names the motors and servos
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor viperTurnMotor;
    DcMotor viperTurnMotorTwo;
    DcMotor viperSlideMotor;
    private Servo clawPartOne;
    private Servo clawPartZero;

    public void init(){
        //On startup, this code maps the motors and servos to their designations on the drivers hub
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        viperTurnMotor = hardwareMap.dcMotor.get("viperTurnMotor");
        viperTurnMotorTwo = hardwareMap.dcMotor.get("viperTurnMotorTwo");
        viperSlideMotor = hardwareMap.dcMotor.get("viperSlideMotor");
        clawPartOne = hardwareMap.servo.get("clawPartOne");
        clawPartZero = hardwareMap.servo.get("clawPartZero");
        //Whenever the arm/viperTurnMotor is put to sleep, it stays in the same position and brakes(will be found in the if statements for the arm)
        viperTurnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperTurnMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);




}


    //Everything under loop happens during the whole period that the opmode is active
    public void loop(){
        //Different movements on the joysticks are configured to different variables
        double forward = .4*(-gamepad1.left_stick_y);
        double turn = .25*(gamepad1.right_stick_x);
        double strafe = .55*(gamepad1.left_stick_x);
        double viperTurn = .45*(gamepad2.left_stick_y);
        double viperSlide = gamepad2.right_stick_y;



        //tells the motors that drive the robot which way to go for forward and back, strafing, and turning

        double leftfront = (- forward - turn - strafe);
        double rightfront = (forward - turn - strafe);
        double leftrear = (-forward - turn + strafe);
        double rightrear = (forward - turn + strafe);
        
        
        //sets the power of the motors to the previous variables
        frontLeftMotor.setPower(leftfront);
        frontRightMotor.setPower(rightfront);
        backLeftMotor.setPower(leftrear);
        backRightMotor.setPower(rightrear);
        viperTurnMotor.setPower(viperTurn);
        viperTurnMotorTwo.setPower(-viperTurn);
        viperSlideMotor.setPower(viperSlide);



        //the following code sets the positions of the servos
        //double getServoPosition = 0;
        //boolean servoPositionOpen = getServoPosition==0;
        //if gamepad 2 left_bumper and the variable getServoPosition is equal to zero
        if (gamepad2.left_bumper){
            //set the claw position to .2 (closed claw)
            clawPartOne.setPosition(.14);
            clawPartZero.setPosition(.86);
            //set the servoPosition variable to .2
            // getServoPosition = 0.2;
        //if gamepad 2 right_bumper and the variable getServoPosition is equal to .2
        } //boolean servoPositionClose= getServoPosition ==0.2;
        if (gamepad2.right_bumper ){
            //set the claw position to 0 (open claw)
            clawPartOne.setPosition(0);
            clawPartZero.setPosition(1);
            //set the servoPosition variable to 0
            // getServoPosition = 0;
        }
    }
}
