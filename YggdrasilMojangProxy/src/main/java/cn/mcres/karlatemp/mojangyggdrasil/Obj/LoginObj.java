package cn.mcres.karlatemp.mojangyggdrasil.Obj;

import java.util.List;

public class LoginObj {

    private String id;
    private String name;
    private List<Properties> properties;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public List<Properties> getProperties() {
        return properties;
    }
}