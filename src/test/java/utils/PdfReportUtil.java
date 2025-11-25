package utils;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.openqa.selenium.*;

import driver.DriverFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PdfReportUtil {

    private PDDocument document;
    private PDPageContentStream contentStream;
    private PDPage currentPage;

    private float yPosition = 760;
    private AtomicInteger stepCount = new AtomicInteger(1);


    public PdfReportUtil(String executionTitle) throws IOException {
        document = new PDDocument();
        newPage();
        drawHeader(executionTitle);
    }

    // -----------------------------------------------------------
    // NUEVA PÁGINA
    // -----------------------------------------------------------
    private void newPage() throws IOException {
        if (contentStream != null) contentStream.close();
        currentPage = new PDPage(PDRectangle.A4);
        document.addPage(currentPage);
        contentStream = new PDPageContentStream(document, currentPage);
        yPosition = 760;
    }

    // -----------------------------------------------------------
    // PORTADA
    // -----------------------------------------------------------
    private void drawHeader(String title) throws IOException {

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 22);
        contentStream.setNonStrokingColor(new Color(0, 70, 160));
        contentStream.newLineAtOffset(120, 780);
        contentStream.showText(title);
        contentStream.endText();

        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(120, 760);
        contentStream.showText("Fecha de ejecución: " + date);
        contentStream.endText();

        drawLine(40, 740, 550, new Color(0, 70, 160));

        yPosition = 720;
    }

    private void drawLine(float x1, float y, float x2, Color color) throws IOException {
        contentStream.setStrokingColor(color);
        contentStream.setLineWidth(1.2f);
        contentStream.moveTo(x1, y);
        contentStream.lineTo(x2, y);
        contentStream.stroke();
    }

    // -----------------------------------------------------------
    // NUEVO ESCENARIO
    // -----------------------------------------------------------
    public void startScenario(String scenarioName) throws IOException {

        if (yPosition < 200) newPage();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("ESCENARIO: " + scenarioName);
        contentStream.endText();

        yPosition -= 30;
        drawLine(45, yPosition, 545, new Color(0, 70, 160));
        yPosition -= 25;

        // reset de pasos por escenario
        stepCount.set(1);
    }

    // -----------------------------------------------------------
    // AGREGAR PASO CON CAPTURA
    // -----------------------------------------------------------
    public void addStep(String stepName, String status) throws IOException {

        WebDriver driver = DriverFactory.getDriver();

        // tomar screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File destFile = new File("target/evidencias/evidencia_" + timestamp + ".png");
        destFile.getParentFile().mkdirs();
        Files.copy(screenshot.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        if (yPosition < 280) newPage();

        int num = stepCount.getAndIncrement();

        // nombre paso
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.showText("Paso " + num + ": " + stepName);
        contentStream.endText();

        // estado
        if ("PASSED".equalsIgnoreCase(status)) {
            contentStream.setNonStrokingColor(Color.GREEN.darker());
        } else {
            contentStream.setNonStrokingColor(Color.RED);
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 12);
        contentStream.newLineAtOffset(450, yPosition);
        contentStream.showText("[" + status + "]");
        contentStream.endText();

        yPosition -= 20;

        // insertar imagen
        PDImageXObject image = PDImageXObject.createFromFile(destFile.getAbsolutePath(), document);
        contentStream.drawImage(image, 50, yPosition - 180, 480, 160);

        drawLine(45, yPosition - 190, 545, new Color(0, 70, 160));

        yPosition -= 210;
    }

    // -----------------------------------------------------------
    // RESUMEN FINAL
    // -----------------------------------------------------------
    public void closeAndSummarize() throws IOException {

        newPage();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.newLineAtOffset(160, 750);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.showText("RESUMEN DE EJECUCIÓN");
        contentStream.endText();

        drawLine(120, 735, 480, new Color(0, 70, 160));

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(120, 700);
        contentStream.showText("Total Casos de Prueba: " + GlobalReport.totalTests);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.setNonStrokingColor(Color.GREEN.darker());
        contentStream.newLineAtOffset(120, 670);
        contentStream.showText("PASSED: " + GlobalReport.passedTests);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.setNonStrokingColor(Color.RED);
        contentStream.newLineAtOffset(120, 640);
        contentStream.showText("FAILED: " + GlobalReport.failedTests);
        contentStream.endText();
    }

    // -----------------------------------------------------------
    // GUARDAR PDF FINAL
    // -----------------------------------------------------------
    public void saveFinalPDF() throws IOException {
        closeAndSummarize();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "target/evidencias/Reporte_Ejecucion_" + timestamp + ".pdf";

        contentStream.close();
        document.save(fileName);
        document.close();

        System.out.println("📘 PDF generado correctamente: " + fileName);
    }
}

