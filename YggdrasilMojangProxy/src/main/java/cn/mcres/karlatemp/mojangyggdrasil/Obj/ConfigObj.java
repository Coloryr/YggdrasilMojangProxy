package cn.mcres.karlatemp.mojangyggdrasil.Obj;

public class ConfigObj {
    private int Port;
    private int Priority;
    private String Address;
    private String Message;
    private String Message1;
    private String Message2;

    public ConfigObj(int Port) {
        this.Port = Port;
    }

    public int getPriority() {
        return Priority;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPort() {
        return Port;
    }

    public String getMessage() {
        return Message;
    }

    public String getMessage1() {
        return Message1;
    }

    public String getMessage2() {
        return Message2;
    }
}
