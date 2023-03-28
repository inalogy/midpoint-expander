package com.inalogy.idm.utilities.expander.impl;

import com.beust.jcommander.JCommander;

import java.nio.charset.Charset;

import com.inalogy.idm.utilities.expander.opts.BaseOptions;
import com.inalogy.idm.utilities.expander.util.ExpanderUtils;
import com.inalogy.idm.utilities.expander.util.Log;

public class ExpanderContext {

    private JCommander jc;

    private Log log;

    public ExpanderContext(JCommander jc) {
        this.jc = jc;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public JCommander getJc() {
        return jc;
    }

    public Charset getCharset() {
        BaseOptions base = ExpanderUtils.getOptions(jc, BaseOptions.class);
        String charset = base.getCharset();

        return Charset.forName(charset);
    }

    public void destroy() {

    }
}
