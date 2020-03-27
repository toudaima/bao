package com.ligouhai.bao.utils.pdf;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.ligouhai.bao.entity.pdf.PDFFooter;
import com.ligouhai.bao.entity.pdf.PDFHeader;
import com.ligouhai.bao.enums.pdf.PDFCellWidth;

import java.util.*;

/**
 * @author ligouhai
 * @date 2020-03-26 17:06
 * @description pdf工具类
 */
public class PDFUtil {

    public static void generatePDF() throws Exception {
        PdfWriter writer = new PdfWriter("C:/Users/pc03/Desktop/" + System.currentTimeMillis() + ".pdf");
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());


        // 页眉插入表格
        PDFHeader headerHandler = new PDFHeader(doc);
        pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
        doc.setMargins((20 + headerHandler.getTableHeight()), 36, 36, 36);


        // 页脚表格
        PDFFooter footerHandler = new PDFFooter(doc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
        doc.setMargins(36, 36, 72, 36);

        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",false);

        List<LinkedHashMap<String, Object>> list = generateData();
        boolean isFirst = true;
        for (LinkedHashMap<String, Object> map : list) {
            generateTable(doc, map, isFirst, font);
            isFirst = false;
        }

        doc.close();
    }


    public static void generateTable(Document doc, LinkedHashMap<String, Object> goods, boolean isFirst, PdfFont font) {
        //商品列表
        List skuList = (List) goods.get("skuList");
        //sku种类，用于合并行
        int rowspan = skuList.size();


        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
//        table.setWidth(523)
        table.setKeepTogether(true);
        table.setTextAlignment(TextAlignment.CENTER);
        if (isFirst) {
            table.setMarginTop(55);
        }

        goods.keySet().forEach(good -> {
            Object info = goods.get(good);
            if (info instanceof List) {
                ((List) info).forEach(sku ->
                    ((LinkedHashMap) sku).keySet().forEach(skuKey -> {
                        float width = PDFCellWidth.getWidthByField(skuKey.toString());
                        String skuInfo = ((LinkedHashMap) sku).get(skuKey).toString();
                        generateCell(skuInfo, width, table, 1, font);
                    }));
            } else {
                float width = PDFCellWidth.getWidthByField(good);
                generateCell(info.toString(), width, table, rowspan, font);
            }
        });
        doc.add(table);
    }

    /**
     * 创建cell
     */
    public static void generateCell(String info, float width, Table table, int rowspan, PdfFont font) {
        Paragraph paragraph = new Paragraph(info != null ? info : "-").setFont(font).setFontSize(8);
        //不拆分，放不下就全部到下一页
        paragraph.setKeepTogether(true);
        paragraph.setWidth(width);
        Cell cell = new Cell(rowspan, 1).add(paragraph);
        cell.setKeepTogether(true);
        cell.setHeight(20f);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(cell);
    }

    /**
     * 造数据
     * @return
     */
    public static List<LinkedHashMap<String, Object>> generateData() {
        //使用linkedHashMap保证顺序
        List<LinkedHashMap<String, Object>> list = new ArrayList(32);
        for (int i = 0; i < 1; i ++) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(8);
            map.put("orderCode", "111111");
            map.put("orderNo", "123442354234");
            map.put("merchantName", "名称123432");

            LinkedHashMap<String, Object> sku1 = new LinkedHashMap<>(8);
            sku1.put("goodsNo", "6666666666");
            sku1.put("goodsName", "牛奶");
            sku1.put("format", "香草味");
            sku1.put("goodsNum", 5);
            sku1.put("unit", "盒");

            LinkedHashMap<String, Object> sku2 = new LinkedHashMap<>(8);
            sku2.put("goodsNo", "2032904920421");
            sku2.put("goodsName", "猪肉");
            sku2.put("format", "辣味");
            sku2.put("goodsNum", 3);
            sku2.put("unit", "斤");

            List skuList = Arrays.asList(sku1, sku2);

            map.put("skuList", skuList);
            list.add(map);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            generatePDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
