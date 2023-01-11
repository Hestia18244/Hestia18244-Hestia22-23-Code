package org.firstinspires.ftc.teamcode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@TeleOp (name = "RobotTwoTeleOp")

public class RobotTwoTeleOp extends OpMode {

    //names the motors and servos
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor viperSlideMotor;
    private Servo clawPartOne;
    private Servo clawPartZero;

    public void init(){

        //On startup, this code maps the motors and servos to their designations on the drivers hub
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        //viperSlideMotor = hardwareMap.dcMotor.get("viperSlideMotor");
        //clawPartOne = hardwareMap.servo.get("clawPartOne");
        //clawPartZero = hardwareMap.servo.get("clawPartZero");

        //whenever the cascading slide motor is set to zero power, the motors tries to brake
        //viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);




    }


    //Everything under loop happens during the whole period that the opMode is active
    public void loop() {

        //Different movements on the joysticks are configured to different variables
        //decimals are used to reduce power
        double forward = -.4 * (gamepad1.left_stick_y);
        double turn = -.55 * (-gamepad1.right_stick_x);
        double strafe = -.55 * (-gamepad1.left_stick_x);
        double viperSlide = .35*(gamepad2.right_stick_y);

        //tells the motors that drive the robot which way to go for forward and back, strafing, and turning
        double leftfront = (-forward - turn - strafe);
        double rightfront = (forward - turn - strafe);
        double leftrear = (-forward - turn + strafe);
        double rightrear = (forward - turn + strafe);

        //sets the power of the motors to the previous variables
        frontLeftMotor.setPower(leftfront);
        frontRightMotor.setPower(rightfront);
        backLeftMotor.setPower(leftrear);
        backRightMotor.setPower(rightrear);
        //viperSlideMotor.setPower(viperSlide);


        //the following code sets the positions of the servos
        //if gamepad2 left bumper is pressed
        if (gamepad2.left_bumper) {
            //set the claw servos' position to be closed
            clawPartOne.setPosition(.80);
            clawPartZero.setPosition(.20);
        }
        //if gamepad2 right bumper is pressed
        if (gamepad2.right_bumper) {
            //set the claw servos' position to be open
            clawPartOne.setPosition(1);
            clawPartZero.setPosition(0);
        }
    }
}