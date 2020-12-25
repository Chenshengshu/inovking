package cn.chenshengshu.invoking.util;


import cn.chenshengshu.invoking.common.Result;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@Service
public class Helper {

    public static <T> String makeUrlParam(String name, T t) {
        return t == null ? "" : (name + "=" + t);
    }


    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

    public static LocalDate getLocalDate(){
        return LocalDate.now();
    }
    public static Long daysBetween(LocalDate begin, LocalDate end) {

        if (begin == null || end == null) return null;
        return Duration.between(toLocalDateTime(begin), toLocalDateTime(end)).toDays();
    }

    public static Long daysToNow(LocalDate date) {
        return daysBetween(date, LocalDate.now());
    }

    public static Long yearsToNow(LocalDate date) {
        return daysToNow(date) / 365;
    }

    public static Long daysToNow(LocalDateTime dateTime) {
        return Duration.between(dateTime, LocalDateTime.now()).toDays();
    }

    public static Long hoursToNow(LocalDateTime dateTime) {
        return Duration.between(dateTime, LocalDateTime.now()).toHours();
    }

    public static Long minutesToNow(LocalDateTime dateTime) {
        return Duration.between(dateTime, LocalDateTime.now()).toMinutes();
    }

    public static Long yearsToNow(LocalDateTime dateTime) {
        return daysToNow(dateTime) / 365;
    }


    /*public static byte[] getBytesFromMultipartFile(MultipartFile multipartFile) {

        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            log.error("MultipartFile 字节读取错误", e);
            throw new DispatcherException(Result.Code.FAILED);
        }
    }*/


    public static String remoteAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


    public static void writeException(ServletResponse response, Result.Code code) {
        try (OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
             Writer writer = new PrintWriter(osw, true)) {
            writer.write(JSONUtil.toJsonStr(Result.failed(code)));
            writer.flush();
        } catch (UnsupportedEncodingException e) {
            log.error("TokenFiler handleException 错误", e);
        } catch (IOException e) {
            log.error("TokenFiler handleException 错误", e);
        }
    }

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter dateTimeFormatterLink = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter dateFormatterXieGang = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static DateTimeFormatter dateFormatterNoSign = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDateTime toLocalDateTime(LocalDate date) {
        return LocalDateTime.parse(date.format(dateFormatter) + " 00:00:00", dateTimeFormatter);
    }

    public static String getLocalDataTimeStr(LocalDateTime time) {
        return time.format(dateTimeFormatter);
    }
    public static String getLocalDataWithXieGangStr(LocalDate time) {
        return time.format(dateFormatterXieGang);
    }
    public static String getLocalDataWithHengGangStr(LocalDate time) {
        return time.format(dateFormatter);
    }

    public static String dateFormatterNoSign(LocalDate time) {
        return time.format(dateFormatterNoSign);
    }

    public static String getLocalDataTimeLinkStr(LocalDateTime time) {
        return time.format(dateTimeFormatterLink);
    }

    public static byte[] getBytesFromMultipartFile(MultipartFile multipartFile) throws Exception {

        return multipartFile.getBytes();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static boolean equalsAny(Integer source, Integer... targets) {
        if (null == source) {
            return Arrays.stream(targets).anyMatch(target -> null == target);
        }
        return Arrays.stream(targets).anyMatch(target -> source.equals(target));
    }

    public static boolean equalsAny(Long source, Long... targets) {
        if (null == source) {
            return Arrays.stream(targets).anyMatch(target -> null == target);
        }
        return Arrays.stream(targets).anyMatch(target -> source.equals(target));
    }

    public static boolean equalsAny(String source, String... targets) {
        if (null == source) {
            return Arrays.stream(targets).anyMatch(target -> null == target);
        }
        return Arrays.stream(targets).anyMatch(target -> source.equals(target));
    }

    //获得字符串的md5值
    public static String generateMD5ForString(byte[] str){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            log.error("获取MD5值 错误", e);
            return null;
        }
        //更新摘要数据
        md.update(str);
        //生成摘要数组
        byte[] digest = md.digest();
        //清空摘要数据，以便下次使用
        md.reset();
        return formatByteArrayTOString(digest);
    }

    public static String formatByteArrayTOString(byte[] digest) {
        if(digest == null){
            log.error("获取MD5值 错误");
            return null;
        }
        //创建sb用于保存md5值
        StringBuffer sb = new StringBuffer();
        int temp;
        for (int i=0;i<digest.length;i++) {
            //将数据转化为0到255之间的数据
            temp=digest[i]&0xff;
            if (temp < 16) {
                sb.append(0);
            }
            //Integer.toHexString(temp)将10进制数字转换为16进制
            sb.append(Integer.toHexString(temp));
        }
        return sb.toString();
    }


    public static <T> List<T> extract(Map<Long, List<T>> map) {
        List<T> result = new ArrayList<>();
        for (List list : map.values()) {
            result.addAll(list);
        }
        return result;
    }
}
