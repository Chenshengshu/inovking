package cn.chenshengshu.invoking.models.statisticsInterface.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenchengshu
 * @date 2020-12-12 17:13
 */
@Data
//获取房屋具体信息参数
public class HouseDetail {

    private Integer id;

    private String houseId;

    private Integer number;

    private LocalDateTime updateTime;
    

}
