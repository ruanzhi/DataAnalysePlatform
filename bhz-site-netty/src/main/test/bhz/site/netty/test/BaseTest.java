/**
 * <br>项目名: test
 * <br>文件名: BaseTest.java
 * <br>Copyright 2015 恒昌互联网运营中心
 */
package bhz.site.netty.test;

import bhz.dat.protocol.RequestData;
import bhz.site.netty.netty.Client;

import bhz.site.netty.utils.SpringUtil;
import bhz.site.netty.entity.DatCheckData;
import bhz.site.netty.utils.GenerateData;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.BeanUtils;


import java.util.List;


/**
 * <br>类 名: BaseTest
 * <br>描 述: 描述类完成的主要功能
 * <br>作 者: rz
 * <br>创 建： 2018年1月9日
 * <br>版 本：v1.0.0
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

public class BaseTest {


    public void send(GenerateData generate) {
        try {
            Client c = Client.getInstance();
            ChannelFuture cf = c.getChannelFuture();
            List<DatCheckData> datCheckDatas = generate.batchAdd(10);
            for (DatCheckData datCheckData : datCheckDatas) {
                RequestData rd = new RequestData();
                BeanUtils.copyProperties(datCheckData, rd, new String[]{"sync"});
                cf.channel().writeAndFlush(rd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        GenerateData generate = (GenerateData) SpringUtil.getBean("generateData");
        BaseTest baseTest = new BaseTest();
        baseTest.send(generate);
        System.out.println("再启动.....");
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
