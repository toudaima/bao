package com.ligouhai.bao.enums.pdf;

/**
 * @author liling
 * @date 2020-03-27 09:42
 *
 * @description pdf中cell对应的宽度
 */
public enum PDFCellWidth {

    /**
     * 小票识别码
     */
    ORDER_CODE(1, "orderCode", 60f, "小票识别码"),
    /**
     * 订单编号
     */
    ORDER_NO(2, "orderNo", 120f, "订单编号"),
    /**
     * 自提点
     */
    MERCHANT_NAME(3, "merchantName", 100f, "自提点"),
    /**
     * 商品编码
     */
    GOODS_NO(4, "goodsNo", 120f, "商品编码"),
    /**
     * 商品名称
     */
    GOODS_NAME(5, "goodsName", 173f, "商品名称"),
    /**
     * 品牌、型号、规格
     */
    FORMAT(6, "format", 80f, "品牌、型号、规格"),
    /**
     * 商品数量
     */
    GOODS_NUM(7, "goodsNmu", 40f, "商品数量"),
    /**
     * 单位
     */
    UNIT(8, "unit", 40f, "单位");


    private final Integer key;
    private final String field;
    private final float width;
    private final String description;

    PDFCellWidth(Integer key, String field, float width, String description) {
        this.key = key;
        this.field = field;
        this.width = width;
        this.description = description;
    }

    public static float getWidthByField(String field) {
        switch (field) {
            case "orderCode":
                return PDFCellWidth.ORDER_CODE.width;
            case "orderNo":
                return PDFCellWidth.ORDER_NO.width;
            case "merchantName":
                return PDFCellWidth.MERCHANT_NAME.width;
            case "goodsNo":
                return PDFCellWidth.GOODS_NO.width;
            case "goodsName":
                return PDFCellWidth.GOODS_NAME.width;
            case "format":
                return PDFCellWidth.FORMAT.width;
            case "goodsNum":
                return PDFCellWidth.GOODS_NUM.width;
            case "unit":
                return PDFCellWidth.UNIT.width;
                default:
                    return 30f;
        }
    }
}
