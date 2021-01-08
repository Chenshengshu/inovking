package cn.chenshengshu.invoking;

import cn.chenshengshu.invoking.util.Helper;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenchengshu
 * @date 2021-01-08 15:27
 */
@SpringBootTest
public class IntefaceTest {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Test
    public void test() throws IOException, TimeoutException {
        System.out.println(host);
    }
}
