package cn.lvhaosir.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvhaosir on 2018/9/17.
 *
 *  response 结果输出工具类
 *
 */
public class ResponseUtil {

    public static void out(ServletResponse response,Map<String,Object> result) {

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }

    }

    /**
     *  无数据结果返回
     * @param flag
     * @param code
     * @param msg
     * @return
     */
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg){

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", flag);
        resultMap.put("message", msg);
        resultMap.put("code", code);
        resultMap.put("timestamp", System.currentTimeMillis());
        return resultMap;
    }

    /**
     *  包含数据结果返回
     * @param flag
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg, Object data){

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", flag);
        resultMap.put("message", msg);
        resultMap.put("code", code);
        resultMap.put("timestamp", System.currentTimeMillis());
        resultMap.put("result", data);
        return resultMap;
    }



}
