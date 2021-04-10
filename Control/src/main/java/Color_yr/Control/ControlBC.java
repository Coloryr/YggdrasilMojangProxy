package Color_yr.Control;

import Color_yr.Control.Side.BC.CommandBC;
import Color_yr.Control.Side.BC.SideBC;
import Color_yr.Control.Side.BC.BCLog;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public class ControlBC extends Plugin {

    public static Logger log = ProxyServer.getInstance().getLogger();

    @Override
    public void onEnable() {
        Control.log = new BCLog();
        Control.side = new SideBC();
        log.info("§d[Control]§e正在启动，感谢使用，本插件交流群：571239090");
        new Control().init(getDataFolder());
        Control.loadconfig();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandBC());
        log.info("§d[Control]§e已启动-" + Control.Version);
    }

    @Override
    public void onDisable() {
        log.info("§d[Control]§e已停止，感谢使用");
    }
}
