/*
Copyright 2023 FIRST Tech Challenge Team FTC

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * This file contains an minimal example of a Linear "OpMode". n OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * -
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 * -
 * Remove a @Disabled the on the next line or two (if present) to add this OpMode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp

public class MechDrive extends LinearOpMode { //class never used, we need the rest of a program to 'use' it, explain where everything else goes please
//all of these could be local, why are they private
    private DcMotor RightFront;
    private DcMotor RightBack;
    private DcMotor LeftFront;
    private DcMotor LeftBack;
    private DcMotor motorLarm;
    private DcMotor motorRarm;
    private Blinker control_Hub; //what is blinker
    private IMU imu; //what is imu
    private Servo sR1; //what is this
    private Servo sR2; //what is this
    private Servo sR3; //what is this

    @Override
    public void runOpMode() {
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");
        RightBack = hardwareMap.get(DcMotor.class, "RightBack");
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftBack = hardwareMap.get(DcMotor.class, "LeftBack");
        motorLarm = hardwareMap.get(DcMotor.class, "motorLarm");
        motorRarm = hardwareMap.get(DcMotor.class, "motorRarm");
        motorLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sR1 = hardwareMap.get(Servo.class, "sR1");
        sR2 = hardwareMap.get(Servo.class, "sR2");
        sR3 = hardwareMap.get(Servo.class, "sR3");


        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        imu = hardwareMap.get(IMU.class, "imu");

        RightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        double forward;
        double strafe;
        double turn;
        double tgtPowerRarm=0.0;
        double tgtPowerLarm=0.0;



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            forward= -gamepad1.left_stick_y;
            strafe= gamepad1.left_stick_x*1.1; //why 1.1?
            turn= gamepad1.right_stick_x;


            double denominator= Math.max(Math.abs(forward)+Math.abs(strafe)+Math.abs(turn),1); //explain please, why is there a 1?
            RightFront.setPower((forward-strafe-turn)/denominator);
            RightBack.setPower((forward+strafe-turn)/denominator);
            LeftFront.setPower((forward+strafe+turn)/denominator);
            LeftBack.setPower((forward-strafe+turn)/denominator);

            if(gamepad1.y){
                sR1.setPosition(0.25); //which arm is this?
            }
            else if (gamepad1.b){
                sR1.setPosition(1.5); //which arm is this?
            }
            else if (gamepad1.x){
                sR2.setPosition(0.7); //which arm is this?
            }
            else if (gamepad1.a){
                sR2.setPosition(1); //which arm is this?
            }


            else if(gamepad1.left_bumper){
                motorLarm.setTargetPosition(490);
                motorRarm.setTargetPosition(-490);
                motorLarm.setPower(1.0);
                motorRarm.setPower(1.0);
                motorLarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorRarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else if (gamepad1.right_bumper){
                motorLarm.setTargetPosition(0);
                motorRarm.setTargetPosition(-0);
                motorLarm.setPower(1.0);
                motorRarm.setPower(1.0);
                motorLarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorRarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sleep(2000);
                motorLarm.setPower(0.0);
                motorRarm.setPower(0.0);

            }
            else if(gamepad1.left_stick_button){
                motorLarm.setPower(0);
                motorRarm.setPower(0);
                motorLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }


            else if (gamepad1.back){ //what is the back button on crontroller?

                sR3.setPosition(0.4);
                sleep(1500); //why this much time?
                sR3.setPosition(1);

            }
                //resetting the entire robot at start?
            else if (gamepad1.start){

                motorLarm.setTargetPosition(0);
                motorRarm.setTargetPosition(-0);
                motorLarm.setPower(1.0);
                motorRarm.setPower(1.0);
                motorLarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorRarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //sleep(10000);
                // motorLarm.setPower(0.0);
                // motorRarm.setPower(0.0);

                //what are these and why are they commented out?

            }

            //   motorRarm.setPower(((float)gamepad1.left_trigger));
            //  motorLarm.setPower((-(float)gamepad1.left_trigger));

            //what are these and why are they commented  out?

            //what is telemetry things? just wondering, seems cool not necessary!
            telemetry.addData("Status", "Running");
            telemetry.addData("Arm Position",  ((Integer)motorRarm.getCurrentPosition()).toString() );
            telemetry.update();

        }
    }
}
