package it.dissan.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.dissan.util.Json;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;
    private ConfigurationManager(){
    }

    @Contract(pure = true)
    public static @NotNull ConfigurationManager getInstance(){
        if (configurationManager == null){
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    /**
     * Configuring all the configuration class by parsing a Json file
     * @param filePath
     * @throws IOException
     */

    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();

        int characterReturned;

        while(true){
            try {
                if (!((characterReturned = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            stringBuffer.append((char) characterReturned);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error in parsing File configuration: ",e);
        }
        try {
            currentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing File configuration, internal: ",e);
        }
    }

    /**
     *
     * @return Configuration throw exception if there's no current configuration
     */

    public Configuration getCurrentConfiguration(){

        if (currentConfiguration == null){
            throw new HttpConfigurationException("No current configuration set.");
        }

        return currentConfiguration;
    }
}
