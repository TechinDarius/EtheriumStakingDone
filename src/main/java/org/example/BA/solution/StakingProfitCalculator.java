package org.example.BA.solution;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StakingProfitCalculator {
    public static void run() {
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
        CsvFileGenerator.generateCsvFile(profitSchedule,
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
            double monthlyReward = calculateMonthlyReward(
                    initialInvestment,
                    yearlyRate,
                    adjustedYearlyRate,
                    adjustedDate,
                    nextRewardDate,
                    daysBetween);

            yearlyRate = getActualYearlyRate(
                    nextRewardDate,
                    adjustedDate,
                    yearlyRate,
                    adjustedYearlyRate);

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
            }
//            else {
//                investment = initialInvestment;
//            }
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

    public static double calculateTotalReward(List<ProfitRecord> schedule) {
        double totalReward = 0.0;
        for (ProfitRecord record : schedule) {
            totalReward += record.rewardAmount();
        }
        return totalReward;
    }

    public static double calculateMonthlyReward(double initialInvestment,
                                                double yearlyRate,
                                                double adjustedYearlyRate,

                                                LocalDate adjustedDate,
                                                LocalDate nextRewardDate,
                                                int daysBetween) {
        double monthlyReward;

        if (adjustedDate != null && nextRewardDate.isAfter(adjustedDate)) {
            int daysBetweenOldAndNewDate = Math.toIntExact(ChronoUnit.DAYS.between(adjustedDate, nextRewardDate));
            monthlyReward = daysBetweenOldAndNewDate * calculateDailyReward(initialInvestment, adjustedYearlyRate)
                    + (daysBetween - daysBetweenOldAndNewDate) * calculateDailyReward(initialInvestment, yearlyRate);
        } else {
            monthlyReward = daysBetween * calculateDailyReward(initialInvestment, yearlyRate);
        }
        return monthlyReward;
    }

    public static double getActualYearlyRate(LocalDate nextRewardDate, LocalDate adjustedDate, double yearlyRate, double adjustedYearlyRate) {
        if (adjustedDate != null && nextRewardDate.isAfter(adjustedDate)) {
            yearlyRate = adjustedYearlyRate;
            return yearlyRate;
        }
        return yearlyRate;
    }

}

