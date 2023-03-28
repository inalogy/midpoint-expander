package com.inalogy.idm.utilities.expander.util;

import com.beust.jcommander.JCommander;
import com.inalogy.idm.utilities.expander.impl.Command;
import com.inalogy.idm.utilities.expander.opts.BaseOptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

public class ExpanderUtils {

    public static JCommander setupCommandLineParser() {
        BaseOptions baseOptions = new BaseOptions();

        JCommander.Builder builder = JCommander.newBuilder()
                .expandAtSign(false)
                .addObject(baseOptions);

        for (Command cmd : Command.values()) {
            builder.addCommand(cmd.getCommandName(), cmd.createOptions());
        }

        JCommander jc = builder.build();
        jc.setProgramName("java -jar expander.jar");
        jc.setColumnSize(150);
        jc.setAtFileCharset(Charset.forName(baseOptions.getCharset()));

        return jc;
    }

    public static <T> T getOptions(JCommander jc, Class<T> type) {
        List<Object> objects = jc.getObjects();
        for (Object object : objects) {
            if (type.equals(object.getClass())) {
                return (T) object;
            }
        }

        return null;
    }

    public static String printStackToString(Exception ex) {
        if (ex == null) {
            return null;
        }

        StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer));

        return writer.toString();
    }
}
