package cn.mcres.karlatemp.mojangyggdrasil.Obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSaveObj {
    private Map<String, String> players = new HashMap<>();
    private List<String> banID = new ArrayList<>();
    private List<String> banUUID = new ArrayList<>();
    private Map<String, SkinOBJ> skin = new HashMap<>();

    public void setSkin(String player, SkinOBJ skin1) {
        skin.put(player, skin1);
    }

    public SkinOBJ getSkin(String player) {
        return skin.get(player);
    }

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

    public void RemovePlayer(String id) {
        players.remove(id);
    }

    public void AddBanID(String id) {
        banID.add(id);
    }

    public void AddBanUUID(String uuid) {
        banUUID.add(uuid);
    }
}
