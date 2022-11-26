package it.dissan;

import it.dissan.config.Configuration;
import it.dissan.config.ConfigurationManager;
import it.dissan.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){

        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using port: " + configuration.getPort());
        LOGGER.info("Using WebRoot: " + configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO handler later
        }

    }
}
