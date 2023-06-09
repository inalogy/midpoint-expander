package com.inalogy.idm.utilities.expander.util;

import com.inalogy.idm.utilities.expander.impl.ExpanderContext;
import com.inalogy.idm.utilities.expander.opts.BaseOptions;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;

public class Log {
    private static final String LOGGER_SYS_OUT = "SYSOUT";
    private static final String LOGGER_SYS_ERR = "SYSERR";

    private LogTarget target;
    private ExpanderContext context;

    private BaseOptions opts;

    private Logger info;
    private Logger error;

    public Log(LogTarget target, ExpanderContext context) {
        this.target = target;
        this.context = context;

        init();
    }

    private void init() {
        opts = ExpanderUtils.getOptions(context.getJc(), BaseOptions.class);

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        ConsoleAppender out = setupAppender("STDOUT", "System.out", lc, setupEncoder(lc));
        ConsoleAppender err = setupAppender("STDERR", "System.err", lc, setupEncoder(lc));

        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        if (LogTarget.SYSTEM_OUT.equals(target)) {
            root.addAppender(out);
        } else {
            root.addAppender(err);
        }

        root.setLevel(Level.OFF);

        info = setupLogger(LOGGER_SYS_OUT, opts);
        error = setupLogger(LOGGER_SYS_ERR, opts);

        error.setAdditive(false);
        error.addAppender(err);
    }

    private Logger setupLogger(String name, BaseOptions opts) {
        Logger logger = (Logger) LoggerFactory.getLogger(name);

        if (opts.isSilent()) {
            logger.setLevel(Level.OFF);
        } else if (opts.isVerbose()) {
            logger.setLevel(Level.DEBUG);
        } else {
            logger.setLevel(Level.INFO);
        }

        return logger;
    }

    private Encoder setupEncoder(LoggerContext ctx) {
        PatternLayoutEncoder ple = new PatternLayoutEncoder();

        if (opts.isVerbose()) {
            ple.setPattern("%date [%thread] %-5level \\(%logger{46}\\): %message%n");
        } else {
            ple.setPattern("%msg%n");
        }

        ple.setContext(ctx);
        ple.start();

        return ple;
    }

    private ConsoleAppender setupAppender(String name, String target, LoggerContext ctx, Encoder enc) {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setName(name);
        appender.setTarget(target);
        appender.setContext(ctx);
        appender.setEncoder(enc);

        appender.start();

        return appender;
    }

    public void error(String message, Object... args) {
        error(message, null, args);
    }

    public void error(String message, Exception ex, Object... args) {
        error.error(message, args);

        if (opts.isVerbose()) {
            error.error("Exception details", ex);
        }
    }

    public void debug(String message, Object... args) {
        info.debug(message, args);
    }

    public void info(String message, Object... args) {
        info.info(message, args);
    }

}
