package cn.mcres.karlatemp.mojangyggdrasil.Inject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BuffedHttpConnection extends HttpURLConnection {
    private final byte[] got;
    private final int code;

    protected BuffedHttpConnection(URL u, byte[] got) {
        this(u, got, 200);
    }

    public BuffedHttpConnection(URL url, byte[] bytes, int respone) {
        super(url);
        this.got = bytes;
        this.code = respone;
    }

    @Override
    public int getResponseCode() {
        return code;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(got);
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() {
        connected = true;
    }
}
