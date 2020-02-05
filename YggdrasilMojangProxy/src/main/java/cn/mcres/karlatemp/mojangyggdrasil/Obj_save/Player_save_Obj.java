package cn.mcres.karlatemp.mojangyggdrasil.Obj_save;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player_save_Obj {
    private Map<String, String> players = new HashMap<>();
    private List<String> banID = new ArrayList<>();
    private List<String> banUUID = new ArrayList<>();

    public Map<String, String> getPlayers() {
        return players;
    }

    public List<String> getBanID() {
        return banID;
    }

    public List<String> getBanUUID() {
        return banUUID;
    }

    public void AddPlayers(String id, String uuid) {
        players.put(id, uuid);
    }

    public void AddBanID(String id) {
        banID.add(id);
    }

    public void AddBanUUID(String uuid) {
        banUUID.add(uuid);
    }
}
