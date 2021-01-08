package cn.chenshengshu.invoking.config;

import cn.chenshengshu.invoking.models.statisticsInterface.dao.InvokStatisticsDao;
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
import java.util.Objects;

/**
 * 定时任务处理  （对于实时反馈不高的写到此类中 合理安排资源）
 * 1.对高频词汇统计
 * 2.对接口调用统计
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
    private InvokStatisticsService InvokStatisticsService;

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
    //todo 后续修改定时的时间 目前使用每一分钟进行主动拉取
    @Scheduled(cron = "0 * * * * ? ")
    public void pullInterfaceTasks() throws IOException {
        int flag = 0;
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
            flag += InvokStatisticsService.interfaceService(message);
            resp = channel.basicGet(QUEUE_NAME, AUTO_ACK);
        }
        Long endTime = System.currentTimeMillis();
        log.info("QUEUE_NAME : {} 完成处理数据时间 : {}", QUEUE_NAME, Helper.getLocalDateTime().toString());
        log.info("执行时间 {} ms , 插入数据 {} 条", endTime - startTime, flag);
    }
}
