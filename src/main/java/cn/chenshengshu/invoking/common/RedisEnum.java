package cn.chenshengshu.invoking.common;

/**
 * @author chenchengshu
 * @date 2020-11-18 14:38
 */
public class RedisEnum {

    public enum Key {
        CELLPHONE_KEY(1001, "LOGIN", "登陆时加在手机号后");

        Key(Integer code, String key, String msg) {
            this.code = code;
            this.key = key;
            this.msg = msg;
        }

        public final Integer code;
        public final String key;
        public final String msg;
    }

    public enum TTL {
        TOKEN(60L * 60L * 24L * 30, "token的失效时间为30天"),
        CAPTCHA(60L * 10L, "手机验证码失效时间为 10分钟");

        TTL(Long time, String msg) {
            this.time = time;
            this.msg = msg;
        }

        public final Long time;

        public final String msg;


    }

    //用于下拉 缓存的key
    public enum Address {
        PROVINCE(2001, "PROVINCE", "省"),
        CITY(2002, "CITY", "市"),
        AREA(2003, "COUNTY", "县");

        Address(Integer code, String key, String msg) {
            this.code = code;
            this.key = key;
            this.msg = msg;
        }
        public final Integer code;
        public final String key;
        public final String msg;

    }

}
