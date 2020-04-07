package cn.mcres.karlatemp.mojangyggdrasil.Config;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Main;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.ConfigObj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.PlayerSaveObj;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MainConfig {

    private static final String config = "{\n" +
            "  \"Port\" : 25566\n" +
            "}";

    private static final String player_save = "{\n" +
            "  \"players\" : {},\n" +
            "  \"banID\": [],\n" +
            "  \"banUUID\": []\n" +
            "}";

    public static void loadconfig() {
        try {
            File file = new File(System.getProperty("user.dir") + "/setting.json");
            PlayerConfig.file = new File(System.getProperty("user.dir") + "/player_save.json");
            if (!file.exists()) {
                InputStream in = new ByteArrayInputStream(config.getBytes());
                Files.copy(in, file.toPath());
            }
            if (!PlayerConfig.file.exists()) {
                InputStream in = new ByteArrayInputStream(player_save.getBytes());
                Files.copy(in, PlayerConfig.file.toPath());
            }

            Main.Config = new Gson().fromJson(new FileReader(file), ConfigObj.class);
            PlayerConfig.playerUuid = new Gson().fromJson(new FileReader(PlayerConfig.file), PlayerSaveObj.class);

            if (Main.Config == null) {
                Main.Config = new ConfigObj(25566);
                InputStream in = new ByteArrayInputStream(new Gson().toJson(Main.Config).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, file.toPath());
            }

            if (PlayerConfig.playerUuid == null) {
                PlayerConfig.playerUuid = new PlayerSaveObj();
                InputStream in = new ByteArrayInputStream(new Gson().toJson(PlayerConfig.playerUuid).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, PlayerConfig.file.toPath());
            }

        } catch (Exception e) {
            Loggin.boot.warning("The config load fail");
            Main.Config = new ConfigObj(25566);
        }
    }
}
