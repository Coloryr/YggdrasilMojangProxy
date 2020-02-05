package Color_yr.Control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigObj {
    public ConfigObj(int Port) {
        this.Port = Port;
        this.Skin = new HashMap<>();
    }

    private int Port;
    private Map<String, TestObj> Skin;

    public int getPort() {
        return Port;
    }

    public Map<String, TestObj> getSkin() {
        return Skin;
    }

    public void AddSkin(String UUID, TestObj Skin) {
        this.Skin.put(UUID, Skin);
    }
}