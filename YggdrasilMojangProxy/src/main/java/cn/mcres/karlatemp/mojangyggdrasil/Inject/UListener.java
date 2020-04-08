package cn.mcres.karlatemp.mojangyggdrasil.Inject;

import cn.mcres.karlatemp.mojangyggdrasil.Log.Loggin;
import cn.mcres.karlatemp.mojangyggdrasil.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.Optional;

public class UListener extends URLStreamHandler {
    private static final Method a, b;
    public static String root;

    static {
        Method c = null, d = null;
        try {
            c = URLStreamHandler.class.getDeclaredMethod("openConnection", URL.class);
            d = URLStreamHandler.class.getDeclaredMethod("openConnection", URL.class, Proxy.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        a = c;
        b = d;
        AccessibleObject.setAccessible(new AccessibleObject[]{c, d}, true);
    }

    private final int defport;
    private final URLStreamHandler parent;

    public UListener(int defport, URLStreamHandler parent) {
        this.defport = defport;
        this.parent = parent;
    }

    private static URLConnection open(URLStreamHandler handler, URL u, Proxy p) throws IOException {
        try {
            if (p == null) {
                return (URLConnection) a.invoke(handler, u);
            } else {
                return (URLConnection) b.invoke(handler, u, p);
            }
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            Throwable thr = e.getTargetException();
            if (thr instanceof IOException) throw (IOException) thr;
            throw new IOException(thr);
        }
    }

    @Override
    protected int getDefaultPort() {
        return defport;
    }

    @Override
    protected URLConnection openConnection(URL u, Proxy p) throws IOException {
        Store<URLConnection> connect = new Store<>();
        Do(u, p, connect, parent);
        if (connect.value != null) return connect.value;
        return open(parent, u, p);
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        Store<URLConnection> connect = new Store<>();
        Do(u, null, connect, parent);
        if (connect.value != null) return connect.value;
        return open(parent, u, null);
    }

    public static class Store<T> {
        T value;
    }

    private static byte[] readAll(InputStream is) throws IOException {
        byte[] array = new byte[is.available()];
        is.read(array);
        return array;
    }

    private boolean Conn(URL url, Proxy proxy, Store<URLConnection> store) throws IOException {
        URLConnection connect;
        if (proxy == null) connect = url.openConnection();
        else connect = url.openConnection(proxy);
        connect.setConnectTimeout(6500);
        connect.setReadTimeout(6500);
        HttpURLConnection huc = (HttpURLConnection) connect;
        int re = huc.getResponseCode();
        String message = huc.getResponseMessage();
        Loggin.boot.info("服务器返回：" + re);
        Loggin.boot.info("服务器返回：" + message);
        if (re == 200) {
            final byte[] got = readAll(huc.getInputStream());
            store.value = new BuffedHttpConnection(url, got);
            huc.disconnect();
            return true;
        }
        huc.disconnect();
        return false;
    }

    private void Do(URL url, Proxy proxy, Store<URLConnection> store, URLStreamHandler http) {
        final String ef = url.toExternalForm();
        if (ef.startsWith(root)) {
            String opt = ef.substring(root.length());
            if (opt.startsWith("sessionserver/session/minecraft/hasJoined")) {
                int respone = 0;
                try {
                    Loggin.boot.info("尝试设置的地址登录");
                    //从设置的服务器验证
                    URL x = new URL(null, url.toExternalForm(), http);
                    if (Conn(x, proxy, store))
                        return;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                try {
                    Loggin.boot.info("尝试正版登录");
                    //从正版服务器验证
                    URL mojang = new URL(null, Main.mojangHasJoined + "?" + url.getQuery(), Main.https);
                    if (Conn(mojang, proxy, store))
                        return;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                if (store.value == null) {
                    store.value = new BuffedHttpConnection(url, new byte[0], respone);
                }
            }
        }
    }
}
