package cn.chenshengshu.invoking.models.statisticsInterface.dao;

import cn.chenshengshu.invoking.models.statisticsInterface.domain.InterfaceStatistics;

import java.util.List;

/**
 * @author chenchengshu
 * @date 2020-12-25 21:50
 */
public interface InvokStatisticsDao {

    List<InterfaceStatistics> queryAll(InterfaceStatistics interfaceStatistics);

    Integer insert(InterfaceStatistics interfaceStatistics);

    Integer update(InterfaceStatistics interfaceStatistics);


}
