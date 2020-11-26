package cn.lvhaosir.core.utils;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvhaosir on 2018/10/7.
 */
public class ObjectUtil {

    public static String mapToString(Map<String,String[]> paramMap) {
        if (paramMap == null) {
            return "";
        }
        Map<String, Object> map = new HashMap<>(16);
        for (Map.Entry<String,String[]> param : paramMap.entrySet()) {
            String key = param.getKey();
            String paramValue = "";
            if (param.getValue() != null && param.getValue().length > 0 ) {
                paramValue = param.getValue()[0];
            }
            String obj = paramValue;
            if (StringUtils.endsWithIgnoreCase(key,"password")) {
                obj = "******";
            }
            map.put(key,obj);
        }
        return new Gson().toJson(map);
    }

}
