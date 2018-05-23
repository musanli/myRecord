package com.main.util.exception;

/**
 * 工具类异常，当前完全等同于RuntimeException
 * @author li_bin
 *
 */
public class UtilException extends RuntimeException{
    static final long serialVersionUID = -7034897190745766939L;

    public UtilException() {
        super();
    }

    public UtilException(String message) {
        super(message);
    }
    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

}
