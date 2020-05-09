package Color_yr.Control;

import java.util.List;

public class ConfigObj {
    public ConfigObj(int Port) {
        this.Port = Port;
    }
    public List<String> Admin;

    public List<String> getAdmin() {
        return Admin;
    }

    private int Port;

    public int getPort() {
        return Port;
    }

}