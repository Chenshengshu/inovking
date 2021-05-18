package cn.chenshengshu.invoking.HanLP;

import cn.chenshengshu.invoking.models.wordAnalyze.dao.HouseInfoDao;
import cn.chenshengshu.invoking.models.wordAnalyze.domain.HouseInfo;
import cn.chenshengshu.invoking.models.wordAnalyze.service.WordClassifyService;
import cn.chenshengshu.invoking.util.HanLPUtils;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @author chenchengshu
 * @date 2020-12-27 22:49
 */
@SpringBootTest
public class HanLPUtilsEnumTest {


    @Autowired(required = false)
    private HouseInfoDao houseInfoDao;

    @Autowired
    private WordClassifyService wordClassifyService;

    @Test
    public void HanLpTest() {
        String text = "整租 . 精装 大一房 近地铁 中间楼层 随时入住 车位充足";
        List<String> list = HanLP.extractKeyword(text, 5);
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("-----------------");
        List<String> wordInfos = HanLP.extractPhrase(text, 6);
        for (String wordInfo : wordInfos) {
            System.out.println(wordInfo);
        }

        System.out.println("------------------");
        List<Term> segment = StandardTokenizer.segment(text);
        for (Term term : segment) {
            System.out.println(term.word);
        }

        System.out.println("------------------");

    }

    @Test
    public void wordTest() throws IOException {
        String document = "整租 . 精装 大一房近地铁中间楼层 随时入住 车位充足 凯欣诚意出租的房品质小区采光好 楼层佳 交通便利";
        String[] testCase = new String[]{
                "整租精装大一房近地铁中间楼层随时入住车位充足凯欣诚意出租的房品质小区采光好楼层佳交通便利",
                "凯欣诚意出租的房品质小区采光好楼层佳交通便利",
        };

        List<Term> termList = StandardTokenizer.segment(testCase[0]);
        for (int i = 0; i < termList.size(); i++) {
            if (termList.get(i).nature.toString() == "a" && (termList.get(i + 1).nature.toString() == "n" || termList.get(i + 1).nature.toString() == "nz")) {
                String word = termList.get(i).word + termList.get(i + 1).word;
                System.out.println(word);
                i++;
            }
            if ((termList.get(i).nature.toString() == "n" || termList.get(i).nature.toString() == "vn") && termList.get(i + 1).nature.toString() == "a") {
                String word = termList.get(i).word + termList.get(i + 1).word;
                System.out.println(word);
                i++;
            }
            if (i >= termList.size()) {
                break;
            }
        }
        System.out.println(termList);

    }

    @Test
    public void HanLPUtilsTest() {
        String text =
                "整租精装大一房近地铁中间楼层随时入住车位充足凯欣诚意出租的房品质小区采光好楼层佳交通便利 凯欣诚意出租的房品质小区采光好楼层佳交通便利";

        Set<String> keys = HanLPUtils.tentativeSegment(text);
        for (String key : keys) {
            System.out.println(key);
        }
    }

    @Test
    public void NLPTest1() {
        HouseInfo houseInfo = new HouseInfo();

        List<HouseInfo> houseInfos = houseInfoDao.queryAll(houseInfo);

        for (HouseInfo info : houseInfos) {
            System.out.println(info);
        }
    }

    @Test
    public void NLPServiceTest() {
        wordClassifyService.statisticsSave();

    }

    @Test
    public void test() {
        String text = "近地铁的超大房子";
        Set<String> sets = HanLPUtils.tentativeSegment(text);
        for (String set : sets) {
            System.out.println(set);
        }
    }

}
