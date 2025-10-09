package org.firstinspires.ftc.teamcode.Tests.ColorSensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Color_Sensor extends OpMode {
    Color_Sensor_Mechanics bench = new Color_Sensor_Mechanics();


    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        Color_Sensor_Mechanics.DetectedColor detected = bench.getDetectedColor(telemetry);

        String BallColor;
        switch (detected) {
            case PURPLEBALL: BallColor = "Purple"; break;
            case GREENBALL: BallColor = "Green"; break;
            default: BallColor = "None";
        }

        telemetry.addData("Ball Color", BallColor);
        telemetry.update();
    }
}
