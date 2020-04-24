package cn.mcres.karlatemp.mojangyggdrasil.Obj;

public class ConfigObj {
    private int Port;
    private int Priority;
    private String Address;

    public int getPriority() {
        return Priority;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ConfigObj(int Port) {
        this.Port = Port;
    }

    public int getPort() {
        return Port;
    }

}
