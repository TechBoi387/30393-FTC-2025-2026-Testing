package org.firstinspires.ftc.teamcode.Tests.DriveModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Mecanum extends OpMode {

    double MotorSpeed = 1.0;

    //Tells Java that each of the motors are DcMotors while assigning variables to them
    private DcMotor FrontLeftMotor;
    private DcMotor FrontRightMotor;
    private DcMotor BackLeftMotor;
    private DcMotor BackRightMotor;

    @Override
    //Runs the code when the "initialize" button is pressed on the Driver Station
    public void init(){

        //Telling Java Were motor/servos are connected to on the control hub / expansion hub
        FrontLeftMotor = hardwareMap.get(DcMotor.class, "FL");
        FrontRightMotor = hardwareMap.get(DcMotor.class, "FR");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "BL");
        BackRightMotor = hardwareMap.get(DcMotor.class, "BR");

        //Setting Motor Direction
        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        BackRightMotor.setDirection(DcMotor.Direction.REVERSE);
    };

    @Override
    //Runs the code when the "Run" button is pressed on the Driver Station
    public void loop(){

        //setting Variables to Joystick Positions.
        double Vertical = gamepad1.left_stick_y;
        double Horizontal = -gamepad1.left_stick_x;
        double Rotation = -gamepad1.right_stick_x;
        double CurrentTime = getRuntime(); // makes a timer

        //setting speed for the drive motors (speed = 1 else speed = 0.7)
        if(gamepad1.left_bumper){MotorSpeed=1.0;}else{MotorSpeed=0.7;};

        //makes drive motors move
        FrontLeftMotor.setPower((Rotation + Vertical + Horizontal)*MotorSpeed);
        FrontRightMotor.setPower((-Rotation + Vertical + -Horizontal)*MotorSpeed);
        BackLeftMotor.setPower((Rotation + Vertical + -Horizontal)*MotorSpeed);
        BackRightMotor.setPower((-Rotation + Vertical + Horizontal)*MotorSpeed);


        //adds the information onto the driver station
        double StartTime = CurrentTime;
        telemetry.addLine("Drive Train");
        telemetry.addData("Vertical LS-Y", -Vertical);
        telemetry.addData("Horizontal LS-X", -Horizontal);
        telemetry.addData("Rotation RS-X", Rotation);
        telemetry.addData("Motor Speed", MotorSpeed);
        telemetry.addLine("");
        telemetry.addLine("Runtime");
        telemetry.addData("Run Time(Rounded)", Math.round(CurrentTime));
        telemetry.addData("Run Time", CurrentTime);
        telemetry.addData("Time Between Telemetry loop", StartTime - CurrentTime);
        telemetry.update();
    };
};