package cn.mcres.karlatemp.mojangyggdrasil;

import cn.mcres.karlatemp.mojangyggdrasil.Config.PlayerConfig;
import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.LoginObj;
import cn.mcres.karlatemp.mojangyggdrasil.Obj.SkinOBJ;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.logging.Level;

public class InitSupport implements ClassFileTransformer, HttpHandler {

    private static final String root = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=";
    private static final String root1 = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s%s";
    private static final String root2 = "https://sessionserver.mojang.com/session/minecraft/hasJoined";
    private int port;
    private boolean booted = false;

    public static int indexOf(byte[] from, byte[] search, int off) {
        if (from.length < search.length) {
            return -1;
        }
        final int sleng = search.length;
        final int end = from.length - search.length;
        dox:
        for (int i = off; i < end; i++) {
            for (int k = 0; k < sleng; k++) {
                if (from[i + k] != search[k]) {
                    continue dox;
                }
            }
            return i;
        }
        return -1;
    }

    public static byte[] replace(byte[] codes, String contain, String rep) {
        byte[] utf8 = toContant(contain);
        int ind = indexOf(codes, utf8, 0);
        if (ind != -1) {
            int ed = utf8.length;
            ByteArrayOutputStream buff = new ByteArrayOutputStream(codes.length);
            buff.write(codes, 0, ind);
            byte[] repx = toContant(rep);
            buff.write(repx, 0, repx.length);
            buff.write(codes, ind + ed, codes.length - ind - ed);
            return buff.toByteArray();
        }
        return codes;
    }

    public static byte[] toContant(String contain) {
        byte[] got = contain.getBytes(StandardCharsets.UTF_8);
        byte[] nw = new byte[got.length + 2];
        int leng = got.length;
        nw[0] = (byte) ((leng >>> Byte.SIZE) & 0xFF);
        nw[1] = (byte) (leng & 0xFF);
        System.arraycopy(got, 0, nw, 2, leng);
        return nw;
    }

    public static void inject(Instrumentation i) {
        InitSupport init = new InitSupport();
        init.port = Main.Config.getPort();
        //System.setProperty("mojang.sessionserver", "http://127.0.0.1:" + init.port);
        init.startup();
        new Thread(() -> {
            // Wait Authlib-injector
            i.addTransformer(init);
        }).start();
    }

    private static byte[] readAll(InputStream is) throws IOException {
        byte[] array = new byte[is.available()];
        is.read(array);
        return array;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (className != null) {
            if (className.equals("net/md_5/bungee/connection/InitialHandler")) {
                Loggin.boot.info("修改BC源码中...");
                byte[] replaced = replace(classfileBuffer, root, "http://127.0.0.1:" + port + "/?username=");
                if (Arrays.equals(replaced, classfileBuffer)) {
                    replaced = replace(classfileBuffer, root1, "http://127.0.0.1:" + port + "/?username=%s&serverId=%s%s");
                    if (Arrays.equals(replaced, classfileBuffer)) {
                        Loggin.boot.severe("错误，不能修改源码");
                        System.exit(0);
                    }
                }
                return replaced;
            }
            else if (className.equals("com/velocitypowered/proxy/connection/client/LoginSessionHandler")) {
                Loggin.boot.info("修改BC源码中...");
                byte[] replaced = replace(classfileBuffer, root, "http://127.0.0.1:" + port + "/?username=");
                if (Arrays.equals(replaced, classfileBuffer)) {
                    replaced = replace(classfileBuffer, root1, "http://127.0.0.1:" + port + "/?username=%s&serverId=%s%s");
                    if (Arrays.equals(replaced, classfileBuffer)) {
                        replaced = replace(classfileBuffer, root2, "http://127.0.0.1:" + port);
                        if (Arrays.equals(replaced, classfileBuffer)) {
                            Loggin.boot.severe("错误，不能修改源码");
                            System.exit(0);
                        }
                    }
                }
                return replaced;
            }
        }
        return classfileBuffer;
    }

