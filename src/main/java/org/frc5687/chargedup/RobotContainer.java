/* Team 5687 (C)2021 */
package org.frc5687.chargedup;

import org.frc5687.chargedup.commands.Drive;
import org.frc5687.chargedup.commands.OutliersCommand;
import org.frc5687.chargedup.subsystems.DriveTrain;
import org.frc5687.chargedup.subsystems.OutliersSubsystem;
import org.frc5687.chargedup.util.OutliersContainer;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class RobotContainer extends OutliersContainer {

    private OI _oi;
    private Pigeon2 _imu;

    private Robot _robot;
    private DriveTrain _driveTrain;

    public RobotContainer(Robot robot, IdentityMode identityMode) {
        super(identityMode);
        _robot = robot;
    }

    public void init() {
        _oi = new OI();
        _imu = new Pigeon2(RobotMap.CAN.PIGEON.PIGEON, RobotMap.CAN.PIGEON.PIGEON_BUS_NAME);
        _driveTrain = new DriveTrain(this, _oi, _imu);

        setDefaultCommand(_driveTrain, new Drive(_driveTrain, _oi));
        _robot.addPeriodic(this::controllerPeriodic, 0.005, 0.005);
        _imu.setYaw(0); // Need to afirm works
    }

    public void periodic() {}

    public void disabledPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void teleopInit() {}

    @Override
    public void autonomousInit() {}

    private void setDefaultCommand(OutliersSubsystem subSystem, OutliersCommand command) {
        if (subSystem == null || command == null) {
            return;
        }
        CommandScheduler s = CommandScheduler.getInstance();
        s.setDefaultCommand(subSystem, command);
    }

    @Override
    public void updateDashboard() {
        _driveTrain.updateDashboard();
    }

    public void controllerPeriodic() {
        if (_driveTrain != null) {
            _driveTrain.controllerPeriodic();
        }
    }
}
