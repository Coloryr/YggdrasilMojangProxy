package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.Config_Obj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj_save.Player_save_Obj;
import cn.mcres.karlatemp.mojangyggdrasil.bungeecord.BCSupport;
import cn.mcres.karlatemp.mojangyggdrasil.plugin.AuthMeStartup;
import com.google.gson.Gson;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class Main {
    public static final String mojangHasJoined = "https://sessionserver.mojang.com/session/minecraft/hasJoined";
    public static final List<UListener.StreamHandler> handlers = new ArrayList<>();

    public static URLStreamHandler http;
    public static URLStreamHandler https;

    public static Config_Obj Config;

    private static final String config = "{\n" +
            "  \"Port\" : 25566,\n" +
            "  \"offline\": false,\n" +
            "  \"authme\": false,\n" +
            "  \"localUUID\": true\n" +
            "}";

    private static final String player_save = "{\n" +
            "  \"players\" : {}\n" +
            "}";

    public static void premain(String opt, Instrumentation i) {
        bootstart(i, opt);
    }

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

    private static void loadconfig() {
        try {
            File file = new File(System.getProperty("user.dir") + "/config.json");
            GT.file = new File(System.getProperty("user.dir") + "/player_save.json");
            if (!file.exists()) {
                InputStream in = new ByteArrayInputStream(config.getBytes());
                Files.copy(in, file.toPath());
            }
            if(!GT.file.exists())
            {
                InputStream in = new ByteArrayInputStream(player_save.getBytes());
                Files.copy(in, GT.file.toPath());
            }

            Config = new Gson().fromJson(new FileReader(file), Config_Obj.class);
            GT.g = new Gson().fromJson(new FileReader(GT.file), Player_save_Obj.class);

            if (Config == null) {
                Config = new Config_Obj(25566);
                InputStream in = new ByteArrayInputStream(new Gson().toJson(Config).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, file.toPath());
            }

            if(GT.g == null)
            {
                GT.g = new Player_save_Obj();
                InputStream in = new ByteArrayInputStream(new Gson().toJson(GT.g).getBytes(StandardCharsets.UTF_8));
                Files.copy(in, GT.file.toPath());
            }

        } catch (Exception e) {
            Loggin.boot.warning("The config load fail");
            Config = new Config_Obj(25566);
        }
    }

    private static void bootstart(Instrumentation i, String opt) {
        Loggin.boot.info("Welcome to use MojangYggdrasil");
        Loggin.boot.info("Version: " + Main.class.getPackage().getImplementationVersion());
        Loggin.boot.info("Author: Karla" + "temp. QQ: 3279826484.");
        Loggin.boot.info("Loading config...");
        loadconfig();
        opt = www(opt);
        Loggin.conf.info("Yggdrasil ROOT: " + opt);
        inject(opt);
        BCSupport.inject(i, opt);
        Mojang.inject();
        if (Config.isOffline()) {
            Offline.build();
        }
        try {
            if (Config.isAuthme())
                AuthMeStartup.startup(i);
        } catch (NoClassDefFoundError nf) {
            // BungeeCord...
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
    }

    public static void agentmain(String opt, Instrumentation i) {
        bootstart(i, opt);
    }

    public static void main(String[] args) {
        Loggin.boot.info("请不要启动JAR");
    }
}
