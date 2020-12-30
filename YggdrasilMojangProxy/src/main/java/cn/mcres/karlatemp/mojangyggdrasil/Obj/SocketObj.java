package cn.mcres.karlatemp.mojangyggdrasil.Obj;

public class SocketObj {
    public enum Fun
    {
        ReSkin,Reload,AddBanID,SetPlayer,AddPlayer,RemoveBanID,RemoveBanUUID,RemovePlayer
    }

    private Fun Do;
    private String ID;
    private String UUID;
    public SocketObj(Fun Do, String ID, String UUID) {
        this.Do = Do;
        this.ID = ID;
        this.UUID = UUID;
    }

    public Fun getDo() {
        return Do;
    }

    public String getID() {
        return ID;
    }

    public String getUUID() {
        return UUID;
    }
}
