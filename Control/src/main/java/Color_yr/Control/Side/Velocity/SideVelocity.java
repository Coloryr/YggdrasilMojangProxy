package Color_yr.Control.Side.Velocity;

import Color_yr.Control.Side.ISide;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;

public class SideVelocity implements ISide {
    @Override
    public void sendMessage(Object sender, String data) {
        CommandSource sender1 = (CommandSource) sender;
        sender1.sendMessage(Component.text(data));
    }
}
