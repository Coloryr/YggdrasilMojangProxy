package cn.mcres.karlatemp.mojangyggdrasil.Obj;

public class SocketObj {
    private String Do;
    private String ID;
    private String UUID;
    private String Message;
    public SocketObj(String Message) {
        this.Message = Message;
    }

    public String getDo() {
        return Do;
    }

    public String getID() {
        return ID;
    }

    public String getUUID() {
        return UUID;
    }

    public String getMessage() {
        return Message;
    }
}
