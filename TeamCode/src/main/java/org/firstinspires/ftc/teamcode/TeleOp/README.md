# TeleOp Basics

This file will discuss the next steps to follow for
programming the driver operated part of the game.
There are not many steps left to do for TeleOp once you
finish the setup.

## The Loop

Some code only has to be executed once or twice and then never needs to be executed again.
However, for TeleOp, the program needs to constantly check for things like
joystick movements or button presses, so all of the code past the setup is usually within a loop.

> An example of what the loop looks like:

    public void loop() {
      // code that is looped goes in here
    }

With this in mind, it will make it easier to understand the rest of TeleOp.

## Motor Movements

### Joystick Data

For us to be able to move the motors on the robot, we
have to be able to take data from the joysticks (or back triggers)
and then have the motors actually do something with this data.
With this in mind, the first step we took in our approach of coding the motors was
to create four different variables:
one for forward movements by the robot, one for strafing, one for turning,
and one to power our viper slide.

> These variables can be found in lines 44-47 of `RobotTwoTeleOp.java`:

    //Different movements on the joysticks are configured to different variables
    //decimals are used to reduce power
    double forward = -.4 * (gamepad1.left_stick_y);
    double turn = -.55 * (-gamepad1.right_stick_x);
    double strafe = -.55 * (-gamepad1.left_stick_x);
    double viperSlide = .35*(gamepad2.right_stick_y);

As you can see, we have set our forward variable to the left stick's y-axis, which makes up and down on the
joystick translate to forward and back with the robot.
We did the same for strafing, but on the x axis instead, and the same for turning, but on the x axis of the right stick.


The decimal determines how much power you want for your motors.
Since our motors seem to go way too fast at normal power, we lowered the power to 2/5 of the original power for better handling.
Also, some of the variables are stored with negative power, which just reverses the
direction of the motors so that steering isn't inverted.

Now that we have data from the joysticks, we need to take that data and do something with it.

### Applying The Direction Variables To The Motors

There are many different ways you could approach this,
but the way my team did it was that we created four separate variables that took all the data from the `forward`, `strafe`, and `turn` variables and applied them to
the correct motors.

> These variables can be found at lines 49-53 of `RobotTwoTeleOp.java`:

    //tells the motors that drive the robot which way to go for forward and back, strafing, and turning
    double leftfront = (-forward - turn - strafe);
    double rightfront = (forward - turn - strafe);
    double leftrear = (-forward - turn + strafe);
    double rightrear = (forward - turn + strafe);

Once again we have negatives in front of some values.
This is so that the motors move the right direction for each joystick movement.

The final step for making sure the motors move is to set the power of the motors to the value of these variables.

> This can be found at lines 55-59 of `RobotTwoTeleOp.java`:

    //sets the power of the motors to the previous variables
    frontLeftMotor.setPower(leftfront);
    frontRightMotor.setPower(rightfront);
    backLeftMotor.setPower(leftrear);
    backRightMotor.setPower(rightrear);

Once this is done, you should have working motors based on whatever joystick movement you assigned to the variables.

## Booleans and Servos

To be able to move servos, we first have to understand what booleans are.
Booleans are a type of data that either returns true or false.
Besides the joysticks and the back triggers, which return as a floating point number (basically a decimal value), all other buttons on the controller
return as a boolean. If they are being pressed, then they return as true, otherwise, they return as false.

Servos have a few differences from motors. Typically, servos can only go 180 degrees, and that 180 degrees is reduced to a scale of 0-1
(for example, setting a servo's position to .5 would be 90 degrees away from 0 and 1).
We use the buttons to activate servos because of the button's boolean data type.

> This can be found at lines 65-75 of `RobotTwoTeleOp.java`:

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

For our robot, we used two simple if statements that happen if the specified button is pressed.
The buttons also work with other types of conditional statements,
but for our TeleOp we didn't need them.

## Next Steps

Thank you for looking into our TeleOp code! We hope this helps,
and we'd greatly appreciate it if you check out our documentation on our other programs.