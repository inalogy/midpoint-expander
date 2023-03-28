package com.inalogy.idm.utilities.expander.opts;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;

@Parameters(resourceBundle = "messages", commandDescriptionKey = "expand")
public class ExpandOptions extends FileOptions {

    public static final String P_PROPERTIES_FILE = "-p";
    public static final String P_PROPERTIES_FILE_LONG = "--properties-file";

    @Parameter(names = {P_PROPERTIES_FILE, P_PROPERTIES_FILE_LONG}, descriptionKey = "expand.options.propertiesFile", required = true)
    private File properties;

    public File getPropertiesFile() {
        return properties;
    }
}
