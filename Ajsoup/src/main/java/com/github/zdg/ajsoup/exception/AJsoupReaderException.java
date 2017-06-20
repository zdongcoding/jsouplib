package com.github.zdg.ajsoup.exception;


/**
 * Created by zoudong on 2017/3/10.
 */

public class AJsoupReaderException extends RuntimeException {
    public AJsoupReaderException() {
    }

    public AJsoupReaderException(String msg) {
        super(msg);
    }

    public AJsoupReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AJsoupReaderException(Throwable cause) {
        super(cause);
    }


}
