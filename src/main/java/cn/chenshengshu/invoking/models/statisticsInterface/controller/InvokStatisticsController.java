package cn.chenshengshu.invoking.models.statisticsInterface.controller;

import cn.chenshengshu.invoking.models.statisticsInterface.service.InvokStatisticsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费业务模块生产的消息
 *
 */
@Component
public class InvokStatisticsController {

    @Autowired
    private InvokStatisticsService invokStatisticsService;

    @RabbitListener(queues = "${rabbitmq-server.exceptionQueue}")
    public void exceptionQueue(String message) {
        invokStatisticsService.exceptionService(message);
    }
}