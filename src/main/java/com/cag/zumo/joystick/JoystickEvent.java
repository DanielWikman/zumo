package com.cag.zumo.joystick;

import lombok.Builder;
import net.java.games.input.Component;

@Builder
public record JoystickEvent(DriveSide driveSide, int value) {
}
