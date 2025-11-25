package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class evidenciaLimpia {
    
    public static void limpiarEvidence() {
        Path path = Paths.get("target/evidencias");

        try {
            if (Files.exists(path)) {
                Files.walk(path)
                        .map(Path::toFile)
                        .forEach(File::delete);
            }

            Files.createDirectories(path);

            System.out.println("Carpeta de evidencias limpiada correctamente.");
        } catch (IOException e) {
            System.err.println("Error limpiando carpeta de evidencias: " + e.getMessage());
        }
    }
}
