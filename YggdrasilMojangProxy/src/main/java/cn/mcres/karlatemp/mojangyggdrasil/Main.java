package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Inject.NetWork;
import cn.mcres.karlatemp.mojangyggdrasil.Inject.UListener;
import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.ConfigObj;
import cn.mcres.karlatemp.mojangyggdrasil.bungeecord.BCSupport;

import java.lang.instrument.Instrumentation;
import java.net.URLStreamHandler;
import java.util.Map;


public class Main {
    public static final String mojangHasJoined = "https://sessionserver.mojang.com/session/minecraft/hasJoined";

    public static URLStreamHandler http;
    public static URLStreamHandler https;

    public static ConfigObj Config;

    private static void inject(final String rootx) {
        UListener.root = www(rootx);
        Map<String, URLStreamHandler> handlers = NetWork.getHandlers();
        handlers.put("http", new UListener(80, NetWork.getURLStreamHandler("http")));
        handlers.put("https", new UListener(443, NetWork.getURLStreamHandler("https")));
    }

    private static String www(String rua) {
        if (!rua.startsWith("http")) {
            rua = "https://" + rua;
        }
        if (!rua.endsWith("/")) {
            rua += "/";
        }
        return rua;
    }

    private static void bootstart(Instrumentation i, String opt) {
        Loggin.boot.info("Welcome to use MojangYggdrasil");
        Loggin.boot.info("Version: " + Main.class.getPackage().getImplementationVersion());
        Loggin.boot.info("Loading config...");
        MainConfig.loadconfig();
        opt = www(opt);
        Loggin.conf.info("Yggdrasil ROOT: " + opt);
        inject(opt);
        BCSupport.inject(i);
    }

    public static void agentmain(String opt, Instrumentation i) {
        bootstart(i, opt);
    }

    public static void premain(String opt, Instrumentation i) {
        bootstart(i, opt);
    }

    public static void main(String[] args) {
        Loggin.boot.info("请不要启动JAR");
    }
}
