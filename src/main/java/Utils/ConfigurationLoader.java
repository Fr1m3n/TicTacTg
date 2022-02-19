package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
    private static final String PATH_TO_PROPERTIES = "src/main/resources/configuration.properties";
    private Properties props = new Properties();
    private FileInputStream inputStream;
    private static ConfigurationLoader instance;

    private ConfigurationLoader(String path) {
        try {
            inputStream = new FileInputStream(path);
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println("Failed to open config file: " + path);
            e.printStackTrace();
        }
    }

    public static ConfigurationLoader getInstance() {
        if (instance == null) {
            instance = new ConfigurationLoader(PATH_TO_PROPERTIES);
        }
        return instance;
    }

    public String getProperty(String property) {
        return props.getProperty(property);
    }
}
