package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.ConfigObj;

import java.lang.instrument.Instrumentation;


public class Main {
    public static ConfigObj Config;

    private static String www(String rua) {
        if (!rua.startsWith("http")) {
            rua = "https://" + rua;
        }
        if (!rua.endsWith("/")) {
            rua += "/";
        }
        return rua;
    }

    private static void bootstart(Instrumentation i) {
        Loggin.boot.info("混合登录初始化中");
        Loggin.boot.info("版本：1.2.0");
        MainConfig.loadconfig();
        Config.setAddress(www(Config.getAddress()));
        Loggin.boot.info("辅助登录地址：" + Config.getAddress());
        InitSupport.inject(i);
    }

    public static void agentmain(String opt, Instrumentation i) {
        bootstart(i);
    }

    public static void premain(String opt, Instrumentation i) {
        bootstart(i);
    }

    public static void main(String[] args) {
        Loggin.boot.info("请不要启动JAR");
    }
}
