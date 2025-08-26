package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Auto: Drive 18in by Encoders", group="Robot")
public class Auto extends LinearOpMode {

    private DcMotor FrontLeftDrive, FrontRightDrive, BackLeftDrive, BackRightDrive;
    public DcMotor arm = null;
    private DcMotor wrist = null;
    private Servo claw = null;
    private CRServo intake = null;
    float count = 0;

    // === TUNE THESE FOR YOUR ROBOT ===
    // Common option shown; replace with your motor/encoder spec if different.
    static final double TICKS_PER_REV = 537.7;       // e.g., goBILDA 5202/5203 312rpm
    static final double EXTERNAL_GEAR_REDUCTION = 1.0; // >1.0 if wheels turn slower than motor
    static final double WHEEL_DIAMETER_IN = 3.5;     // your wheel diameter in inches
    static final double COUNTS_PER_INCH = (TICKS_PER_REV * EXTERNAL_GEAR_REDUCTION) / (WHEEL_DIAMETER_IN * Math.PI);

    static final double DRIVE_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        // Map hardware
        FrontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        BackLeftDrive = hardwareMap.get(DcMotor.class, "BL");
        BackRightDrive = hardwareMap.get(DcMotor.class, "BR");

        arm = hardwareMap.get(DcMotor.class, "arm");
        wrist = hardwareMap.get(DcMotor.class, "wrist");
        claw = hardwareMap.get(Servo.class, "claw");
        intake = hardwareMap.get(CRServo.class, "intake");

        FrontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        BackLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        BackRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Use encoders
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Robot is ready for Start","");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;
        Drive(1,0,0,1);
        sleep(1000);


    }

    /**
     * Drives straight for the given distance (inches) using RUN_TO_POSITION.
     * Positive distance drives forward, negative drives backward.
     */

    public void Drive(double Vertical, double Horizontal, double Rotation, double Speed){
        FrontLeftDrive.setPower((Rotation + Vertical + -Horizontal) * Speed);
        FrontRightDrive.setPower((-Rotation + Vertical + Horizontal) * Speed);
        BackLeftDrive.setPower((Rotation + Vertical + Horizontal) * Speed);
        BackRightDrive.setPower((-Rotation + Vertical + -Horizontal) * Speed);
    }
}