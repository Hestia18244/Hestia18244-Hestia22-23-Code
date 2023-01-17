# Basic Autonomous

This document will go over how a basic autonomous works.

## Linear OpModes

For teleOp, the program runs sequentially until it hits the
loop, which then constantly checks for things like joystick
movements during the whole time that the opMode is active.

Autonomous modes are different. Usually, they run using some sort of linear opMode, which
is an opMode that runs straight through in a sequential order.
This is important to understand how our basic autonomous modes work.

## Running The OpMode And Waiting For Start

> An example of the keyword to run the opMode from lines
17-18 of `HestiaAutonomousMoveLeft.java`:

    public void runOpMode(){
        waitForStart();

All of the main code that makes the robot move comes after these two things. Anything below `public void runOpMode()` will happen once the 'initialize' button
has been pressed on the driver's hub.
`waitForStart();` is a function that acts as a sort of block.
It stops anything below it from happening until the user presses play on the driver's hub.

Hardware mappings in these basic autonomous modes occur
after the `waitForStart();` function is called, but they can also occur before that function as well.

## Moving The Motors

The first part of moving the motors is to set the power the motors are going to run at.
To do this, you write the name of the motor follow by `.setPower()`, and
in the parentheses, you write the power on a scale of 0-1 ( .5 power would be 50% power).

> An example of this from lines 26-29 of `HestiaAutonomousMoveLeft.java`:

    //sets power to all the motors so that it moves left
    frontRightMotor.setPower(-.5);
    frontLeftMotor.setPower(-.5);
    backRightMotor.setPower(.5);
    backLeftMotor.setPower(.5);

The negatives are used to change the direction the motors turn to.

The next part of moving the motors is to determine how long the motors will run for.
With the example above, we have a program that runs the motors at .5 power and then
just doesn't stop. To change this, we use the `sleep()` method.

> An example of this from lines 26-31 of `HestiaAutonomousMoveLeft.java`:

    frontRightMotor.setPower(-.5);
    frontLeftMotor.setPower(-.5);
    backRightMotor.setPower(.5);
    backLeftMotor.setPower(.5);
    //sleep causes this to happen for 1.1 seconds (1100 milliseconds)
    sleep(1100);

The sleep method accepts a value in milliseconds. This tells the motors to run for a 1100 milliseconds.

The rest of autonomous is basically just repeating this process. In the autonomous modes that use the OpenCv plugin, I use functions so that I don't have to constantly write out code.

## Next Steps

Thank you for taking a look at our basic autonomous programs! We hope this helps,
and we'd greatly appreciate it if you check out our documentation on our other programs.