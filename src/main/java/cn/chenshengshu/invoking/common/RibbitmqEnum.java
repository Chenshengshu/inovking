package cn.chenshengshu.invoking.common;

/**
 * @author chenchengshu
 * @date 2020-12-25 16:06
 */
public enum RibbitmqEnum {

    INTERFACE_QUEUE(1, "interfaceQueue", "接口调用队列，用于记录接口调用信息！"),
    HOUSE_DETAIL_QUEUE(2, "houseDetailQueue", "房屋详细信息调用， 可以用于记录房屋访问量，对热点房屋进行统计！"),
    EXCEPTION_QUEUE(3, "exceptionQueue", "异常统计队列，可以精确定位异常接口");

    RibbitmqEnum(Integer code, String key, String msg) {
        this.code = code;
        this.key = key;
        this.msg = msg;
    }

    public final Integer code;
    public final String key;
    public final String msg;

}
