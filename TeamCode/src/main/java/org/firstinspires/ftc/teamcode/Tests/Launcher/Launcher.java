package org.firstinspires.ftc.teamcode.Tests.Launcher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Tests.Servo.ServoTest;

@TeleOp
public class Launcher extends OpMode {
    ColorSensor_Mechanics bench = new ColorSensor_Mechanics();
    ElapsedTime timer = new ElapsedTime();
    public CRServo LifterThingy;
    public CRServo RejectThingy;
    public DcMotor FlingyThingy;
    Double MotorSpeed;

    @Override
    public void init() {
        bench.init(hardwareMap);
        LifterThingy = hardwareMap.get(CRServo.class, "LiftServo");
        RejectThingy = hardwareMap.get(CRServo.class, "RejectServo");
        FlingyThingy = hardwareMap.get(DcMotor.class, "LauncherMotor");
    }

    @Override
    public void loop() {

        ColorSensor_Mechanics.DetectedColor detected = bench.getDetectedColor(telemetry);

        String BallColor;
        switch (detected) {
            case PURPLEBALL: BallColor = "Purple"; break;
            case GREENBALL: BallColor = "Green"; break;
            case RED: BallColor = "Red"; break;
            case GREEN: BallColor = "Green2"; break;
            case BLUE: BallColor = "Blue"; break;
            default: BallColor = "Unknown";
        }

        if (BallColor.equals("Purple")){
            LifterThingy.setPower(1);
        } else if (BallColor.equals("Green")) {
            RejectThingy.setPower(-1);
        }

        if(gamepad1.left_bumper){MotorSpeed=1.0;}else{MotorSpeed=0.7;};

        FlingyThingy.setPower(-MotorSpeed);

        telemetry.addData("Ball Color", BallColor);
        telemetry.addData("MotorSpeed", MotorSpeed);
        telemetry.update();
        if (gamepad1.a) {
            LifterThingy.setPower(0);
            RejectThingy.setPower(0);
        }
    }
};
