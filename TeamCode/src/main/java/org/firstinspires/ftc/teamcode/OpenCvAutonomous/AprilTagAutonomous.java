//this code should work for all three locations.
// This should be used when on the right side of the field (regardless of color)
package org.firstinspires.ftc.teamcode.OpenCvAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


import java.util.ArrayList;

@Autonomous
public class AprilTagAutonomous extends LinearOpMode
{
    OpenCvCamera camera;
    DcMotor frontRightMotor;
    DcMotor frontLeftMotor;
    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor viperSlideMotor;
    Servo clawPartOne;
    Servo clawPartZero;
    //declares that the pipeline is being used for this
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;
    //declares a variable for each number (based on the april tag number)
    int locationOne = 11;
    int locationTwo = 12;
    int locationThree = 13;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
        //hardware maps the motors to what they are named in the drivers hub
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        viperSlideMotor = hardwareMap.dcMotor.get("viperSlideMotor");
        clawPartOne = hardwareMap.servo.get("clawPartOne");
        clawPartZero = hardwareMap.servo.get("clawPartZero");
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == locationOne || tag.id== locationTwo || tag.id==locationThree)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Actually do something useful */
        if (tagOfInterest == null || tagOfInterest.id == locationOne) {
            //location one code

            //Move backwards for 990 ticks with a delay of 0 seconds
            moveBackwards(1000, 0);

            //move Left into location one for 1900 ticks and stop for the remainder of autonomous
            moveLeft(1900, 30000);


        } else if (tagOfInterest.id == locationTwo){
            //location two code
            //pass through location one
            //moves left fpr 2400 ticks into location 2
            moveLeft(2400, 30000);

        } else if (tagOfInterest.id == locationThree){
            //location three code

            // move forward for 985 ticks with a delay of 0 seconds
            moveForward(1000, 0);

            // move left for 1900 ticks into location three and stop for the remainder of autonomous
            moveLeft(1900,30000);

        }

        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        //while (opModeIsActive()) {sleep(20);}
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
    //runToLocation method is called in the if statements to move the robot to whatever location
    public void runToLocation(int frontRight, int frontLeft, int backRight, int backLeft, double power, int ms){

        //resets the encoder value to zero for all motors
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets the motors to run to whatever position inputted into the method
        frontRightMotor.setTargetPosition(frontRight);
        frontLeftMotor.setTargetPosition(frontLeft);
        backRightMotor.setTargetPosition(backRight);
        backLeftMotor.setTargetPosition(backLeft);

        //sets the motors to whatever power is inputted into the method
        frontRightMotor.setPower(power);
        frontLeftMotor.setPower(power);
        backRightMotor.setPower(power);
        backLeftMotor.setPower(power);

        //sets the motors to run to the position it is told to
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontRightMotor.isBusy() || frontLeftMotor.isBusy() || backRightMotor.isBusy() || backLeftMotor.isBusy()) {
            //while the motors are busy, do nothing
        }

        //sets the motors to run based on time
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //sets the power of the motors to zero once everything is finished moving
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);

        //add a delay before whatever movement is next
        sleep(ms);
    }
    public void notEncoders(double fRPower, double fLPower, double bRPower, double bLPower, int ms){
        //sets the power of the motors to whatever is inputted into the method
        frontRightMotor.setPower(fRPower);
        frontLeftMotor.setPower(fLPower);
        backRightMotor.setPower(bRPower);
        backLeftMotor.setPower(bLPower);
        //sets the duration of said power
        sleep(ms);
    }
    //function that moves the robot right using runToLocation function
    public void moveRight(int position1, int time1){
        runToLocation(-position1, -position1, position1, position1, .6, time1);
    }
    //function that moves the robot left using runToLocation function
    public void moveLeft(int position2, int time2){
        runToLocation(position2, position2, -position2, -position2,.6, time2);
    }
    //function that moves the robot forward using runToLocation function
    public void moveForward(int position3, int time3){
        runToLocation(-position3, position3, position3, -position3, .6, time3);
    }
    //function that moves the robot backwards using runToLocation function
    public void moveBackwards(int position4, int time4){
        runToLocation(position4, -position4, -position4, position4, .6, time4);
    }
    // This function moves the viperSlide motor based on whatever is inputted into the method
    public void viperSlide (int slidePosition, double slidePower, int slideTime){

        //stops and resets the encoder to zero
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets the target position to whatever is inputted into the function
        viperSlideMotor.setTargetPosition(slidePosition);

        //sets the power to whatever is inputted into the function
        viperSlideMotor.setPower(slidePower);

        //sets the motor to run to position
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (viperSlideMotor.isBusy()){
            //while the motor is busy, do nothing
        }

        //set the power of the motor to zero after it is finished
        viperSlideMotor.setPower(0);

        //add a delay after if needed
        sleep(slideTime);
    }
    // This function moves the two servos to a certain position based on whatever is inputted into the function
    public void servoMove(int clawPartOnePos, int clawPartZeroPos, int delay){

        // Moves the two servos to whatever is input into the function
        clawPartOne.setPosition(clawPartOnePos);
        clawPartZero.setPosition(clawPartZeroPos);

        // Adds a delay after the servos move
        sleep(delay);
    }
    // This function allows me to move the viperSlide motor without setting the position to zero
    public void viperSlideNoZero (int slidePosition, double slidePower, int ms){

        //sets the target position to whatever is inputted into the function
        viperSlideMotor.setTargetPosition(slidePosition);

        //sets the power to whatever is inputted into the function
        viperSlideMotor.setPower(slidePower);

        //sets the motor to run to position
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (viperSlideMotor.isBusy()){
            //while the motor is busy, do nothing
        }

        //set the power of the motor to zero after it is finished
        viperSlideMotor.setPower(0);

        //add a delay after if needed
        sleep(ms);
    }
}