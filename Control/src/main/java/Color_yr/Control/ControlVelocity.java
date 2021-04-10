package Color_yr.Control;

import Color_yr.Control.Side.Velocity.CommandVelocity;
import Color_yr.Control.Side.Velocity.SideVelocity;
import Color_yr.Control.Side.Velocity.VelocityLog;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "control", name = "Control", version = Control.Version,
        url = "https://github.com/HeartAge/YggdrasilMojangProxy", description = "混合登录配套插件", authors = {"Color_yr"})
public class ControlVelocity {
    public static ControlVelocity plugin;
    public final ProxyServer server;
    public final Path dataDirectory;
    public final Logger logger;

    @Inject
    public ControlVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        plugin = this;
        logger.info("§d[Control]§e正在启动，感谢使用，本插件交流群：571239090");
        Control.log = new VelocityLog();
        Control.side = new SideVelocity();
        new Control().init(dataDirectory.toFile());
        Control.loadconfig();
        CommandMeta meta = server.getCommandManager().metaBuilder("my")
                .aliases("control")
                .build();

        server.getCommandManager().register(meta, new CommandVelocity());
        logger.info("§d[Control]§e已启动-" + Control.Version);
    }

    @Subscribe
    public void onStop(ProxyShutdownEvent event) {
        logger.info("§d[Control]§e已停止，感谢使用");
    }
}
