package org.example.BA.solution;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileGenerator {
    public static void generateCsvFile(List<ProfitRecord> schedule,
                                       String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Line #,Reward Date,Investment Amount,Reward Amount,Total Reward Amount Till that date, Staking Reward Rate\n");
            for (ProfitRecord month : schedule) {
                writer.write(String.format("%d,%s,%.6f,%.6f,%.6f,%.2f%%\n",
                        month.lineNumber(),
                        month.rewardDate(),
                        month.investmentAmount(),
                        month.rewardAmount(),
                        month.totalRewardAmount(),
                        month.yearlyStakingRewardRate() * 100));
            }
            writer.write("\n");
            System.out.println("CSV file generated successfully.");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Something goes wrong with IO!");
        }
    }
}
