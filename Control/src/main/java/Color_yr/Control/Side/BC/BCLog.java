package Color_yr.Control.Side.BC;

import Color_yr.Control.ControlBC;
import Color_yr.Control.Side.ILog;

public class BCLog implements ILog {
    @Override
    public void info(String data) {
        ControlBC.log.info(data);
    }

    @Override
    public void warning(String data) {
        ControlBC.log.warning(data);
    }
}
