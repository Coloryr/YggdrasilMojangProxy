package cn.mcres.karlatemp.mojangyggdrasil.Config;

import cn.mcres.karlatemp.mojangyggdrasil.Obj.PlayerSaveObj;
import com.google.gson.Gson;

import java.io.*;
import java.util.UUID;

public class PlayerConfig {
    public static PlayerSaveObj playerUuid = new PlayerSaveObj();
    public static File file;

    public static String getUUID(String id, String uuid) {
        if (playerUuid.getPlayers().containsKey(id)) {
            return playerUuid.getPlayers().get(id);
        } else if (playerUuid.getPlayers().containsValue(uuid)) {
            UUID uuid1 = UUID.randomUUID();
            AddPlayer(id, uuid1.toString());
            return uuid1.toString();
        } else {
            AddPlayer(id, uuid);
            return uuid;
        }
    }

    public static void AddPlayer(String id, String uuid) {
        playerUuid.AddPlayers(id, uuid);
        save();
    }

    public static void AddBanID(String id) {
        playerUuid.AddBanID(id);
        save();
    }

    public static void AddBanUUID(String uuid) {
        playerUuid.AddBanUUID(uuid);
        save();
    }

    private static void save() {
        try {
            String data = new Gson().toJson(playerUuid);
            if (file.exists()) {
                Writer out = new FileWriter(file);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBan(String id, String uuid) {
        return playerUuid.getBanID().contains(id) || playerUuid.getBanUUID().contains(uuid);
    }
}
