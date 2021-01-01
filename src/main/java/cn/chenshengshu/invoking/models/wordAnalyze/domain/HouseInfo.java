package cn.chenshengshu.invoking.models.wordAnalyze.domain;

import lombok.Data;

/**
 * (HouseInfo 实体类
 *
 * @author chenshengshu
 * @since 2020-12-12 15:59:48
 */
@Data
public class HouseInfo {

    //  编号
    private Long id;

    //  标题
    private String houseTitle;

    // 描述
    private String houseDesc;

    //  图片
    private String image;

    //  价格
    private String price;

    // 地址
    private String address;

    //  建筑类型
    private String architectureType;

    //  建筑面积
    private String architectureArea;

    // 物业类型
    private String propertyType;

    //  楼盘户型
    private String floorType;

    // 物业公司
    private String propertyManagement;

    //  物业费
    private String propertyFee;

    //  供暖方式
    private String heatingWay;

    // 供水方式
    private String waterWay;

    //  供电方式
    private String elevatorWay;

    //  省
    private String province;

    // 市
    private String city;

    // 县
    private String county;

    //  是否有效
    private Integer state;
}