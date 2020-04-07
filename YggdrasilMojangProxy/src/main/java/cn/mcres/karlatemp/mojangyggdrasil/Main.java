package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Config.MainConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Inject.NetWork;
import cn.mcres.karlatemp.mojangyggdrasil.Inject.Sign;
import cn.mcres.karlatemp.mojangyggdrasil.Inject.UListener;
import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.ConfigObj;
import cn.mcres.karlatemp.mojangyggdrasil.bungeecord.BCSupport;

import java.lang.instrument.Instrumentation;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    public static final String mojangHasJoined = "https://sessionserver.mojang.com/session/minecraft/hasJoined";
    public static final List<UListener.StreamHandler> handlers = new ArrayList<>();

    public static URLStreamHandler http;
    public static URLStreamHandler https;

    public static ConfigObj Config;

    private static void inject(final String rootx) {
        final String root = www(rootx);
        final URLStreamHandler http = NetWork.getURLStreamHandler("http");
        final URLStreamHandler https = NetWork.getURLStreamHandler("https");
        Main.http = http;
        Main.https = https;
        UListener.StreamHandler sh = (listener, url, proxy, store) -> {
            final String ef = url.toExternalForm();
            if (ef.startsWith(root)) {
                String opt = ef.substring(root.length());
                if (opt.startsWith("sessionserver/session/minecraft/hasJoined")) {
                    for (UListener.StreamHandler handler : handlers) {
                        handler.run(listener, url, proxy, store);
                    }
                }
            }
        };
        Map<String, URLStreamHandler> handlers = NetWork.getHandlers();
        handlers.put("http", new UListener(80, http, sh));
        handlers.put("https", new UListener(443, https, sh));
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
        Sign.inject();
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
