package cn.chenshengshu.invoking.common;

/**
 * @author chenchengshu
 * @date 2020-12-28 17:04
 */
public class HanLPEnum {

    public enum Nature {
        NATURE_A(1, "a", "形容词"),
        NATURE_N(2, "n", "名词"),
        NATURE_NZ(3, "nz", "其他专名"),
        NATURE_VN(4, "vn", "动名词"),
        NATURE_V(5, "v", "动词");

        Nature(Integer code, String key, String decipher) {
            this.code = code;
            this.key = key;
            this.decipher = decipher;
        }

        public final Integer code;
        public final String key;
        public final String decipher;
    }
}
