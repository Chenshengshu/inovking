package cn.chenshengshu.invoking.util;

import org.springframework.stereotype.Component;

/**
 * 验证码工具类
 * <p>
 * 可以产生不同长度的随机数
 *
 * @author chenchengshu
 * @date 2020-11-17 09:45
 */
@Component
public class VerificationCodeUtil {

    //默认验证码长度为4
    public static final int DEFAULT_LENGTH = 4;

    final static String ENUM_CODE[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String getCode() {
        return getCode(DEFAULT_LENGTH);
    }

    public static String getCode(int len) {
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int index = (int) (Math.random() * 10);
            code.append(ENUM_CODE[index]);
        }
        return code.toString();
    }

}
