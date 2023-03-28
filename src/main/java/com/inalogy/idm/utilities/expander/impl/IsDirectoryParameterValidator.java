package com.inalogy.idm.utilities.expander.impl;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

public class IsDirectoryParameterValidator implements IParameterValidator {
    @Override
    public void validate(String name, String path) throws ParameterException {
        File directory = new File(path);
        if (!directory.exists()) {
            throw new ParameterException("Directory '" + path + "' specified for parameter " + name + " does not exist.");
        } else if (!directory.isDirectory()) {
            throw new ParameterException("Value '" + path + "' specified for parameter " + name + " is not valid directory.");
        }
    }
}
