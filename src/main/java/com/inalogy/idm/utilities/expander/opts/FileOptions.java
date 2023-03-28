package com.inalogy.idm.utilities.expander.opts;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;

import com.inalogy.idm.utilities.expander.impl.IsDirectoryParameterValidator;

@Parameters(resourceBundle = "messages")
public class FileOptions {

    public static final String P_WORKING_DIRECTORY = "-w";
    public static final String P_WORKING_DIRECTORY_LONG = "--working-directory";

    public static final String P_EXTENSION = "-e";
    public static final String P_EXTENSION_LONG = "--extension";

    @Parameter(names = {P_WORKING_DIRECTORY, P_WORKING_DIRECTORY_LONG}, descriptionKey = "file.options.workingDirectory", required = true,
            validateWith = IsDirectoryParameterValidator.class)
    private File workingDirectory;

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    @Parameter(names = {P_EXTENSION, P_EXTENSION_LONG}, descriptionKey = "file.options.extension", required = true)
    private String extension;

    public String getExtension() {
        return extension;
    }
}
