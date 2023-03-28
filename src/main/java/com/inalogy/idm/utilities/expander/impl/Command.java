package com.inalogy.idm.utilities.expander.impl;

import com.inalogy.idm.utilities.expander.action.ExpandAction;
import com.inalogy.idm.utilities.expander.action.FileAction;
import com.inalogy.idm.utilities.expander.opts.ExpandOptions;

public enum Command {
    EXPAND("expand", ExpandOptions.class, ExpandAction.class);

    private String commandName;

    private Class options;

    private Class<? extends FileAction> action;

    Command(String commandName, Class options, Class<? extends FileAction> stylesheetAction) {
        this.commandName = commandName;
        this.options = options;
        this.action = stylesheetAction;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object createOptions() {
        try {
            return options.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static FileAction createStylesheetAction(String command) {
        Command cmd = findCommand(command);
        if (cmd == null) {
            return null;
        }

        try {
            if (cmd.action == null) {
                return null;
            }

            return cmd.action.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Command findCommand(String command) {
        if (command == null) {
            return null;
        }

        for (Command cmd : values()) {
            if (command.equals(cmd.getCommandName())) {
                return cmd;
            }
        }

        return null;
    }
}