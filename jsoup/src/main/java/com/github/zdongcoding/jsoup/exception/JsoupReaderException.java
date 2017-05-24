package com.github.zdongcoding.jsoup.exception;


/**
 * Created by zoudong on 2017/3/10.
 */

public class JsoupReaderException extends RuntimeException {
    public JsoupReaderException() {
    }

    public JsoupReaderException(String msg) {
        super(msg);
    }

    public JsoupReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsoupReaderException(Throwable cause) {
        super(cause);
    }


}
