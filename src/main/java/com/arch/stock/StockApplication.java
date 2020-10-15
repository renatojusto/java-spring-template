package com.arch.stock;

import com.arch.stock.config.StockConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StockApplication {

    private static final Logger log = LoggerFactory.getLogger(StockApplication.class);
    private final Environment env;

    public StockApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StockApplication.class);
        addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defProperties);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                     "Application '{}' is running! Access URLs:\n\t" +
                     "Local: \t\t{}://localhost:{}{}\n\t" +
                     "External: \t{}://{}:{}{}\n\t" +
                     "Swagger: \t{}://localhost:{}{}/swagger-ui.html\n\t" +
                     "Profile(s): \t{}\n----------------------------------------------------------",
                 env.getProperty("spring.application.name"),
                 protocol,
                 serverPort,
                 contextPath,
                 protocol,
                 hostAddress,
                 serverPort,
                 contextPath,
                 protocol,
                 serverPort,
                 contextPath,
                 env.getActiveProfiles());
    }

    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(StockConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(StockConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                          "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(StockConstants.SPRING_PROFILE_DEVELOPMENT)) {
            log.error("You have misconfigured your application! It should not " +
                          "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

}
