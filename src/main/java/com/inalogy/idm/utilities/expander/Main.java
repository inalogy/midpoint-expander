package com.inalogy.idm.utilities.expander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.inalogy.idm.utilities.expander.impl.Command;
import com.inalogy.idm.utilities.expander.impl.ExpanderContext;
import com.inalogy.idm.utilities.expander.opts.BaseOptions;
import com.inalogy.idm.utilities.expander.opts.FileOptions;
import com.inalogy.idm.utilities.expander.util.ExpanderUtils;

import org.apache.commons.io.FileUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.inalogy.idm.utilities.expander.action.FileAction;

public class Main {

    public static void main(String[] args) {
        new Main().run(args);
    }

    protected void run(String[] args) {
        JCommander jc = ExpanderUtils.setupCommandLineParser();

        try {
            jc.parse(args);
        } catch (ParameterException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        String parsedCommand = jc.getParsedCommand();

        BaseOptions base = ExpanderUtils.getOptions(jc, BaseOptions.class);

        if (base.isVersion()) {
            try {
                Path path = Paths.get(Main.class.getResource("/version").toURI());
                String version = FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
                System.out.println(version);
            } catch (Exception ex) {
            }
            return;
        }

        if (base.isHelp() || parsedCommand == null) {
            printHelp(jc, parsedCommand);
            return;
        }

        if (base.isVerbose() && base.isSilent()) {
            System.err.println("Cant' use " + BaseOptions.P_VERBOSE + " and " + BaseOptions.P_SILENT
                    + " together (verbose and silent)");
            printHelp(jc, parsedCommand);
            return;
        }

        ExpanderContext context = null;
        try {
            FileAction action = Command.createStylesheetAction(parsedCommand);

            if (action == null) {
                System.err.println("Action for command '" + parsedCommand + "' not found");
                return;
            }

            Object options = jc.getCommands().get(parsedCommand).getObjects().get(0);

            context = new ExpanderContext(jc);

            action.init(context, (FileOptions) options);

            action.execute();
        } catch (Exception ex) {
            handleException(base, ex);
        } finally {
            cleanupResources(base, context);
        }

    }

    private void cleanupResources(BaseOptions opts, ExpanderContext context) {
        try {
            if (context != null) {
                context.destroy();
            }
        } catch (Exception ex) {
            if (opts.isVerbose()) {
                String stack = ExpanderUtils.printStackToString(ex);

                System.err.print("Unexpected exception occurred (" + ex.getClass()
                        + ") during destroying context. Exception stack trace:\n" + stack);
            }
        }
    }

    private void handleException(BaseOptions opts, Exception ex) {
        if (!opts.isSilent()) {
            System.err.println("Unexpected exception occurred (" + ex.getClass() + "), reason: " + ex.getMessage());
        }

        if (opts.isVerbose()) {
            String stack = ExpanderUtils.printStackToString(ex);

            System.err.print("Exception stack trace:\n" + stack);
        }
    }

    private void printHelp(JCommander jc, String parsedCommand) {
        if (parsedCommand == null) {
            jc.usage();
        } else {
            jc.usage(parsedCommand);
        }
    }

}