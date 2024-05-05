package org.example.BA.solution;

import java.time.LocalDate;
import java.util.Scanner;

public class InputData {
    private double initialInvestment;
    private double yearlyRate;
    private LocalDate startDate;
    private int stakingDurationInMonths;
    private int rewardDay;
    private boolean reinvestRewards;
    private boolean additionalDataRequest;
    private LocalDate adjustedDate;
    private double adjustedYearlyRate;


    public static InputData getInputDataFromUser() {
        Scanner scanner = new Scanner(System.in);
        InputData inputData = new InputData();

        System.out.println("Enter initial investment amount of ETH: ");
        inputData.setInitialInvestment(scanner.nextDouble());

        System.out.println("Enter yearly staking reward rate in %: ");
        inputData.setYearlyRate(scanner.nextDouble() / 100);

        System.out.println("Enter staking start date (YYYY-MM-DD): ");
        inputData.setStartDate(LocalDate.parse(scanner.next()));

        System.out.println("Enter staking duration in months: ");
        inputData.setStakingDurationInMonths(scanner.nextInt());

        System.out.println("Enter reward payment day: ");
        inputData.setRewardDay(scanner.nextInt());

        System.out.println("Do you want to reinvest staking rewards? (yes/no): ");
        inputData.setReinvestRewards(scanner.next().equalsIgnoreCase("yes"));


        System.out.println("Do you want to provide additional data (start date and yearly rate)? (yes/no): ");
        inputData.setAdditionalDataRequested(scanner.next().equalsIgnoreCase("yes"));

        if (inputData.isAdditionalDataRequested()) {
            System.out.println("Enter additional date (YYYY-MM-DD): ");
            inputData.setAdjustedDate(LocalDate.parse(scanner.next()));

            System.out.println("Enter additional yearly staking reward rate in %: ");
            inputData.setAdjustedYearlyRate(scanner.nextDouble() / 100);
        }

        scanner.close();

        return inputData;
    }

    public double getAdjustedYearlyRate() {
        return adjustedYearlyRate;
    }

    public void setAdjustedYearlyRate(double adjustedYearlyRate) {
        this.adjustedYearlyRate = adjustedYearlyRate;
    }

    public void setAdjustedDate(LocalDate adjustedDate) {
        this.adjustedDate = adjustedDate;
    }

    public LocalDate getAdjustedDate() {
        return adjustedDate;
    }

    public boolean isAdditionalDataRequested() {
        return additionalDataRequest;
    }

    public void setAdditionalDataRequested(boolean additionalDataRequest) {
        this.additionalDataRequest=additionalDataRequest;
    }

    public double getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(double initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public double getYearlyRate() {
        return yearlyRate;
    }

    public void setYearlyRate(double yearlyRate) {
        this.yearlyRate = yearlyRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getStakingDurationInMonths() {
        return stakingDurationInMonths;
    }

    public void setStakingDurationInMonths(int stakingDurationInMonths) {
        this.stakingDurationInMonths = stakingDurationInMonths;
    }

    public int getRewardDay() {
        return rewardDay;
    }

    public void setRewardDay(int rewardDay) {
        this.rewardDay = rewardDay;
    }

    public boolean isReinvestRewards() {
        return reinvestRewards;
    }

    public void setReinvestRewards(boolean reinvestRewards) {
        this.reinvestRewards = reinvestRewards;
    }
}

