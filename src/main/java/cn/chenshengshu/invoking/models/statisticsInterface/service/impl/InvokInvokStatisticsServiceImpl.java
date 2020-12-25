package cn.chenshengshu.invoking.models.statisticsInterface.service.impl;

import cn.chenshengshu.invoking.models.statisticsInterface.dao.InvokStatisticsDao;
import cn.chenshengshu.invoking.models.statisticsInterface.domain.InterfaceStatistics;
import cn.chenshengshu.invoking.models.statisticsInterface.service.InvokStatisticsService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author chenchengshu
 * @date 2020-12-25 17:03
 */
@Service
@Slf4j
public class InvokInvokStatisticsServiceImpl implements InvokStatisticsService {


    @Autowired(required = false)
    private InvokStatisticsDao invokStatisticsDao;


    @Override
    public void interfaceService(String message) {

        log.info("interfaceQueue 消费者收到消息 : {}", message);
        InterfaceStatistics interfaceStatistics = JSONUtil.toBean(message, InterfaceStatistics.class);
        Integer insert = invokStatisticsDao.insert(interfaceStatistics);
        log.info("interfaceStatistics{}", JSONUtil.toJsonStr(interfaceStatistics));
    }

    @Override
    public void exceptionService(String message) {

        log.info("exceptionQueue 消费者收到消息 : {}", message);
        InterfaceStatistics interfaceStatistics = JSONUtil.toBean(message, InterfaceStatistics.class);
        invokStatisticsDao.insert(interfaceStatistics);
        log.info("interfaceStatistics{}", JSONUtil.toJsonStr(interfaceStatistics));
    }
}
