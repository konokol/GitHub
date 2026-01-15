package com.github.log;

/**
 * LogcatFactory
 *
 * @author  Ivan on 2020-04-15
 * @version v1.0
 * @since   v1.0
 */
public class LogcatFactory implements ILoggerFactory {

    @Override
    public ILogger create() {
        return new LogcatLogger();
    }
}
