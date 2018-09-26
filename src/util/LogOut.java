package util;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogOut {
    public static Logger log = Logger.getLogger(LogOut.class);
    public LogOut() {
        configlog4j.config();
    }

    public void testprint1() {
        log.debug("debug test");
        log.info("info test");
        log.warn("warn test");
        log.error("error test");
    }
}

class configlog4j {
    public static void config() {
        String connectdir=LogOut.class.getResource("log4j.properties").getPath();
        LogOut.log.debug("url:::>> " + connectdir);
        PropertyConfigurator.configure(connectdir);
    }
}
