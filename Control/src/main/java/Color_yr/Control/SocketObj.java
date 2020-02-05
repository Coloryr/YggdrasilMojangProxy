package Color_yr.Control;

public class SocketObj {
    private String Do;
    private String ID;
    private String UUID;
    private String Message;
    public SocketObj(String Do, String ID, String UUID) {
        this.Do = Do;
        this.ID = ID;
        this.UUID = UUID;
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
