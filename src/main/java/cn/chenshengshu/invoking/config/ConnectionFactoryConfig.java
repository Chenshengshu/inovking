package cn.chenshengshu.invoking.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息队列主动拉取数据 服务器设置
 *
 * @author chenchengshu
 * @date 2021-01-08 15:41
 */
@Configuration
public class ConnectionFactoryConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean(value = "interfaceFactory")
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        //设置登录账号
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean(value = "interfaceConnection")
    public Connection getConnection() throws IOException, TimeoutException {
        //连接服务器
        Connection connection = getConnectionFactory().newConnection();
        return connection;
    }

}
