package cn.chenshengshu.invoking.config;

import cn.chenshengshu.invoking.models.wordAnalyze.service.WordClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author chenchengshu
 * @date 2020-12-31 10:41
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTaskConfig {

    @Autowired
    private WordClassifyService wordClassifyService;

    //明天 02:00:00 执行
    @Scheduled(cron = "0 0 2 * * ? ")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate = 60000)
    private void configureTasks() {
        wordClassifyService.statisticsSave();
        System.out.println("Scheduled");
    }


}
