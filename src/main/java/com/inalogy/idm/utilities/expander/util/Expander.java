package com.inalogy.idm.utilities.expander.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expander {
    public static final Pattern PATTERN = Pattern.compile("\\$\\((\\S*?)\\)");

    private Properties properties;

    public Expander(Properties properties) {
        this.properties = properties;
    }

    public String expand(String object) {
        if (object == null) {
            return null;
        }

        Matcher matcher = PATTERN.matcher(object);

        List<String> missingKeys = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            if (key.isEmpty()) {
                matcher.appendReplacement(sb, "");
                continue;
            }

            String value = expandKey(key);
            if (value == null) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group()));
                missingKeys.add(key);
            } else {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public InputStream expand(InputStream is, Charset charset) throws IOException {
        if (is == null) {
            return null;
        }

        String text = IOUtils.toString(is, charset);
        String expanded = expand(text);

        return new ByteArrayInputStream(expanded.getBytes());
    }

    private String expandKey(String key) {
        if (this.properties == null) {
            return null;
        }

        return this.properties.getProperty(key);
    }
}