    private void startup() {
        if (booted) return;
        booted = true;
        try {
            HttpServer server = HttpServer.create();
            if (port == 0) {
                ServerSocket ss = new ServerSocket(port);
                port = ss.getLocalPort();
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server.bind(new InetSocketAddress(port), 0);
            server.createContext("/", this);
            Loggin.boot.info("本地登录服务器开启与端口" + port);
            server.start();
        } catch (IOException ioe) {
            Loggin.boot.log(Level.SEVERE, ioe.getMessage(), ioe);
        }
    }

    private String Conn(URL url) throws IOException {
        URLConnection connect;
        connect = url.openConnection();
        connect.setConnectTimeout(6500);
        connect.setReadTimeout(6500);
        HttpURLConnection huc = (HttpURLConnection) connect;
        int re = huc.getResponseCode();
        String message = huc.getResponseMessage();
        Loggin.boot.info("服务器返回：" + re);
        Loggin.boot.info("服务器返回：" + message);
        if (re == 200) {
            final byte[] got = readAll(huc.getInputStream());
            huc.disconnect();
            return new String(got);
        }
        huc.disconnect();
        return null;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        final URI uri = httpExchange.getRequestURI();
        boolean done = false;
        int state = 0;
        String temp = "";
        try {
            Loggin.boot.info("尝试设置的地址登录");
            //从设置的服务器验证
            URL x = new URL(null, Main.Config.getAddress() + "sessionserver/session/minecraft/hasJoined?" + uri.getQuery());
            temp = Conn(x);
            if (temp != null) {
                done = true;
                state = 1;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (!done)
            try {
                Loggin.boot.info("尝试正版登录");
                //从正版服务器验证
                URL mojang = new URL(null, "https://sessionserver.mojang.com/session/minecraft/hasJoined?" + uri.getQuery());
                temp = Conn(mojang);
                if (temp != null) {
                    done = true;
                    state = 2;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        try {
            OutputStream out = httpExchange.getResponseBody();
            if (!done) {
                LoginObj obj = new LoginObj();
                obj.setId(null);
                obj.setName(Main.Config.getMessage1());
                temp = new Gson().toJson(obj);
                httpExchange.sendResponseHeaders(200, 0);
                out.write(temp.getBytes(StandardCharsets.UTF_8));
                return;
            }
            LoginObj obj = new Gson().fromJson(temp, LoginObj.class);
            if (PlayerConfig.isBan(obj.getName(), obj.getId())) {
                obj.setId(null);
                obj.setName(Main.Config.getMessage2());
                temp = new Gson().toJson(obj);
                httpExchange.sendResponseHeaders(200, 0);
                out.write(temp.getBytes(StandardCharsets.UTF_8));
            }
            switch (Main.Config.getPriority()) {
                case 1:
                    if (state == 2 && !PlayerConfig.haveName(obj.getName())) {
                        Loggin.boot.info("玩家：" + obj.getName() + "没用使用设置的地址登录过");
                        obj.setId(null);
                        obj.setName(Main.Config.getMessage());
                        temp = new Gson().toJson(obj);
                        httpExchange.sendResponseHeaders(200, 0);
                        out.write(temp.getBytes(StandardCharsets.UTF_8));
                        return;
                    }
                    break;
                case 2:
                    if (state == 1 && !PlayerConfig.haveName(obj.getName())) {
                        Loggin.boot.info("玩家：" + obj.getName() + "没用使用正版登录过");
                        obj.setId(null);
                        obj.setName(Main.Config.getMessage());
                        temp = new Gson().toJson(obj);
                        httpExchange.sendResponseHeaders(200, 0);
                        out.write(temp.getBytes(StandardCharsets.UTF_8));
                        return;
                    }
                    break;
            }
            String uuid = PlayerConfig.getUUID(obj.getName(), obj.getId());
            SkinOBJ skin = PlayerConfig.getSKin(obj.getName());
            if (skin == null) {
                skin = new SkinOBJ();
                skin.setSkin(obj.getProperties());
                skin.setPox(state);
                PlayerConfig.SetSkin(obj.getName(), skin);
            } else if (skin.getPox() == state) {
                skin.setSkin(obj.getProperties());
                PlayerConfig.SetSkin(obj.getName(), skin);
            } else {
                obj.setProperties(skin.getSkin());
            }
            obj.setId(uuid);
            temp = new Gson().toJson(obj);
            httpExchange.sendResponseHeaders(200, 0);
            out.write(temp.getBytes(StandardCharsets.UTF_8));
            Loggin.boot.info("玩家：" + obj.getName() + " UUID:" + obj.getId());
        } finally {
            httpExchange.close();
        }
    }
}
