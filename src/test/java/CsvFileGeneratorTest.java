import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.BA.solution.CsvFileGenerator;
import org.example.BA.solution.ProfitRecord;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFileGeneratorTest {

    @Test
    void testGenerateCsvFile() {

        String filePath = "test.csv";

        List<ProfitRecord> testRecords = new ArrayList<>();
        testRecords.add(new ProfitRecord(1, LocalDate.now(), 100.0, 10.0, 10.0, 0.1));

        CsvFileGenerator.generateCsvFile(testRecords, filePath);

        File file = new File(filePath);
        assertTrue(file.exists());

        file.delete();
    }
}
