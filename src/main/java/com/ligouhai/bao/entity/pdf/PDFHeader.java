package com.ligouhai.bao.entity.pdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.TableRenderer;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author ligouhai
 * @date 2020-03-26 16:51
 * @description 生成pdf的header信息
 */
public class PDFHeader implements IEventHandler {

    protected Table table;
    protected Table headTable;
    protected float tableHeight;
    protected Document doc;

    public PDFHeader(Document doc) {
        this.doc = doc;
        table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
        //table.setWidth(770);
        PdfFont titleFont = null;
        PdfFont tableFont = null;
        try {
            titleFont = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",false);
            tableFont = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",false);
        } catch (Exception e) {

        }
        Paragraph paragraph = new Paragraph("头信息1").setFont(titleFont);
        Cell cell = new Cell(0,8).add(paragraph);
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setBorder(Border.NO_BORDER);
        Color bgColour = new DeviceRgb(0, 255, 255);
        cell.setBackgroundColor(bgColour);
        table.addCell(cell);


        float fontSize = 8;
        LocalDate localDate = LocalDate.now();
        StringBuilder sb = new StringBuilder().append(localDate.getYear()).append("年").append(localDate.getMonthValue()).append("月").append(localDate.getDayOfMonth()).append("日");
        Cell cell2 = new Cell(1,4).add(new Paragraph("打印日期:" + sb.toString()).setFont(tableFont).setFontSize(fontSize));
        cell2.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell2);

        Cell cell3 = new Cell(1,4).add(new Paragraph("配送日期:" + new Date()).setFont(tableFont).setFontSize(fontSize));
        cell3.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell3);

        Cell cell4 = new Cell(2,2).add(new Paragraph("商家名称").setFont(tableFont).setFontSize(fontSize));
        cell4.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell4);

        Cell cell5 = new Cell(2,2).add(new Paragraph("商家电话").setFont(tableFont).setFontSize(fontSize));
        cell5.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell5);

        Cell cell6 = new Cell(2,4).add(new Paragraph("详细地址").setFont(tableFont).setFontSize(fontSize));
        cell6.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell6);


        // 表头
        Cell cell7 = new Cell().add(new Paragraph("识别码").setFont(tableFont).setFontSize(fontSize).setWidth(60));
        cell7.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell7);

        Cell cell8 = new Cell().add(new Paragraph("订单编号").setFont(tableFont).setFontSize(fontSize).setWidth(120));
        cell8.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell8);

        Cell cell9 = new Cell().add(new Paragraph("自提点").setFont(tableFont).setFontSize(fontSize).setWidth(100));
        cell9.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell9);

        Cell cell10 = new Cell().add(new Paragraph("编码").setFont(tableFont).setFontSize(fontSize).setWidth(120));
        cell10.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell10);

        Cell cell11 = new Cell().add(new Paragraph("商品名称").setFont(tableFont).setFontSize(fontSize).setWidth(173));
        cell11.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell11);

        Cell cell12 = new Cell().add(new Paragraph("品牌").setFont(tableFont).setFontSize(fontSize).setWidth(80));
        cell12.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell12);

        Cell cell13 = new Cell().add(new Paragraph("数量").setFont(tableFont).setFontSize(fontSize).setWidth(40));
        cell13.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell13);

        Cell cell14 = new Cell().add(new Paragraph("单位").setFont(tableFont).setFontSize(fontSize).setWidth(40));
        cell14.setTextAlignment(TextAlignment.CENTER).setBorderBottom(Border.NO_BORDER);
        table.addCell(cell14);


        TableRenderer renderer = (TableRenderer) table.createRendererSubTree();
        renderer.setParent(new Document(new PdfDocument(new PdfWriter(new ByteArrayOutputStream()))).getRenderer());
        tableHeight = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4.rotate()))).getOccupiedArea().getBBox().getHeight();
    }
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Rectangle rect = new Rectangle(36,
                (pdfDoc.getDefaultPageSize().getTop() - doc.getTopMargin()-getTableHeight() + 20), (page.getPageSize().getWidth() - 72), getTableHeight());
        new Canvas(canvas, pdfDoc, rect)
                .add(table);
    }

    public float getTableHeight() {
        return tableHeight;
    }
}
