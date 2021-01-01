package cn.chenshengshu.invoking.models.wordAnalyze.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenchengshu
 * @date 2020-12-28 20:59
 */
@Data
public class WordInfo {

    private Integer id;

    //单词实体
    private String wordName;

    //出现次数
    private Integer number;

    //创建时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

}
