package com.zhaw.crime_cases;

import com.zhaw.crime_cases.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrimeCasesApplication implements CommandLineRunner {
    @Autowired
    private DataImportService dataImportService;

    public static void main(String[] args) {
        SpringApplication.run(CrimeCasesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataImportService.importCsv("src/main/resources/data/defendant.csv");
        dataImportService.importIndictmentCsv("src/main/resources/data/indictment.csv");
        dataImportService.importCrimeCsv("src/main/resources/data/crime.csv");
        dataImportService.importSingleCsv("src/main/resources/data/single.csv");
        dataImportService.importMultipleCsv("src/main/resources/data/multiple.csv");
    }
}
