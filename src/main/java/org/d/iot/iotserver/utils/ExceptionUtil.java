package org.d.iot.iotserver.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ClassName: ExceptionUtil <br/>
 * Description: 异常工具类 <br/>
 * date: 2019/9/5 22:21<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class ExceptionUtil {

    /**
     * 将异常堆栈信息转化成字符串
     * @param e
     * @return
     */
    public static String stackTrace(Exception e){
        StringWriter sw = null;
        PrintWriter pw = null;
        try{
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        }finally {
            IOUtils.closeQuietly(sw);
            IOUtils.closeQuietly(pw);
        }
        return sw.toString();
    }
}
