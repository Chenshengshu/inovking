package cn.chenshengshu.invoking.util;

import cn.chenshengshu.invoking.common.HanLPEnum;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.*;

/**
 * @author chenchengshu
 * @date 2020-12-28 11:15
 */

public class HanLPUtils {

    /**
     * 分词处理
     * 对特殊词进行处理
     *
     * @param text
     * @return
     */
    public static Set<String> tentativeSegment(String text) {
        Set<String> set = new TreeSet<>();
        System.out.println("text : " + text);
        List<Term> termList = StandardTokenizer.segment(text);
        System.out.println(termList);
        for (int i = 0; i < termList.size(); i++) {
            if (i >= termList.size() - 2) {
                break;
            }
            String word = "";
            if (termList.get(i).nature.toString() == HanLPEnum.Nature.NATURE_A.key
                    && (termList.get(i + 1).nature.toString() == HanLPEnum.Nature.NATURE_N.key
                    || termList.get(i + 1).nature.toString() == HanLPEnum.Nature.NATURE_NZ.key)) {
                word = termList.get(i).word + termList.get(i + 1).word;
                i++;
            }
            if ((termList.get(i).nature.toString() == HanLPEnum.Nature.NATURE_N.key
                    || termList.get(i).nature.toString() == HanLPEnum.Nature.NATURE_A.key
                    || termList.get(i).nature.toString() == HanLPEnum.Nature.NATURE_V.key)
                    && termList.get(i + 1).nature.toString() == HanLPEnum.Nature.NATURE_A.key) {
                word = termList.get(i).word + termList.get(i + 1).word;
                i++;
            }
            set.add(word);
        }
        return set;
    }


}





