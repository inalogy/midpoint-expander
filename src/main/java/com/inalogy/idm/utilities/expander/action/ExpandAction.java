package com.inalogy.idm.utilities.expander.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inalogy.idm.utilities.expander.opts.ExpandOptions;
import com.inalogy.idm.utilities.expander.util.Expander;

import org.apache.commons.io.FileUtils;

public class ExpandAction extends FileAction<ExpandOptions> {

    private Expander expander;

    @Override
    public void preProcessing() throws Exception {
        Properties properties = loadProperties();
        this.expander = new Expander(properties);
    }

    @Override
    protected void processFile(File file) {

        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);

            InputStream expandedStream = expander.expand(inputStream, context.getCharset());

            inputStream.close();

            FileUtils.copyInputStreamToFile(expandedStream, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterProcessing() throws Exception {

    }

    private Properties loadProperties() throws Exception {
        File propertiesFile = options.getPropertiesFile();
        if (propertiesFile == null) {
            throw new IllegalStateException("Option '" + ExpandOptions.P_PROPERTIES_FILE + "' must be specified.");
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties properties = new Properties();

            // load a properties file
            properties.load(input);

            return properties;
        }
    }
}
