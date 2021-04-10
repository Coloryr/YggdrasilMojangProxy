package Color_yr.Control;

import Color_yr.Control.Side.ISide;
import Color_yr.Control.Side.ILog;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;

public class Control {
    public  static final String Version = "1.2.0";
    public static ConfigObj config;
    private static File FileName;
    public static ILog log;
    public static ISide side;

    public static void loadconfig() {
        try {
            config = new Gson().fromJson(new FileReader(FileName), ConfigObj.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (config == null) {
            config = new ConfigObj();
            save();
        }
    }

    public static void save() {
        try {
            String data = new Gson().toJson(config);
            if (FileName.exists()) {
                Writer out = new FileWriter(FileName);
                out.write(data);
                out.close();
            }
            loadconfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(File local) {
        try {
            if (FileName == null) {
                FileName = new File(local, "config.json");
                if (!local.exists())
                    local.mkdir();
                if (!FileName.exists()) {
                    InputStream in = this.getClass().getResourceAsStream("/config.json");
                    Files.copy(in, FileName.toPath());
                }
            }
        } catch (IOException e) {
            log.warning("§d[Control]§c配置文件错误：");
            e.printStackTrace();
        }
    }
}
