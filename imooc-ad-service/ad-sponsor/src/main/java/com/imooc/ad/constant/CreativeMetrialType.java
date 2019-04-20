package com.imooc.ad.constant;

import lombok.Getter;

/**
 * 这些信息都会提交给广告请求的广告方 需要告诉他们真正类型是什么
 * 以便于选择正确的解析器
 */
@Getter
public enum CreativeMetrialType {
    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp4"),
    AVI(4, "avi"),

    TXT(5, "txt");
    private int type;
    private String message;

    CreativeMetrialType(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
