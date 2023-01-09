# Basic Code Applicable To All OpModes

Despite the fact that autonomous and TeleOp are very different, both modes
share a few similar aspects. These similarities will be listed here.

## Importing Modules

To actually be able to program things like motors and servos, you first
need to import the necessary modules that allow you to create motors within the program. An example of this would be lines 2-4 of `RobotTwoTeleOp.java`.

    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.hardware.DcMotor;

There are three import statements within this chunk of code. The first
imports the TeleOp module, which allows me to declare the opMode as TeleOp, and the second and third modules are necessary so that I can actually declare motors and servos later on and then make them do things.

Without these imported modules, I would not be able to do things like
program the motors. I would instead get a ton of errors.

There are many other modules besides these ones, all with a specific
purpose, and importing modules is usually the first thing you'll see at the start of a program.

## Declaring Motors, Servos, And Other Hardware

The next important step is to declare the motors, servos, or any other
hardware. This allows you to create these objects within the code, so that you can actually do something with them.

> An example of this from `AprilTagAutonomousRight` (lines 22-28):

    OpenCvCamera camera;
    DcMotor frontRightMotor;
    DcMotor frontLeftMotor;
    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor viperSlideMotor;
    Servo clawPartOne;

`OpenCvCamera` declares that whatever name is assigned to it is a camera
used for OpenCv. `camera` is the name of the object. The rest of the code is similar, but it is dedicated for motors and servos.

With the hardware declared, we now have objects we can actually work with.
The next and final step will be to map the hardware to control hub.

## Mapping Hardware

To make sure that the physical control hub actually knows what we mean by
`camera` or `frontRightMotor`, we have to give them the name that the driver's hub and control hub will recognize them by.

> Lines 26-27 of `RobotTwoTeleOp.java`:

    viperSlideMotor = hardwareMap.dcMotor.get("viperSlideMotor");
    clawPartOne = hardwareMap.servo.get("clawPartOne");

This chunk of code takes the objects we created earlier and maps them to
what we name them on the driver's hub/control hub. This step is important because without it, there might not be any errors in the code, but when you try and actually test it, the robot won't know what `viperSlideMotor` is and the program won't work.

## Next steps

These are all of the main similarities between the different OpModes. Anything after this will vary based on the OpMode and the actual function of the program, so be sure to check out the readme files within each OpMode's directory for more specific details. 