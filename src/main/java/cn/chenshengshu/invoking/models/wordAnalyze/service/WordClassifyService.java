package cn.chenshengshu.invoking.models.wordAnalyze.service;

import cn.chenshengshu.invoking.models.wordAnalyze.domain.WordInfo;

import java.util.Map;

/**
 * @author chenchengshu
 * @date 2020-12-28 20:58
 */
public interface WordClassifyService {


    Map<String, Integer> segmentWordAndStatistics();

    WordInfo getWordsInfo(String name);

    void statisticsSave();


}