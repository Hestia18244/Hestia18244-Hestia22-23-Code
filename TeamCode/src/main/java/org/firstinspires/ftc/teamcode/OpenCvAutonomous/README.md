# Autonomous Using The AprilTag OpenCv Plugin

This document will go over autonomous modes that use
the OpenCv plugin and the AprilTag detection plugin. I won't go over how
to install these two plugins, but the links for these two plugins will be at the end of this file.

## AprilTags

AprilTags are essentially simplified qr codes that things like cameras can detect. With the AprilTag detection plugin and the premade `AprilTagDetectionPipeline.java` file and the
template that takes advantage of this pipeline, we can have the cameras detect the codes and then have the robot do
things based off of which code is detected.

![AprilTag #11](aprilTag11.png)

> An example of an AprilTag (This one is AprilTag #11)

In the actual programs that use AprilTags, I store the AprilTags used
for the three locations in three different `int` variables.

> The variables that store the AprilTags that correspond to locations:

    //declares a variable for each number (based on the april tag number)
    int locationOne = 11;
    int locationTwo = 12;
    int locationThree = 13;

On their own, they don't do anything, but these variables will make it
easier to know which location a certain block of code is for.

## Telemetry Data

From about lines 88-150, the code there is mostly from the templates made by the people who made the plugin.
It's mostly just code that displays things in the telemetry (driver's hub), and it makes sure that a tag is actually found.

![](OnBotDoc_RunOpModeDCMotor.jpg)

> The black space where it says "Target Power" is where the telemetry data can be found.

While the opMode is actually running, and an AprilTag is found,
it will send data to the telemetry on what tag is found, how far away it is, and other data similar to those.
This is all because of the code around lines 88-150

## If Statements Based On What Tag is Found

Starting at line 166, you will see an if-elseif-elseif statement, with many function calls within them.
These function calls will change soon as I transition the autonomous mode to using encoders.

> An example of how the if statements may look

    if (tagOfInterest == null || tagOfInterest.id == locationOne) {
        //function calls that do stuff
    } else if (tagOfInterest.id == locationTwo){
        //function calls that do stuff
    } else if (tagOfInterest.id == locationThree){
        //function calls that do stuff
    }

The first if statement checks for whether no tag was found, or the id assigned to `locationOne` is found.
Then it moves the motors accordingly to the correct location (I didn't include that here but I'll describe the functions
more in depth further down).

The second if statement checks if the tag found is the one assigned to `locationTwo`, and then moves the motors accordingly. The third
if statement does the same, but for `locationThree`.

## Functions

From lines 230 to the end, I have multiple functions that allow me to write less code when making the motors/servos move.

### Encoder Functions

Encoders are an internal clock built into motors that allow a robot to move with precision
(like a servo). Running an autonomous on encoders is more precise than
running it on time.

At the moment, we aren't using this function to move our robot (we're using a time function),
but I plan to change to this encoder function to make our robot's movements more consistent.

> The start of the function from line 230 of `AprilTagAutonomousLeft.java`:

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

The function is named `runToLocation` and accepts parameters that are used to set the position
of the motors. There are other parameters, but I'll explain them later.

`frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);` means that you are setting the initial
value of the encoders to zero. Everytime I call this function, the encoders ***current position*** is set to zero.
This makes it easier to figure out what position the motors need to run to everytime I call this function.

`frontRightMotor.setTargetPosition(frontRight);` sets the target position of the motor's encoders
to whatever is put into the parameter of frontRight. This doesn't make it move yet, but it makes sure that once the motor moves, it moves to that position.

> This is the next part of the function:

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

The fifth parameter of the function is called `power`, and this parameter plugs the `.setPower()` function to set the power of the motors to whatever is put into the function.

`frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);` sets the run mode of the motor to run to the position we set earlier.
This is what makes the motors move, and when
the motors move they move to the position set earlier at the power set right
before it. They ***don't*** run based on time, but on the internal motor clocks.

> This is the next and final part of the function:

    while (frontRightMotor.isBusy() || frontLeftMotor.isBusy() || backRightMotor.isBusy() || backLeftMotor.isBusy()) {
        //while the motors are busy, do nothing
    }

    //sets the power of the motors to zero once everything is finished moving
    frontRightMotor.setPower(0);
    frontLeftMotor.setPower(0);
    backRightMotor.setPower(0);
    backLeftMotor.setPower(0);

    //add a delay before whatever movement is next
    sleep(ms);

The `while` loop acts as a block. It essentially says that while the motors are busy, do nothing other than keep these motors busy.
It prevents the motors from moving onto the next bit of code, which sets the power of the motors to zero.

`frontRightMotor.setPower(0);` sets the power of the motors to zero once they finish moving to the desired encoder tick.
`sleep(ms);` takes advantage of the final parameter, `ms`, which adds a delay before moving onto the next block of code.
If I didn't want a delay, I would put zero.

There is another function called `viperSlide`, which is the function that moves
our slide using encoders. It is basically the same as the function above, but for just the `viperSlide` motor.

> There are also functions that take advantage of the `runToLocation` function:

    //function that moves the robot right using runToLocation function
    public void moveRight(int position1, int time1){
        runToLocation(-position1, -position1, position1, position1, .5, time1);
    }

This function makes it so that instead of figuring out where the negatives have to be placed everytime,
I just have separate functions for each direction (the negatives are used to reverse the direction).

### Servo Function

There is a servo function that is used to move the servos. It accepts three parameters and it is a very basic function.

    // This function moves the two servos to a certain position based on whatever is inputted into the function
    public void servoMove(int clawPartOnePos, int clawPartZeroPos, int delay){

        // Moves the two servos to whatever is input into the function
        clawPartOne.setPosition(clawPartOnePos);
        clawPartZero.setPosition(clawPartZeroPos);

        // Adds a delay after the servos move
        sleep(delay);
    }

Similarly to the encoder function,
this accepts parameters for the servos' position.
Immediately after the movement of the servos, there
is a delay much like the encoder function's delay.

### Time Function

Currently, we are using a very basic time function to move our robot to the parking locations.

    public void notEncoders(double fRPower, double fLPower, double bRPower, double bLPower, int ms){
        //sets the power of the motors to whatever is inputted into the method
        frontRightMotor.setPower(fRPower);
        frontLeftMotor.setPower(fLPower);
        backRightMotor.setPower(bRPower);
        backLeftMotor.setPower(bLPower);
        //sets the duration of said power
        sleep(ms);
    }

This function accepts a few parameters and plugs them into a block of code that is very similar to our basic autonomous modes.
It takes the parameters of power for each motor and then the duration for which the motors run it (sleep in this case
is not used for a delay since this function runs on time).

In simple terms, this function moves the motors for a certain duration based on the parameters.

## Additional Links

* [EasyOpenCv Plugin for FTC](https://github.com/OpenFTC/EasyOpenCV)
* [AprilTag Plugin for FTC](https://github.com/OpenFTC/EOCV-AprilTag-Plugin)

## Thank You

Thank you for taking a look at our openCv autonomous programs! We hope this helps, and we'd greatly appreciate it if you check out our documentation on our other programs.
