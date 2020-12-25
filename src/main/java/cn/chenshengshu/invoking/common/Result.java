package cn.chenshengshu.invoking.common;


import lombok.Getter;

/**
 * 100:短信
 * 110:用户
 *
 * @param <T>
 */
@Getter
public class Result<T> {
    public enum Code {
        OK(0, "成功", "成功"),
        FAILED(1, "失败", "失败"),
        TAX_REQUEST_ERRR(100001, "用户不存在", "用户名或密码错误"),
        TOKEN_ERROR(100002, "Token不存在", "登录过期，请重新登录"),
        TAX_REQUEST_EXIST(100003, "用户重复", "用户名已经注册"),
        TAX_REQUEST_LOSE(100004, "用户注册失败", "注册失败，请稍后重试"),
        TAX_REQUEST_NOT_EXIST(100005, "验证码不存在", "请获取验证码"),
        QR_CODE_LOSE(100006, "二维码登陆异常", "二维码错误,请重新获取!"),
        PARAM_ERROR(110001, "请求参数异常", "请求参数异常");

        Code(Integer code, String msg, String webMsg) {
            this.code = code;
            this.msg = msg;
            this.webMsg = webMsg;
        }

        public final Integer code;
        public final String msg;
        public final String webMsg;
    }

    public Result() {
    }

    public Result(Code code) {
        this.code = code.code;
        this.msg = code.webMsg;
    }

    public Result(Code status, T data) {
        this.code = status.code;
        this.msg = status.webMsg;
        this.data = data;
    }

    public static Result success(Object object) {
        return new Result(Code.OK, object);
    }

    public static Result failed(Code code) {
        return new Result(code);
    }

    public static Result failed(Code code, String error) {
        Result result = new Result(code);
        //result.error = error;
        return result;
    }

    public Integer code;
    public String msg;
    public T data;
}
