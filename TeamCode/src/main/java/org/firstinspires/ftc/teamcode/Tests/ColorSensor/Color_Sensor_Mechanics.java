package org.firstinspires.ftc.teamcode.Tests.ColorSensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Color_Sensor_Mechanics {
    NormalizedColorSensor colorSensor;


    public enum DetectedColor {
        PURPLEBALL,
        GREENBALL,
        RED, // for testing
        GREEN, //for testing
        BLUE, //for testing
        UNKNOWN
    }

    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "Color1");
        colorSensor.setGain(15);
    }


    public DetectedColor getDetectedColor(Telemetry telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors(); // returns 4 values red, blue, green, alpha (brightness/ light)

        float normRed, normGreen, normBlue;
        normRed = colors.red / colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("red", normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);


        /*
        RED =1, .76, .54
        GREEN = 0.47, 1, 0.74
        YELLOW = 1, 1, 1
        BLUE = .34, .75, 1
        GREENBALL =
        PURPLEBALL =
         */

        //TODO add if statements for specific colors added


         if (normRed > 1.5 && normGreen < .13 && normBlue < .3) {
            return DetectedColor.RED;
        } else if (normRed < .55 && normGreen > .85 && normBlue < .85) {
            return DetectedColor.GREEN;
        } else if (normRed < .45 && normGreen < .8 && normBlue > .85) {
             return DetectedColor.BLUE;
        } else if (normGreen > normBlue && normGreen > normRed && normRed < (normGreen / 2)) {
                 return DetectedColor.GREENBALL;
        } else if (normBlue > normGreen && normBlue > normRed) {
                 return DetectedColor.PURPLEBALL;
        } else return DetectedColor.UNKNOWN;
    }
}
