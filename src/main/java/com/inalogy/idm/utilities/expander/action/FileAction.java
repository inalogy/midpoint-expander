package com.inalogy.idm.utilities.expander.action;

import com.inalogy.idm.utilities.expander.impl.ExpanderContext;
import com.inalogy.idm.utilities.expander.opts.FileOptions;
import com.inalogy.idm.utilities.expander.util.Log;
import com.inalogy.idm.utilities.expander.util.LogTarget;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;

import java.util.Collection;
import java.util.Collections;

public abstract class FileAction<T extends FileOptions> {

    protected Log log;
    protected ExpanderContext context;

    protected T options;
    protected Collection<File> files = Collections.EMPTY_LIST;

    public void init(ExpanderContext context, T options) {
        this.context = context;
        this.options = options;

        LogTarget target = getInfoLogTarget();
        log = new Log(target, this.context);

        this.context.setLog(log);

        this.files = FileUtils.listFiles(
                this.options.getWorkingDirectory(),
                new RegexFileFilter("^(?!~\\$).*\\." + options.getExtension() + "$"), DirectoryFileFilter.DIRECTORY
        );
    }

    public void execute() throws Exception {
        preProcessing();

        files.stream().forEachOrdered(file -> {
            processFile(file);
        });

        afterProcessing();
    }

    protected abstract void preProcessing() throws Exception;

    protected abstract void processFile(File stream);

    protected abstract void afterProcessing() throws Exception;

    public LogTarget getInfoLogTarget() {
        return LogTarget.SYSTEM_OUT;
    }
}
