
/**
* @title: ConfigUtil.java
* @package com.pt.core.util
* @author
* @version v1.00
* @description: TODO(用一句话描述该文件做什么)
*/ 

package com.fintech.platform.tools.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


	
/**
 * @classname: ConfigUtil
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ConfigUtil {

    public static String SERVER_UA_HOST;

    public static int SERVER_UA_PORT;

    public static String SERVER_TICKET_HOST;

    public static int SERVER_TICKET_PORT;

    public static String SERVER_REDIS_HOST;

    public static int SERVER_REDIS_PORT;
    
    public static long systemId;

    private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    public static Properties p = null;

    static {

        InputStream is = ConfigUtil.class.getResourceAsStream("/config.properties");

        p = new Properties();
        try {
            p.load(is);
            SERVER_UA_HOST = p.getProperty("ua.host");
            SERVER_UA_PORT = Integer.parseInt(p.getProperty("ua.port") == null ? "0" : p.getProperty("ua.port"));
            SERVER_TICKET_HOST = p.getProperty("ticket.host");
            SERVER_TICKET_PORT = Integer.parseInt(p.getProperty("ticket.port") == null ? "0" : p
                    .getProperty("ticket.port"));

            SERVER_REDIS_HOST = p.getProperty("redis.host");
            SERVER_REDIS_PORT = Integer.parseInt(p.getProperty("redis.port") == null ? "0" : p
                    .getProperty("redis.port"));
            systemId = Long.parseLong(p.getProperty("systemId") == null ? "0" : p
                    .getProperty("systemId"));
        }
        catch (IOException e) {
            logger.error("载入配置文件出错/conf.properties," + e.getMessage());
        }

        logger.info("ua.host=" + SERVER_UA_HOST);
        logger.info("ua.port=" + SERVER_UA_PORT);
        logger.info("ticket.host=" + SERVER_TICKET_HOST);
        logger.info("ticket.port=" + SERVER_TICKET_PORT);
        logger.info("redis.host=" + SERVER_REDIS_HOST);
        logger.info("redis.port=" + SERVER_REDIS_PORT);
        logger.info("systemId=" + systemId);

    }
}
