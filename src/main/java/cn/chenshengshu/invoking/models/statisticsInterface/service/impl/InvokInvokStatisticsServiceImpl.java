package cn.chenshengshu.invoking.models.statisticsInterface.service.impl;

import cn.chenshengshu.invoking.models.statisticsInterface.dao.InvokStatisticsDao;
import cn.chenshengshu.invoking.models.statisticsInterface.domain.HouseDetail;
import cn.chenshengshu.invoking.models.statisticsInterface.domain.InterfaceStatistics;
import cn.chenshengshu.invoking.models.statisticsInterface.dto.request.HouseDetailReq;
import cn.chenshengshu.invoking.models.statisticsInterface.service.InvokStatisticsService;
import cn.chenshengshu.invoking.util.Helper;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author chenchengshu
 * @date 2020-12-25 17:03
 */
@Service
@Slf4j
public class InvokInvokStatisticsServiceImpl implements InvokStatisticsService {


    @Autowired(required = false)
    private InvokStatisticsDao invokStatisticsDao;


    @Override
    public Integer interfaceService(String message) {

        // log.info("interfaceQueue 消费者收到消息 : {}", message);
        InterfaceStatistics interfaceStatistics = JSONUtil.toBean(message, InterfaceStatistics.class);
        int flag = invokStatisticsDao.insert(interfaceStatistics);
        log.info("interfaceStatistics : {}", flag > 0 ? "消费成功" : "消费失败");
        return flag;
    }

    @Override
    public Integer interfaceServiceList(List<InterfaceStatistics> list) {
        Integer flag = invokStatisticsDao.insertList(list);
        return flag;
    }

    @Override
    public void exceptionService(String message) {

        //todo 异常消息接受到后 后续进行钉钉提醒 将消息转发到钉钉提醒服务处理
        //log.info("exceptionQueue 消费者收到消息 : {}", message);
        InterfaceStatistics interfaceStatistics = JSONUtil.toBean(message, InterfaceStatistics.class);
        int flag = invokStatisticsDao.insert(interfaceStatistics);
        log.info("interfaceStatistics : {}", flag > 0 ? "消费成功" : "消费失败");
    }


    @Override
    public void numberStatisticsQueue(String message) {
        HouseDetailReq houseDetailReq = JSONUtil.toBean(message, HouseDetailReq.class);
        HouseDetail houseDetail = invokStatisticsDao.findHouseDetail(houseDetailReq.getHouseId());
        if (Objects.isNull(houseDetail)) {
            //insert
            HouseDetail houseNumber = new HouseDetail();
            houseNumber.setHouseId(houseDetailReq.getHouseId());
            houseNumber.setNumber(1);
            houseNumber.setUpdateTime(Helper.getLocalDateTime());
            Integer flag = invokStatisticsDao.insertHouseLookNumber(houseNumber);
            if (flag <= 0) {
                log.info("房屋访问统计出错！房屋id{}", houseDetailReq.getHouseId());
            }

        } else {
            //update
            int number = houseDetail.getNumber() + 1;
            HouseDetail houseNumber = new HouseDetail();
            houseNumber.setId(houseDetail.getId());
            houseNumber.setNumber(number);
            houseNumber.setUpdateTime(Helper.getLocalDateTime());
            Integer flag = invokStatisticsDao.updateHouseLookNumber(houseNumber);
            if (flag <= 0) {
                log.info("房屋访问统计出错！房屋id{}", houseDetailReq.getHouseId());
            }
        }

    }

    @Override
    public void titleWordQueue(String message) {


    }


}
