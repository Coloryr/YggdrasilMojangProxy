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
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        byte[] array = new byte[2048];
        while (true) {
            int i = is.read(array);
            if (i == -1) break;
            bs.write(array, 0, i);
        }
        return bs.toByteArray();
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
                    URLConnection connect;
                    if (proxy == null) connect = x.openConnection();
                    else connect = x.openConnection(proxy);
                    HttpURLConnection huc = (HttpURLConnection) connect;
                    Loggin.boot.info("服务器返回：" + huc.getResponseCode());
                    Loggin.boot.info("服务器返回：" + huc.getResponseMessage());
                    if ((respone = huc.getResponseCode()) == 200) {
                        final byte[] got = readAll(huc.getInputStream());
                        store.value = new BuffedHttpConnection(url, got);
                        return;
                    }
                    huc.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                try {
                    Loggin.boot.info("尝试正版登录");
                    //从正版服务器验证
                    URL mojang = new URL(null, Main.mojangHasJoined + "?" + url.getQuery(), Main.https);
                    URLConnection connect;
                    if (proxy == null) connect = mojang.openConnection();
                    else connect = mojang.openConnection(proxy);
                    HttpURLConnection huc = (HttpURLConnection) connect;
                    Loggin.boot.info("服务器返回：" + huc.getResponseCode());
                    Loggin.boot.info("服务器返回：" + huc.getResponseMessage());
                    if ((respone = huc.getResponseCode()) == 200) {
                        final byte[] got = readAll(huc.getInputStream());
                        store.value = new BuffedHttpConnection(mojang, got);
                    }
                    huc.disconnect();
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
