package cn.chenshengshu.invoking.models.statisticsInterface.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Map;

/**
 * @author chenchengshu
 * @date 2020-12-22 16:23
 */
@Data
public class InterfaceStatistics {


    private Integer id;

    //url路径
    private String url;

    //uri路径
    private String uri;

    //请求方式
    private String httpMethod;

    //ip地址
    private String ip;

    //响应数据
    private String response;

    //执行时间
    private Long executeTime;

    //请求时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String requestTime;

    //请求参数
    private String request;

    //执行结果
    private String executeResult;
}
