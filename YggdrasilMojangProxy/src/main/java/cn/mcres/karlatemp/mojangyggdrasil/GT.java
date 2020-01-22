package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.Player_save_Obj;
import com.google.gson.Gson;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class GT {
    public static Player_save_Obj g = new Player_save_Obj();
    public static File file;

    public static String getUUID(String id, String uuid) {
        if (g.getPlayers().containsKey(id)) {
            return g.getPlayers().get(id);
        } else if (cn.mcres.karlatemp.mojangyggdrasil.Main.Config.isOffline()) {
            uuid = UUID.randomUUID().toString();
            Save(id, uuid);
        } else {
            Save(id, uuid);
        }
        return null;
    }

    public static void Save(String id, String uuid) {
        Map<String, String> save = g.getPlayers();
        save.put(id, uuid);
        g.setPlayers(save);
        try {
            String data = new Gson().toJson(g);
            if (file.exists()) {
                Writer out =new FileWriter(file);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
