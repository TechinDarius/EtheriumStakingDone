package org.example.BA.solution;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StakingProfitCalculator {
    public static void main(String[] args) {
        InputData inputData = InputData.getInputDataFromUser();

        double initialInvestment = inputData.getInitialInvestment();
        double yearlyRate = inputData.getYearlyRate();
        LocalDate startDate = inputData.getStartDate();
        LocalDate adjustedDate = inputData.getAdjustedDate();
        double adjustedYearlyRate = inputData.getAdjustedYearlyRate();
        int stakingDurationInMonths = inputData.getStakingDurationInMonths();
        int rewardDay = inputData.getRewardDay();
        boolean reinvestRewards = inputData.isReinvestRewards();

        List<ProfitRecord> profitSchedule = calculateProfitSchedule(
                initialInvestment,
                yearlyRate,
                adjustedYearlyRate,
                startDate,
                adjustedDate,
                stakingDurationInMonths,
                rewardDay,
                reinvestRewards);
        generateCsvFile(profitSchedule,
                "ETH_profit_schedule.csv");
    }

    public static List<ProfitRecord> calculateProfitSchedule(double initialInvestment,
                                                             double yearlyRate,
                                                             double adjustedYearlyRate,
                                                             LocalDate startDate,
                                                             LocalDate adjustedDate,
                                                             int stakingDurationInMonths,
                                                             int rewardDay,
                                                             boolean reinvestRewards) {
        List<ProfitRecord> schedule = new ArrayList<>();
        LocalDate nextRewardDate = startDate.withDayOfMonth(rewardDay);
        double investment = initialInvestment;
        LocalDate endDate = nextRewardDate;
        int daysBetween = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

        for (int line = 1; line <= stakingDurationInMonths; line++) {

            startDate = nextRewardDate;
            double monthlyReward;

            if (adjustedDate != null && nextRewardDate.isAfter(adjustedDate)) {
                int daysBetweenOldAndNewDate = Math.toIntExact(ChronoUnit.DAYS.between(adjustedDate, nextRewardDate));
                monthlyReward = daysBetweenOldAndNewDate * calculateDailyReward(initialInvestment, adjustedYearlyRate)
                        + (daysBetween - daysBetweenOldAndNewDate) * calculateDailyReward(initialInvestment, yearlyRate);
                yearlyRate = adjustedYearlyRate;
            } else {
                monthlyReward = daysBetween * calculateDailyReward(initialInvestment, yearlyRate);
            }

            double totalReward = calculateTotalReward(schedule);
            schedule.add(new ProfitRecord(
                    line,
                    nextRewardDate,
                    investment,
                    monthlyReward,
                    totalReward,
                    yearlyRate));

            nextRewardDate = getNextRewardDate(nextRewardDate);
            daysBetween = Math.toIntExact(ChronoUnit.DAYS.between(startDate, nextRewardDate));
            if (reinvestRewards) {
                investment += monthlyReward;
            } else {
                investment = initialInvestment;
            }
        }
        return schedule;
    }

    public static double calculateDailyReward(double investment, double yearlyRate) {
        int oneYear = 365;
        return investment * (yearlyRate / oneYear);
    }

    public static LocalDate getNextRewardDate(LocalDate currentRewardDate) {
        int addOneMonth = 1;
        return currentRewardDate.plusMonths(addOneMonth);
    }

    public static LocalDate getEndRewardDate(LocalDate currentRewardDate, int stakingDurationInMonths) {
        return currentRewardDate.plusMonths(stakingDurationInMonths);
    }

    public static double calculateTotalReward(List<ProfitRecord> schedule) {
        double totalReward = 0.0;
        for (ProfitRecord record : schedule) {
            totalReward += record.rewardAmount();
        }
        return totalReward;
    }

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
            System.out.println("Something goes wrong with IO!");//Gal cia kazka dar galima bus pasikorteguot
        }
    }
}

