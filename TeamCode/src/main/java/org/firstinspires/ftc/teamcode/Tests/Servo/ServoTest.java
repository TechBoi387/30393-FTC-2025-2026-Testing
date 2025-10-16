package org.firstinspires.ftc.teamcode.Tests.Servo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends OpMode {
    public Servo servo;
    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class,"servo");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            servo.setPosition(0.3);
        } else {
            servo.setPosition(0);
        }
    }
}
