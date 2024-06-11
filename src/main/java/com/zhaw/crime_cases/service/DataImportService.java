package com.zhaw.crime_cases.service;

import com.zhaw.crime_cases.entity.Defendant;
import com.zhaw.crime_cases.entity.Crime;
import com.zhaw.crime_cases.entity.Indictment;
import com.zhaw.crime_cases.entity.Multiple;
import com.zhaw.crime_cases.entity.Single;
import com.zhaw.crime_cases.repository.DefendantRepository;
import com.zhaw.crime_cases.repository.CrimeRepository;
import com.zhaw.crime_cases.repository.IndictmentRepository;
import com.zhaw.crime_cases.repository.MultipleRepository;
import com.zhaw.crime_cases.repository.SingleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class DataImportService {
    @Autowired
    private DefendantRepository defendantRepository;

    @Autowired
    private CrimeRepository crimeRepository;

    @Autowired
    private IndictmentRepository indictmentRepository;

    @Autowired
    private MultipleRepository multipleRepository;

    @Autowired
    private SingleRepository singleRepository;

    public void importCsv(String filePath) {
        if (defendantRepository.count() == 0) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Defendant defendant = new Defendant();
                    defendant.setName(data[1].trim());
                    defendant.setAge(Integer.parseInt(data[2].trim()));
                    defendantRepository.save(defendant);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Defendant data already exists in the database.");
        }
    }

    public void importIndictmentCsv(String filePath) {
        if (indictmentRepository.count() == 0) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Indictment indictment = new Indictment();
                    indictment.setIndictmentDate(LocalDate.parse(data[1].trim()));
                    indictment.setDefendantId(Long.parseLong(data[2].trim()));
                    indictment.setCrimeId(Long.parseLong(data[3].trim()));
                    indictmentRepository.save(indictment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Indictment data already exists in the database.");
        }
    }

    public void importCrimeCsv(String filePath) {
        if (crimeRepository.count() == 0) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Crime crime = new Crime();
                    crime.setCrimeType(data[1].trim().replace("'", ""));
                    crime.setCrimeDate(LocalDate.parse(data[2].trim()));
                    crimeRepository.save(crime);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Crime data already exists in the database.");
        }
    }

    public void importSingleCsv(String filePath) {
        if (singleRepository.count() == 0) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Single single = new Single();
                    single.setSeverity(data[1].trim().replace("'", ""));
                    single.setLocation(data[2].trim());
                    single.setCrimeDate(LocalDate.parse(data[3].trim()));
                    singleRepository.save(single);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Single data already exists in the database.");
        }
    }

    public void importMultipleCsv(String filePath) {
        if (multipleRepository.count() == 0) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    Multiple multiple = new Multiple();
                    multiple.setSeverity(data[1].trim().replace("'", ""));
                    multiple.setNumberOfIncidents(Integer.parseInt(data[2].trim()));
                    multiple.setCrimeDate(LocalDate.parse(data[3].trim()));
                    multipleRepository.save(multiple);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Multiple data already exists in the database.");
        }
    }
}
