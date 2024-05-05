import org.example.BA.solution.ProfitRecord;
import org.example.BA.solution.StakingProfitCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StakingProfitCalculatorTest {

    @Test
    void testCalculateMonthlyReward_WhenAdjustedDateIsNull() {
        double initialInvestment = 100.0;
        double yearlyRate = 0.12;
        double adjustedYearlyRate = 0.08;

        LocalDate nextRewardDate = LocalDate.of(2024, 4, 27);
        int daysBetween = 30;

        double monthlyReward = StakingProfitCalculator.calculateMonthlyReward(
                initialInvestment, yearlyRate, adjustedYearlyRate,
                null, nextRewardDate, daysBetween);

        double expectedReward = initialInvestment * yearlyRate / 365 * daysBetween;
        assertEquals(expectedReward, monthlyReward, 0.000001);
    }

    @Test
    void testCalculateMonthlyReward_WhenAdjustedDateIsAfterNextRewardDate() {
        double initialInvestment = 100.0;
        double yearlyRate = 0.12;
        double adjustedYearlyRate = 0.08;
        LocalDate adjustedDate = LocalDate.of(2024, 5, 1);
        LocalDate nextRewardDate = LocalDate.of(2024, 4, 27);
        int daysBetween = 30;

        double monthlyReward = StakingProfitCalculator.calculateMonthlyReward(
                initialInvestment, yearlyRate, adjustedYearlyRate,
                adjustedDate, nextRewardDate, daysBetween);

        double expectedReward = (daysBetween * calculateDailyReward(initialInvestment, yearlyRate));
        assertEquals(expectedReward, monthlyReward, 0.000001);
    }

    @Test
    void testCalculateMonthlyReward_WhenAdjustedDateIsBeforeNextRewardDate() {
        double initialInvestment = 100.0;
        double yearlyRate = 0.12;
        double adjustedYearlyRate = 0.08;
        LocalDate adjustedDate = LocalDate.of(2024, 4, 20);
        LocalDate nextRewardDate = LocalDate.of(2024, 4, 27);
        int daysBetween = 30;

        double monthlyReward = StakingProfitCalculator.calculateMonthlyReward(
                initialInvestment, yearlyRate, adjustedYearlyRate,
                adjustedDate, nextRewardDate, daysBetween);

        double expectedReward = ((daysBetween - 7) * calculateDailyReward(initialInvestment, yearlyRate)) +
                (7 * calculateDailyReward(initialInvestment, adjustedYearlyRate));
        assertEquals(expectedReward, monthlyReward, 0.000001);
    }

    private double calculateDailyReward(double investment, double yearlyRate) {
        int oneYear = 365;
        return investment * (yearlyRate / oneYear);
    }

    @Test
    void testCalculateMonthlyReward_whenAdjustedDate_isNotNull() {
        double initialInvestment = 25.0;
        double yearlyRate = 0.1;
        double adjustedYearlyRate = 0.08;
        LocalDate startDate = LocalDate.now();
        LocalDate nextRewardDate = startDate.plusMonths(1);
        int daysBetween = 20;

        double monthlyReward = StakingProfitCalculator.calculateMonthlyReward(initialInvestment,
                yearlyRate,
                adjustedYearlyRate,
                null,
                nextRewardDate,
                daysBetween);
        double expected = initialInvestment * yearlyRate / 365 * daysBetween;
        assertEquals(expected, monthlyReward, 0.000001);
    }

    @Test
    void testGetActualYearlyRate_WhenAdjustedDateIsNull() {
        LocalDate nextRewardDate = LocalDate.now().plusDays(30);
        double yearlyRate = 0.1;
        double adjustedYearlyRate = 0.08;

        double actualYearlyRate = StakingProfitCalculator.getActualYearlyRate(
                nextRewardDate,
                null,
                yearlyRate,
                adjustedYearlyRate);

        assertEquals(yearlyRate, actualYearlyRate);
    }

    @Test
    void testGetActualYearlyRate_WhenAdjustedDateIsAfterNextRewardDate() {
        LocalDate nextRewardDate = LocalDate.now().plusMonths(1);
        LocalDate adjustedDate = LocalDate.now().plusDays(65);
        double yearlyRate = 0.1;
        double adjustedYearlyRate = 0.08;

        double actualYearlyRate = StakingProfitCalculator.getActualYearlyRate(
                nextRewardDate,
                adjustedDate,
                yearlyRate,
                adjustedYearlyRate);

        assertEquals(yearlyRate, actualYearlyRate, 0.000001);
    }

    @Test
    void testGetActualYearlyRate_WhenAdjustedDateIsBeforeNextRewardDate() {
        LocalDate nextRewardDate = LocalDate.now().plusDays(30);
        LocalDate adjustedDate = LocalDate.now().plusDays(15);
        double yearlyRate = 0.1;
        double adjustedYearlyRate = 0.08;

        double actualYearlyRate = StakingProfitCalculator.getActualYearlyRate(
                nextRewardDate,
                adjustedDate,
                yearlyRate,
                adjustedYearlyRate);

        assertEquals(adjustedYearlyRate, actualYearlyRate, 0.000001);
    }

    @Test
    void testCalculateTotalReward_EmptyList() {
        double totalReward = StakingProfitCalculator.calculateTotalReward(new ArrayList<>());
        assertEquals(0.0, totalReward, 0.000001);
    }

    @Test
    void testCalculateTotalReward_NonEmptyList() {
        List<ProfitRecord> records = new ArrayList<>();
        records.add(new ProfitRecord(1, LocalDate.now(), 100.0, 10.0, 10.0, 0.1));
        records.add(new ProfitRecord(2, LocalDate.now(), 200.0, 20.0, 30.0, 0.1));
        records.add(new ProfitRecord(3, LocalDate.now(), 300.0, 30.0, 60.0, 0.1));

        double totalReward = StakingProfitCalculator.calculateTotalReward(records);
        assertEquals(10.0 + 20.0 + 30.0, totalReward, 0.000001);
    }

    @Test
    void testGetNextRewardDate() {
        LocalDate currentRewardDate = LocalDate.of(2024, 4, 1);
        LocalDate expectedNextRewardDate = LocalDate.of(2024, 5, 1);

        LocalDate nextRewardDate = StakingProfitCalculator.getNextRewardDate(currentRewardDate);

        assertEquals(expectedNextRewardDate, nextRewardDate);
    }

    @Test
    void testCalculateProfitSchedule() {
        double initialInvestment = 10.0;
        double yearlyRate = 0.1;
        double adjustedYearlyRate = 0;
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        int stakingDurationInMonths = 6;
        int rewardDay = 1;
        boolean reinvestRewards = false;

        List<ProfitRecord> profitSchedule = StakingProfitCalculator.calculateProfitSchedule(
                initialInvestment,
                yearlyRate,
                adjustedYearlyRate,
                startDate,
                null,
                stakingDurationInMonths,
                rewardDay,
                reinvestRewards);

        assertEquals(stakingDurationInMonths, profitSchedule.size());

        ProfitRecord firstRecord = profitSchedule.getFirst();

        assertEquals(1, firstRecord.lineNumber());
        assertEquals(startDate.withDayOfMonth(rewardDay), firstRecord.rewardDate());
        assertEquals(initialInvestment, firstRecord.investmentAmount());
        double expectedMonthlyReward = initialInvestment * Math.toIntExact(ChronoUnit.DAYS.between(startDate, firstRecord.rewardDate())) * yearlyRate / 365;

        assertEquals(expectedMonthlyReward, firstRecord.rewardAmount(), 0.000001);
        assertEquals(expectedMonthlyReward, firstRecord.totalRewardAmount(), 0.000001);

        ProfitRecord lastRecord = profitSchedule.getLast();
        assertEquals(stakingDurationInMonths, lastRecord.lineNumber());
        assertEquals(startDate.plusMonths(stakingDurationInMonths - 1).withDayOfMonth(rewardDay), lastRecord.rewardDate());

    }


}

