//this code should work for all three locations.
//"PassOne" means that it passes through location one to get to location two
//use this when location three is the one that contains the cones in it
package org.firstinspires.ftc.teamcode.OpenCv;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


import java.util.ArrayList;

@Autonomous
public class AprilTagAutonomousRight extends LinearOpMode
{
    OpenCvCamera camera;
    DcMotor frontRightMotor;
    DcMotor frontLeftMotor;
    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor viperSlideMotor;
    Servo clawPartOne;
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
        //clawPartOne= hardwareMap.servo.get("clawPartOne");
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
            //move backward
            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(-.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(900);
            //set power to move left
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(1800);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(30000);
        } else if (tagOfInterest.id == locationTwo){
            //location two code
            //pass through location one
            //move forward
            frontRightMotor.setPower(.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(-.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(900);
            //set power to move left
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(2525);
            //move forward into location two
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(.5);
            sleep(900);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(30000);

        } else if (tagOfInterest.id == locationThree){
            //location three code
            //move forward
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(.5);
            backRightMotor.setPower(-.5);
            backLeftMotor.setPower(.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(900);
            //set power to move right
            frontRightMotor.setPower(-.5);
            frontLeftMotor.setPower(-.5);
            backRightMotor.setPower(.5);
            backLeftMotor.setPower(.5);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(1800);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(0);
            backRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            //sleep causes this to happen for 1 seconds (1000 milliseconds)
            sleep(30000);
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
        int frontRightTarget= frontRightMotor.getCurrentPosition();
        int frontLeftTarget = frontLeftMotor.getCurrentPosition();
        int backRightTarget = backRightMotor.getCurrentPosition();
        int backLeftTarget = backLeftMotor.getCurrentPosition();
        frontRightMotor.setPower(power);
        frontLeftMotor.setPower(power);
        backRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        //sets the motors to run to the position it is told to
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //sets the power of the motors for when it moves to the encoder position
        while (frontRightMotor.isBusy() || frontLeftMotor.isBusy() || backRightMotor.isBusy() || backLeftMotor.isBusy()) {
            //while the motors are busy, do nothing
        }
        // moved setPower above runtoposition
        // commented out the if
        // changed the while loop to OR instead of AND
        // moved setpower 0 outside of the if statement

        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        backLeftMotor.setPower(0);


        /**
        //once the motors are finished,set the power to zero
        if (frontRightTarget==frontRight && frontLeftTarget==frontLeft && backRightTarget==backRight && backLeftTarget==backLeft){

            //sleep for whatever number of milliseconds are inputted into the method
            sleep(ms);
        } **/
    }
}