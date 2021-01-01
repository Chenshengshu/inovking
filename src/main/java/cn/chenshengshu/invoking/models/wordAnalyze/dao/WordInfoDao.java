package cn.chenshengshu.invoking.models.wordAnalyze.dao;

import cn.chenshengshu.invoking.models.wordAnalyze.domain.WordInfo;

import java.util.List;

/**
 * @author chenchengshu
 * @date 2020-12-31 10:02
 */
public interface WordInfoDao {

    List<WordInfo> queryAll(WordInfo wordInfo);

    WordInfo query(WordInfo wordInfo);

    Integer insertWordInfo(WordInfo wordInfo);

    Integer updateWordNumber(WordInfo wordInfo);
}
