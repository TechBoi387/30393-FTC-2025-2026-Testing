package org.firstinspires.ftc.teamcode.Tests.Intake;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Intake extends OpMode {

    private DcMotor Lifter;

    @Override
    public void init() {
        Lifter = hardwareMap.get(DcMotor.class,"Lifter");

        Lifter.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            Lifter.setTargetPosition(Lifter.getCurrentPosition() + 90);
        }
    }
}
