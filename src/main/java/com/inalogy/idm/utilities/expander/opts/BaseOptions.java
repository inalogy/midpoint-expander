package com.inalogy.idm.utilities.expander.opts;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(resourceBundle = "messages")
public class BaseOptions {

    public static final String P_HELP = "-h";
    public static final String P_HELP_LONG = "--help";

    public static final String P_VERBOSE = "-v";
    public static final String P_VERBOSE_LONG = "--verbose";

    public static final String P_SILENT = "-s";
    public static final String P_SILENT_LONG = "--silent";

    public static final String P_CHARSET = "-c";
    public static final String P_CHARSET_LONG = "--charset";

    public static final String P_VERSION = "-V";
    public static final String P_VERSION_LONG = "--version";


    @Parameter(names = {P_HELP, P_HELP_LONG}, help = true, descriptionKey = "base.help")
    private boolean help = false;

    @Parameter(names = {P_VERBOSE, P_VERBOSE_LONG}, descriptionKey = "base.verbose")
    private boolean verbose = false;

    @Parameter(names = {P_SILENT, P_SILENT_LONG}, descriptionKey = "base.silent")
    private boolean silent = false;

    @Parameter(names = {P_CHARSET, P_CHARSET_LONG}, descriptionKey = "base.charset")
    private String charset = "utf-8";

    @Parameter(names = {P_VERSION, P_VERSION_LONG}, descriptionKey = "base.version")
    private boolean version = false;

    public boolean isHelp() {
        return help;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isSilent() {
        return silent;
    }

    public String getCharset() {
        return charset;
    }

    public boolean isVersion() {
        return version;
    }
}
