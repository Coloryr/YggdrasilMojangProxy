package cn.mcres.karlatemp.mojangyggdrasil.Obj_save;

import java.util.List;

public class Login_Obj {

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