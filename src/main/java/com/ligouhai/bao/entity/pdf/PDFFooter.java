package com.ligouhai.bao.entity.pdf;

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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.TableRenderer;

import java.io.ByteArrayOutputStream;

/**
 * @author ligouhai
 * @date 2020-03-26 17:02
 * @description 生成pdf的footer信息
 */
public class PDFFooter implements IEventHandler {

    protected Table table;

    protected float tableHeight;

    protected Document doc;

    protected int pageNo;

    public PDFFooter(Document doc) {
        this.doc = doc;
        table = new Table(UnitValue.createPercentArray(10)).useAllAvailableWidth();

        PdfFont tableFont = null;
        try {
            tableFont = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",false);
        } catch (Exception e) {

        }

        float fontSize = 9f;
        Cell cell2 = new Cell().add(new Paragraph("分拣员").setFont(tableFont).setFontSize(fontSize));
        cell2.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell2);

        Cell cell3 = new Cell().setWidth(126);
        table.addCell(cell3);

        Cell cell4 = new Cell().add(new Paragraph("配送员").setFont(tableFont).setFontSize(fontSize));
        cell4.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell4);

        Cell cell5 = new Cell();
        table.addCell(cell5);

        Cell cell6 = new Cell().add(new Paragraph("配送时间").setFont(tableFont).setFontSize(fontSize));
        cell6.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell6);

        Cell cell7 = new Cell();
        table.addCell(cell7);

        Cell cell8 = new Cell().add(new Paragraph("商家").setFont(tableFont).setFontSize(fontSize));
        cell8.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell8);

        Cell cell9 = new Cell();
        table.addCell(cell9);

        Cell cell10 = new Cell().add(new Paragraph("接收时间").setFont(tableFont).setFontSize(fontSize));
        cell10.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell10);

        Cell cell11 = new Cell();
        table.addCell(cell11);

        Cell cell12 = new Cell().add(new Paragraph("备注").setFont(tableFont).setFontSize(fontSize));
        cell12.setTextAlignment(TextAlignment.CENTER);
        table.addCell(cell12);

        Cell cell13 = new Cell(1,9);
        table.addCell(cell13);

        TableRenderer renderer = (TableRenderer) table.createRendererSubTree();
        renderer.setParent(new Document(new PdfDocument(new PdfWriter(new ByteArrayOutputStream()))).getRenderer());
        tableHeight = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4.rotate()))).getOccupiedArea().getBBox().getHeight();
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",false);
        } catch (Exception e) {

        }
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        pageNo ++;
        new Canvas(canvas, pdfDoc, new Rectangle(36, 10, (page.getPageSize().getWidth() - 72), 66))
                .add(table).add(new Paragraph("--"+pageNo+"--").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(7));
    }
}
