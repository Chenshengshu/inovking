package cn.chenshengshu.invoking.HanLP;

import cn.chenshengshu.invoking.util.HanLPUtils;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author chenchengshu
 * @date 2020-12-27 22:49
 */
@SpringBootTest
public class HanLPUtilsEnumTest {

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
}
