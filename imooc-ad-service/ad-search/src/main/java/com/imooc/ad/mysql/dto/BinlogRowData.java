package com.imooc.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;
import java.util.Map;
import java.util.List;
/**
 * binlog  一定是对表进行操作才会有的数据
 * 就是将来我们去实现将binlog的日志数据也就是event 这个开源工具给我们传递的event
 * 把它转换为我们定义的java对象 在检索系统中定义的一个java对象
 */
@Data
public class BinlogRowData {

    private TableTemplate table;
    private EventType type;//事件类型
    private List<Map<String,String>> after; //更新前的数据
    private List<Map<String,String>> before;//对于插入和删除before 是空的 只有update和delete

}
