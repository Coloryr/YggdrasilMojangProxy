package Color_yr.Control.Side.BC;

import Color_yr.Control.CommandEX;
import Color_yr.Control.Control;
import cn.mcres.karlatemp.mojangyggdrasil.Log.Read;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SocketObj;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandBC extends Command {

    public CommandBC() {
        super("my");
    }

    public void execute(CommandSender sender, String[] args) {
        CommandEX.EX(sender, args, sender.getName());
    }
}
