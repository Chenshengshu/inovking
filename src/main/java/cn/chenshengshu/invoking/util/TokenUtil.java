package cn.chenshengshu.invoking.util;

import java.util.UUID;

/**
 * token工具类
 *
 * @author chenchengshu
 * @date 2020-11-17 14:04
 */

public class TokenUtil {

    /**
     * 根据UUID生成token
     *
     * @return
     */
    public static String getToken() {
        return UUID.randomUUID().toString();
    }
}
