package cn.chenshengshu.invoking.config;

import cn.chenshengshu.invoking.models.statisticsInterface.domain.InterfaceStatistics;
import cn.chenshengshu.invoking.models.statisticsInterface.service.InvokStatisticsService;
import cn.chenshengshu.invoking.models.wordAnalyze.service.WordClassifyService;
import cn.chenshengshu.invoking.util.Helper;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 定时任务处理  （对于实时反馈不高的写到此类中 合理安排资源）
 * 1.对高频词汇统计
 * 2.对接口调用统计
 * <p>
 * 对于接口调用统计要求的实时性不高，所以利用定时任务在系统处于闲置状态进行消费消息
 * 对于每天只进行一次消费的话，可能存在一个弊端，当消费堆积过多时，消费时将所有消息
 * 消费完对于，每次消费会对数据库进行一次插入操作，对于数据库的压力会直线上升，所以
 * 可以一定时间进行消费，确定好消费时间和时间间隔，每次消费只消费x(x:可以为100可以
 * 为1000 根据业务产生的条数进行消费，最好不要太多，因为太多会给数据库太多压力)条。
 * <p>
 * cron 生成 https://cron.qqe2.com/
 *
 * @author chenchengshu
 * @date 2020-12-31 10:41
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTaskConfig {


    private static final boolean DUIABLE = true;//持久化
    private static final boolean EXCLUSIVE = false;//排他队列
    private static final boolean AUTO_DELETE = false;//没有consumer时，队列是否自动删除
    private static final boolean AUTO_ACK = true; //自动应答

    @Autowired
    private WordClassifyService wordClassifyService;

    //使用接口调用队列
    @Value("${rabbitmq-server.interfaceQueue}")
    private String QUEUE_NAME;

    @Autowired(required = false)
    private InvokStatisticsService invokStatisticsService;

    @Autowired
    private Connection connection;


    //每天 02:00:00 执行
    @Scheduled(cron = "0 0 2 * * ? ")
    //@Scheduled(fixedRate = 60000)
    private void configureTasks() {
        wordClassifyService.statisticsSave();
        System.out.println("Scheduled");
    }


    //定时任务每天晚上3点左右进行处理今天接口调用数据 @Scheduled(cron = "0 0 3 * * ? *")
    //todo 目前测试使用每一分钟进行主动拉取 后续可以使用每5分钟拉取一次 每次消费100条消息
    @Scheduled(cron = "0 * * * * ? ")
    public void pullInterfaceTasks() throws IOException {
        int flag = 0;
        List<InterfaceStatistics> interfaceStatisticsList = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        Channel channel = connection.createChannel();
        //定义一个队列
        channel.queueDeclare(QUEUE_NAME, DUIABLE, EXCLUSIVE, AUTO_DELETE, null);
        //接收消息
        log.info("QUEUE_NAME : {} 开始处理数据时间  : {}", QUEUE_NAME, Helper.getLocalDateTime().toString());
        GetResponse resp = channel.basicGet(QUEUE_NAME, AUTO_ACK);
        //查看队列中是否存在消息并消费消息
        while (Objects.nonNull(resp)) {
            String message = new String(resp.getBody(), "UTF-8");
            interfaceStatisticsList.add(JSONUtil.toBean(message, InterfaceStatistics.class));
            resp = channel.basicGet(QUEUE_NAME, AUTO_ACK);
        }
        if (interfaceStatisticsList.size() > 0) {
            flag = invokStatisticsService.interfaceServiceList(interfaceStatisticsList);
        }
        Long endTime = System.currentTimeMillis();
        log.info("QUEUE_NAME : {} 完成处理数据时间 : {}", QUEUE_NAME, Helper.getLocalDateTime().toString());
        log.info("执行时间 {} ms , 插入数据 {} 条", endTime - startTime, flag);
    }
}
