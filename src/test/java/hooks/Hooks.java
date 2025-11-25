package hooks;

import io.cucumber.java.*;
import driver.DriverFactory;
import utils.GlobalReport;
import utils.StepListener;
import utils.evidenciaLimpia;
import utils.PdfReportUtil;

public class Hooks {

    private static boolean initialized = false;

    // INICIALIZACIÓN GLOBAL (primera ejecución)
    @Before
    public void setup(Scenario scenario) throws Exception {

        if (!initialized) {
        evidenciaLimpia.limpiarEvidence();
        initialized = true;
    }

        if (GlobalReport.report == null) {
            GlobalReport.report = new PdfReportUtil("REPORTE DE AUTOMATIZACIÓN");
        }

        DriverFactory.getDriver();

        // nueva sección en el PDF para este escenario
        GlobalReport.report.startScenario(scenario.getName());
    }

    // GUARDAR PASO Y CAPTURA
    @AfterStep
    public void afterStep(Scenario scenario) throws Exception {

        String stepText = StepListener.lastStepText;
        String status = scenario.isFailed() ? "FAILED" : "PASSED";

        if (stepText != null && !stepText.isEmpty()) {
            GlobalReport.report.addStep(stepText, status);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        GlobalReport.totalTests++;

        if (scenario.isFailed()) {
            GlobalReport.failedTests++;
        } else {
         GlobalReport.passedTests++;
        }
    }


    // FINALIZAR Y GENERAR UN SOLO PDF
    @AfterAll
    public static void finishExecution() throws Exception {
        if (GlobalReport.report != null) {
            GlobalReport.report.saveFinalPDF();
        }

        DriverFactory.quitDriver(); // cierra navegador al final del escenario
    }
}



