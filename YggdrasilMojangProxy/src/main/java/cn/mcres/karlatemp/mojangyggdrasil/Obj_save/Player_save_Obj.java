package cn.mcres.karlatemp.mojangyggdrasil.Obj_save;

import java.util.HashMap;
import java.util.Map;

public class Player_save_Obj {
    private Map<String, String> players = new HashMap<>();

    public Map<String, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, String> players) {
        this.players = players;
    }
}
