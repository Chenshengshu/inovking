package cn.chenshengshu.invoking.models.wordAnalyze.service.impl;

import cn.chenshengshu.invoking.common.HanLPEnum;
import cn.chenshengshu.invoking.models.wordAnalyze.dao.HouseInfoDao;
import cn.chenshengshu.invoking.models.wordAnalyze.dao.WordInfoDao;
import cn.chenshengshu.invoking.models.wordAnalyze.domain.HouseInfo;
import cn.chenshengshu.invoking.models.wordAnalyze.domain.WordInfo;
import cn.chenshengshu.invoking.models.wordAnalyze.service.WordClassifyService;
import cn.chenshengshu.invoking.util.Helper;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenchengshu
 * @date 2020-12-28 20:58
 */
@Service
public class WordClassifyServiceImpl implements WordClassifyService {

    @Autowired(required = false)
    private HouseInfoDao houseInfoDao;

    @Autowired(required = false)
    private WordInfoDao wordInfoDao;


    @Override
    public void statisticsSave() {

        // Map<String, Integer> words = extractPhrase();
        Map<String, Integer> words = segmentWordAndStatistics();

        Set<String> names = words.keySet();
        for (String name : names) {
            WordInfo wordInfo = getWordsInfo(name);
            Integer number = words.get(name);
            //如果不存在该单词 就新插入一个该单词
            if (Objects.isNull(wordInfo)) {
                wordInfo = new WordInfo();
                wordInfo.setWordName(name);
                wordInfo.setNumber(number);
                wordInfo.setCreateTime(Helper.getLocalDateTime());
                wordInfo.setUpdateTime(Helper.getLocalDateTime());
                wordInfoDao.insertWordInfo(wordInfo);
            } else {
                wordInfo.setNumber(wordInfo.getNumber() + number);
                wordInfoDao.updateWordNumber(wordInfo);
            }
        }

    }

    @Override
    public WordInfo getWordsInfo(String name) {
        //todo 查询是否含有该单词
        WordInfo word = new WordInfo();
        word.setWordName(name);
        WordInfo wordInfo = wordInfoDao.query(word);
        return wordInfo;
    }


    // 标准分词 带有词性的分词
    @Override
    public Map<String, Integer> segmentWordAndStatistics() {

        HouseInfo houseInfo = new HouseInfo();
        Map<String, Integer> map = new HashMap<>();
        List<HouseInfo> houseInfos = houseInfoDao.queryAll(houseInfo);
        for (HouseInfo info : houseInfos) {
            String text = info.getHouseTitle();
            List<Term> termList = StandardTokenizer.segment(text);
            for (int i = 0; i < termList.size(); i++) {
                if (i >= termList.size() - 2) {
                    break;
                }
                //String word = "";
                if (termList.get(i).nature.toString() == HanLPEnum.Nature.NATURE_A.key && (termList.get(i + 1).nature.toString() == HanLPEnum.Nature.NATURE_N.key || termList.get(i + 1).nature.toString() == HanLPEnum.Nature.NATURE_NZ.key)) {
                    String word = termList.get(i).word + termList.get(i + 1).word;
                    i++;
                    if (!map.containsKey(word)) {
                        map.put(word, 1);
                    } else {
                        Integer temp = map.get(word);
                        temp++;
                        map.put(word, temp);

                    }

                }
            }
        }
        return map;
    }


    // 短语提取
    private Map<String, Integer> extractPhrase() {
        HouseInfo houseInfo = new HouseInfo();
        Map<String, Integer> map = new HashMap<>();
        List<HouseInfo> houseInfos = houseInfoDao.queryAll(houseInfo);

        for (HouseInfo info : houseInfos) {
            String text = info.getHouseTitle();
            System.out.println("text" + text);

            List<String> words = HanLP.extractPhrase(text, 4);
            for (String word : words) {
                if (!map.containsKey(word)) {
                    map.put(word, 1);
                } else {
                    Integer temp = map.get(word);
                    temp++;
                    map.put(word, temp);
                }
            }
        }
        return map;
    }
}