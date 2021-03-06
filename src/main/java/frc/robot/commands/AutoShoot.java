// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import org.photonvision.targeting.PhotonTrackedTarget;

public class AutoShoot extends CommandBase {
  /** Creates a new AutoShoot. */
  public Shooter shooter;
  public Swerve swerve;
  public LEDSubsystem led;

  private PhotonCamera camera = new PhotonCamera("photonvision");

  double yaw;
  double pitch;
  double skew;
  Transform2d pose;

  //TODO ! DISABLE SWERVEDRIVE COMMAND 
  public AutoShoot(LEDSubsystem led,Shooter shooter, Swerve swerve) {
    this.led = led;
    this.shooter = shooter;
    this.swerve = swerve;

    camera.setDriverMode(false);
    
    addRequirements(led,shooter,swerve);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    led.turnOn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  //TODO goTo 
  @Override
  public void execute() {
    var result = camera.getLatestResult();
    if(result.hasTargets()){
      PhotonTrackedTarget target = result.getBestTarget();

      yaw = target.getYaw();
      pitch = target.getPitch();
      skew = target.getSkew();
      pose = target.getCameraToTarget();

      SmartDashboard.putNumber("Yaw", yaw);
      SmartDashboard.putNumber("Pitch", pitch);

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
