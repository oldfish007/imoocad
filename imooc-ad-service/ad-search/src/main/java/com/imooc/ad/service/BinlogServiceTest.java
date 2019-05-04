package com.imooc.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author
 * @description
 * @date 2019/4/26
 *
 * Write---------------
 * WriteRowsEventData{tableId=72, includedColumns={0, 1, 2}, rows=[
 *     [12, 12, bj40]
 * ]}
 * Update--------------
 * UpdateRowsEventData{tableId=72, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
 *     {before=[11, 11, 宝马], after=[11, 11, 大众]}
 * ]}
 *
 *
 *
 *
 *
 *
 */
public class BinlogServiceTest {
    public static void main(String[] args) throws Exception {
        //把自己当成一个slave 连接到mysql上面去
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",3306,"root","123"

        );
        //client.setBinlogFilename(); 从哪一个文件开始监听
        //如果不设置 他将从最新的最后一个文件开始监听
       // client.setBinlogPosition(2892); //需要监听的一个位置
        //注册到一个事件的监听器  监听器就会去监听mysql发生的一个变化
        client.registerEventListener(event -> {

            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update--------------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write---------------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete--------------");
                System.out.println(data.toString());
            }
        });
        client.connect();

    }
}
