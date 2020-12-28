package cn.chenshengshu.invoking.models.statisticsInterface.controller;

import cn.chenshengshu.invoking.models.statisticsInterface.service.InvokStatisticsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvokStatisticsContorller {

    @Autowired
    private InvokStatisticsService invokStatisticsService;

    @RabbitListener(queues = "${rabbitmq-server.interfaceQueue}")
    public void interfaceQueue(String message) {
        invokStatisticsService.interfaceService(message);
    }

    @RabbitListener(queues = "${rabbitmq-server.exceptionQueue}")
    public void exceptionQueue(String message) {
        invokStatisticsService.exceptionService(message);
    }
}