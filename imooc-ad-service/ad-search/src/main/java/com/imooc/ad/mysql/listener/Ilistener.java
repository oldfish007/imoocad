package com.imooc.ad.mysql.listener;

import com.imooc.ad.mysql.dto.BinlogRowData;

/**
 * 实现对增量索引的更新
 */
public interface Ilistener {

    void register();
    void onEvent(BinlogRowData eventData);
}

