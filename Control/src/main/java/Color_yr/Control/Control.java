package Color_yr.Control;

import com.google.gson.Gson;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;


public class Control extends Plugin {

    private static final String Version = "1.0.0";

    public static int Port = 123;

    public static ConfigObj config;
    public static File FileName;


    public static Logger log = ProxyServer.getInstance().getLogger();

    private void loadconfig() {
        Port = config.getPort();
    }

    private static void save() {
        try {
            String data = new Gson().toJson(config);
            if (FileName.exists()) {
                Writer out = new FileWriter(FileName);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setConfig() {
        try {
            if (FileName == null) {
                FileName = new File(getDataFolder(), "config.json");
                if (!getDataFolder().exists())
                    getDataFolder().mkdir();
                if (!FileName.exists()) {
                    InputStream in = getResourceAsStream("config.json");
                    Files.copy(in, FileName.toPath());
                }
            }

            config = new Gson().fromJson(new FileReader(FileName), ConfigObj.class);
            if (config == null) {
                config = new ConfigObj(123);
                save();
            } else {
                loadconfig();
            }
        } catch (IOException e) {
            log.warning("§d[Control]§c配置文件错误：" + e);
        }
    }

    @Override
    public void onEnable() {
        log.info("§d[Control]§e正在启动，感谢使用，本插件交流群：571239090");
        setConfig();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new command());
        log.info("§d[Control]§e已启动-" + Version);
    }

    @Override
    public void onDisable() {
        log.info("§d[Control]§e已停止，感谢使用");
    }
}
