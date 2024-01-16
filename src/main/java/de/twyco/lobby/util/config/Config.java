package de.twyco.lobby.util.config;

import de.twyco.lobby.Lobby;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Config {

    private final FileConfiguration fileConfiguration;
    private final File file;
    private final Logger logger;

    public Config(String fileName, File filePath) throws IOException {
        logger = Lobby.getInstance().getLogger();
        file = new File(filePath, fileName);

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException("Could not create filepath.");
            }
        }

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Could not create file.");
                }
            } catch (IOException e) {
                logger.severe("Error creating the config file. " + e.getMessage());
            }
        }
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Error loading the fileConfiguration. " + e.getMessage());
        }
    }

    public Config(String fileName, File filePath, String defaultFileName) throws IOException {
        logger = Lobby.getInstance().getLogger();
        file = new File(filePath, fileName);

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                throw new IOException("Could not create filepath.");
            }
        }

        if (!file.exists()) {
            copyDefaultConfig(file, defaultFileName);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Error loading the fileConfiguration. " + e.getMessage());
        }
    }

    private void copyDefaultConfig(File file, String defaultFileName) {
        try (InputStream inputStream = Lobby.getInstance().getResource(defaultFileName)) {
            if (inputStream != null) {
                try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            logger.severe("Error copying the default" + defaultFileName + "config. " + e.getMessage());
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Error reloading the config. " + e.getMessage());
        }
    }

}
