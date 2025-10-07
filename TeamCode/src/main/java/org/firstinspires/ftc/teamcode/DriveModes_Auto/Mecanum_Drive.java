package org.firstinspires.ftc.teamcode.DriveModes_Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Mecanum Drive", group="Linear OpMode")
public class Mecanum_Drive extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor FrontLeftDrive = null;
    private DcMotor FrontRightDrive = null;
    private DcMotor BackLeftDrive = null;
    private DcMotor BackRightDrive = null;
    private DcMotor arm = null;
    private DcMotor wrist = null;
    private Servo claw = null;
    private CRServo intake = null;


    // Arm and Wrist target positions for each state
    private static final int ARM_POSITION_INIT = 0;
    private static final int ARM_POSITION_INTAKE = 150;

    private static final int WRIST_POSITION_INIT = 0;
    private static final int WRIST_POSITION_SAMPLE = 270;

    // Claw positions
    private static final double CLAW_OPEN_POSITION = 0.2;
    private static final double CLAW_CLOSED_POSITION = 0.5;

    // Enum for state machine
    private enum RobotState {
        INIT,
        INTAKE,
        GRAB_SAMPLE,
        REMOVE_SAMPLE,
        MANUAL
    }

    // Initial state
    private RobotState currentState = RobotState.INIT;

    // Claw toggle state
    private boolean clawOpen = true;

    //target position
    private int targetArm = 0;
    private int targetWrist = 0;

    @Override
    public void runOpMode() {
        // Initialize the hardware variables.
        FrontLeftDrive  = hardwareMap.get(DcMotor.class, "FL");
        FrontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        BackLeftDrive = hardwareMap.get(DcMotor.class,"BL");
        BackRightDrive = hardwareMap.get(DcMotor.class,"BR");
        arm = hardwareMap.get(DcMotor.class, "arm");
        wrist = hardwareMap.get(DcMotor.class, "wrist");
        claw = hardwareMap.get(Servo.class, "claw");
        intake = hardwareMap.get(CRServo.class, "intake");

        // Stop and reset encoders
        FrontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to use encoders
        FrontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set motor direction
        FrontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        FrontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        BackLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        //Set zero power behavior
        wrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BackRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // State machine logic
            switch (currentState) {
                case INIT:
                    targetArm = ARM_POSITION_INIT;
                    targetWrist = WRIST_POSITION_INIT;
                    telemetry.addData("State", "INIT");
                    break;
                case INTAKE:
                    targetArm = ARM_POSITION_INTAKE;
                    targetWrist = WRIST_POSITION_SAMPLE;
                    telemetry.addData("State", "INTAKE");
                    break;
                case GRAB_SAMPLE:
                    telemetry.addData("State", "GRAB_SAMPLE");
                    break;
                case REMOVE_SAMPLE:
                    telemetry.addData("State", "REMOVE_SAMPLE");
                    break;
                case MANUAL:
                    telemetry.addData("State", "MANUAL");
                    break;
            }


            // Handle state transitions based on gamepad input
            if (gamepad1.dpad_up){ //manual control
                currentState = RobotState.MANUAL;
                targetArm -= 10;
            } else if (gamepad1.dpad_down){
                currentState = RobotState.MANUAL;
                targetArm += 10;
            } else if (gamepad1.dpad_left){
                currentState = RobotState.MANUAL;
                targetWrist += 1;
            } else if (gamepad1.dpad_right){
                currentState = RobotState.MANUAL;
                targetWrist -= 1;
            }
            if (gamepad1.a){
                currentState = RobotState.GRAB_SAMPLE;
                claw.setPosition(CLAW_CLOSED_POSITION);
            } else if (gamepad1.b){
                currentState = RobotState.REMOVE_SAMPLE;
                claw.setPosition(CLAW_OPEN_POSITION);
            }

            if (gamepad1.right_trigger>0.1) {
                intake.setPower(1.0);
            } else if (gamepad1.left_trigger>0.1) {
                intake.setPower(-1.0);
            }

            double Vertical = gamepad1.left_stick_y;
            double Horizontal = -gamepad1.left_stick_x;
            double Rotation = -gamepad1.right_stick_x;

            //makes drive motors move
            FrontLeftDrive.setPower(Rotation + Vertical + -Horizontal);
            FrontRightDrive.setPower(-Rotation + Vertical + Horizontal);
            BackLeftDrive.setPower(Rotation + Vertical + Horizontal);
            BackRightDrive.setPower(-Rotation + Vertical + -Horizontal);

            arm.setTargetPosition(targetArm);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            wrist.setTargetPosition(targetWrist);
            wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(1);
            wrist.setPower(1);

            // Send telemetry data to the driver station
            telemetry.addData("Vertical", Vertical);
            telemetry.addData("Horizontal",Horizontal);
            telemetry.addData("Rotation", Rotation);
            telemetry.addData("Claw Position", clawOpen ? "Open" : "Closed");
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.addData("Arm Power", arm.getPower());
            telemetry.addData("Wrist Position", wrist.getCurrentPosition());
            telemetry.addData("Wrist Power", wrist.getPower());
            telemetry.update();
        }
    }
}

