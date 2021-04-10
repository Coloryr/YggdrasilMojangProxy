package Color_yr.Control.Side.BC;

import Color_yr.Control.Side.ISide;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class SideBC implements ISide {
    @Override
    public void sendMessage(Object sender, String data) {
        CommandSender sender1 = (CommandSender) sender;
        sender1.sendMessage(new TextComponent(data));
    }
}
