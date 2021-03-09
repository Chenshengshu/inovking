package cn.chenshengshu.invoking.models.statisticsInterface.service;

import cn.chenshengshu.invoking.models.statisticsInterface.domain.InterfaceStatistics;

import java.util.List;

/**
 * @author chenchengshu
 * @date 2020-12-25 17:03
 */
public interface InvokStatisticsService {

    Integer interfaceService(String message);

    void exceptionService(String message);

    Integer interfaceServiceList(List<InterfaceStatistics> list);

    void numberStatisticsQueue(String message);


}
