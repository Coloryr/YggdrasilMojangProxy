package Color_yr.Control.Side.Velocity;

import Color_yr.Control.ControlVelocity;
import Color_yr.Control.Side.ILog;

public class VelocityLog implements ILog {
    @Override
    public void info(String data) {
        ControlVelocity.plugin.logger.info(data);
    }

    @Override
    public void warning(String data) {
        ControlVelocity.plugin.logger.warn(data);
    }
}
