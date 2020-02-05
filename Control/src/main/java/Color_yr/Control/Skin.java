package Color_yr.Control;

import li.cock.ie.reflect.DuckBypass;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import net.md_5.bungee.connection.LoginResult.Property;

import java.lang.reflect.Constructor;

public class Skin {
    private static DuckBypass reflect;
    private static Class<?> LoginResult;

    static {
        try {
            LoginResult = getBungeeClass("connection", "LoginResult");
            reflect = new DuckBypass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SetSkin(ProxiedPlayer player, TestObj Skin) {

        InitialHandler handler = (InitialHandler) player.getPendingConnection();
        LoginResult profile = handler.getLoginProfile();
        Property[] temp = new Property[Skin.getTest().size()];
        for (int a = 0; a < Skin.getTest().size(); a++) {
            Properties temp1 = Skin.getTest().get(a);
            temp[a] = new Property(temp1.getName(), temp1.getValue(), temp1.getSignature());
        }

        profile.setProperties(temp);
        setObject(InitialHandler.class, handler, "loginProfile", profile);

    }

    private static Object invokeConstructor(Class<?> clazz, Class<?>[] args, Object... initargs) throws Exception {
        return getConstructor(clazz, args).newInstance(initargs);
    }
    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... args) throws Exception {
        Constructor<?> c = clazz.getConstructor(args);
        c.setAccessible(true);
        return c;
    }

    private static void setObject(Class<?> clazz, Object obj, String fname, Object value) {
        reflect.setValue(clazz, fname, obj, value);
    }

    private static Class<?> getBungeeClass(String path, String clazz) throws Exception {
        return Class.forName("net.md_5.bungee." + path + "." + clazz);
    }

}
