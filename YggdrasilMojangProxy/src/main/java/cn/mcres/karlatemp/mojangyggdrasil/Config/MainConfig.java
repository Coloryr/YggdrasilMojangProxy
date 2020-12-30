package cn.mcres.karlatemp.mojangyggdrasil.Config;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Main;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.ConfigObj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.PlayerSaveObj;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MainConfig {

    private static final String config = "{\n" +
            "  \"Port\" : 25566,\n" +
            "  \"Address\": \"https://auth2.nide8.com:233/xxxxxxxxxxx\",\n" +
            "  \"Priority\": 0,\n" +
            "  \"Message\": \"你还没有使用过正版登录\",\n" +
            "  \"Message1\": \"连接到登录服务器失败\",\n" +
            "  \"Message2\": \"你已被服务器禁止进入\"\n" +
            "}";

    private static final String player_save = "{\n" +
            "  \"players\" : {},\n" +
            "  \"banID\": [],\n" +
            "  \"banUUID\": [],\n" +
            "  \"skin\": {}\n" +
            "}";

    public static void loadconfig() {
        try {
            Loggin.boot.info("加载配置中");
            File file = new File(System.getProperty("user.dir") + "/setting.json");
            PlayerConfig.file = new File(System.getProperty("user.dir") + "/player_save.json");
            if (!file.exists()) {
                InputStream in = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
                Files.copy(in, file.toPath());
            }
            if (!PlayerConfig.file.exists()) {
                InputStream in = new ByteArrayInputStream(player_save.getBytes(StandardCharsets.UTF_8));
                Files.copy(in, PlayerConfig.file.toPath());
            }

            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8);
            Main.Config = new Gson().fromJson(reader, ConfigObj.class);
            reader.close();

            reader = new InputStreamReader(
                    new FileInputStream(PlayerConfig.file), StandardCharsets.UTF_8);
            PlayerConfig.playerUuid = new Gson().fromJson(reader, PlayerSaveObj.class);
            reader.close();

            if (Main.Config == null) {
                Main.Config = new ConfigObj(25566);
                InputStream in = new ByteArrayInputStream(new Gson().toJson(Main.Config).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, file.toPath());
                Loggin.boot.info("请进行初始化设置再启动服务器");
                System.exit(0);
            }

            if (Main.Config.getAddress() == null || Main.Config.getAddress().isEmpty()) {
                Loggin.boot.info("请进行初始化设置再启动服务器");
                System.exit(0);
            }

            if (PlayerConfig.playerUuid == null) {
                Loggin.boot.info("玩家报错不存在，新建中");
                PlayerConfig.playerUuid = new PlayerSaveObj();
                InputStream in = new ByteArrayInputStream(new Gson().toJson(PlayerConfig.playerUuid).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, PlayerConfig.file.toPath());
            }

        } catch (Exception e) {
            Loggin.boot.warning("配置文件加载失败，请检查");
            System.exit(0);
        }
    }
}
