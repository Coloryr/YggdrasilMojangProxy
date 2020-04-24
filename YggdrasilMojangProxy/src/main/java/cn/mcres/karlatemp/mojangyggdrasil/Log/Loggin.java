package cn.mcres.karlatemp.mojangyggdrasil.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Loggin {
    public static final Logger boot = Logger.getLogger("辅助登录");
    public static final PrintStream ps = System.out;
    private static final String n = "\n";

    static {
        boot.setUseParentHandlers(false);
        boot.addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                if (isLoggable(record)) {
                    Throwable thr = record.getThrown();
                    record.setThrown(null);
                    StringBuilder bui = new StringBuilder().append(getFormatter().formatMessage(record));
                    if (thr != null) {
                        bui.append('\n');
                        StringWriter sw = new StringWriter();
                        thr.printStackTrace(new PrintWriter(sw));
                        bui.append(sw);
                    }
                    write(bui, record.getLoggerName());
                }
            }
        });
    }

    private synchronized static void write(StringBuilder bui, String lname) {
        synchronized (ps) {
            int i = 0, k;
            while ((k = bui.indexOf(n, i)) > 0) {
                ps.append('[').append(lname).append("] ").println(bui.subSequence(i, k));
                i = k + 1;
            }
            String cut = bui.substring(i);
            if (!cut.isEmpty()) {
                ps.append('[').append(lname).append("] ").println(cut);
            }
        }
    }
}
