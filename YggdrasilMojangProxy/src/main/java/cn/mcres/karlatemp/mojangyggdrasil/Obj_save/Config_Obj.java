package cn.mcres.karlatemp.mojangyggdrasil.Obj_save;

public class Config_Obj {
    private int Port;
    private boolean offline;
    private boolean authme;
    private boolean localUUID;

    public Config_Obj(int Port) {
        this.Port = Port;
        offline = false;
        authme = false;
        localUUID = true;
    }

    public boolean isAuthme() {
        return authme;
    }

    public boolean isOffline() {
        return offline;
    }

    public int getPort() {
        return Port;
    }

    public boolean isLocalUUID() {
        return localUUID;
    }
}
