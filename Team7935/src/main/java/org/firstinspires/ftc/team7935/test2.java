/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.team7935;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 *
 * This OpMode executes a basic Tank Drive Teleop for a two wheeled robot using two REV SPARKminis.
 * To use this example, connect two REV SPARKminis into servo ports on the Expansion Hub. On the
 * robot configuration, use the drop down list under 'Servos' to select 'REV SPARKmini Controller'
 * and name them 'left_drive' and 'right_drive'.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Drive_Code", group="Concept")
//@Disabled
public class test2 extends LinearOpMode {


    // Servo variables
    static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int CYCLE_MS = 750;     // period of each cycle
    static final double MIN_POS = 0.4;     // Minimum rotational position
    private ElapsedTime servoTimer = new ElapsedTime();
    boolean First = true;


    // Drive variables
    double drivepower= .5;
    double turnpower= .5;
    double straifpower= .5;


    // Servo code
  //  Servo servo;


    private ElapsedTime runtime = new ElapsedTime();

    // Drive code
    private DcMotor backleft = null;
    private DcMotor frontleft = null;
    private DcMotor frontright = null;
    private DcMotor backright = null;

   private DcMotor lift = null;
   private DcMotor sweeper = null;


    CRServo leftservo;
    CRServo rightservo;
    static final double Max = 1;
    static final double Min = 0;

    static final double rightup = 1;
    static final double leftup = 0;
    static final double rightdown = 0;
    static final double leftdown = 1;





    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();



        rightservo = hardwareMap.get(CRServo.class,"right");
        leftservo = hardwareMap.get(CRServo.class,"left");
        lift = hardwareMap.get(DcMotor.class,"lift");
        sweeper = hardwareMap.get(DcMotor.class,"sweeper");

        backleft = hardwareMap.get(DcMotor.class, "backleft");
        frontleft = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backright = hardwareMap.get(DcMotor.class, "backright");



        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            double leftfrontpower;
            double leftbackpower;
            double rightfrontpower;
            double rightbackpower;


            double drive = gamepad1.left_stick_y * drivepower;
            double turn  =  -gamepad1.right_stick_x * turnpower;
            double straif  =  -gamepad1.left_stick_x * straifpower;
            leftfrontpower    = Range.clip(drive + turn + straif, -1.0, 1.0) ;
            leftbackpower   = Range.clip(drive + turn - straif, -1.0, 1.0) ;
            rightfrontpower   = Range.clip(drive - turn - straif, -1.0, 1.0) ;
            rightbackpower   = Range.clip(drive - turn + straif, -1.0, 1.0) ;



            frontleft.setPower(leftfrontpower);
            backleft.setPower(leftbackpower);
            frontright.setPower(-rightfrontpower);
            backright.setPower(-rightbackpower);





            if (gamepad2.dpad_right){      //up
                rightservo.setPower(.4);
                leftservo.setPower(-.4);
            }
            else if (gamepad2.dpad_left) {     //down
                rightservo.setPower(-.25);
                leftservo.setPower(.25);
            }
            else{
                rightservo.setPower(0);
                leftservo.setPower(0);
            }

            if(gamepad1.left_bumper){
                sweeper.setPower(1);
            }
            else if(gamepad1.right_bumper){
                sweeper.setPower(-1);
            }
            else {
                sweeper.setPower(0);
            }

            if(gamepad2.dpad_up){
                lift.setPower(0.5);
            }
            else if(gamepad2.dpad_down){
                lift.setPower(-0.5);
            }
            else{
                lift.setPower(0);
            }


           /* if (gamepad1.a & First == true){
                //position = MIN_POS;
                //servoTimer.reset();
            }

            if (servoTimer.milliseconds()> CYCLE_MS){
                //position = MAX_POS;

            }

             */


            /*
            if (gamepad1.a) {
                if (runtime.milliseconds()>CYCLE_MS){
                    if (rampUp) {
                        position = MIN_POS;
                        rampUp = !rampUp;   // Switch ramp direction
                    }
                    else {
                        position = MAX_POS;
                        rampUp = !rampUp;  // Switch ramp direction
                    }
                    runtime.reset();
                }

            }
            else {
                position = MAX_POS;
                rampUp = true;
                //First = true;
            }
            */


            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the servo to the new position and pause;
           // servo.setPosition(position);
            //sleep(CYCLE_MS);
            idle();
        }
    }
}

