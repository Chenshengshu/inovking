package cn.chenshengshu.invoking.models.wordAnalyze.dao;


import cn.chenshengshu.invoking.models.wordAnalyze.domain.HouseInfo;

import java.util.List;

/**
 * (HouseInfo)表数据库访问层
 *
 * @author chenshengshu
 * @since 2020-12-12 15:59:50
 */
public interface HouseInfoDao {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param houseInfo 实例对象
     * @return 对象列表
     */
    List<HouseInfo> queryAll(HouseInfo houseInfo);


}