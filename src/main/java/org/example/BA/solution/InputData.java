package org.example.BA.solution;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        inputData.setInitialInvestment(readValidPositiveDoubleInput(scanner,
                "Enter initial investment amount of ETH: "));

        inputData.setYearlyRate(readValidPositiveDoubleInput(scanner,
                "Enter yearly staking reward rate in %: "));

        inputData.setStartDate(readValidDateInput(scanner,
                "Enter staking start date (YYYY-MM-DD): "));

        inputData.setStakingDurationInMonths(readValidPositiveIntegerInput(scanner,
                "Enter staking duration in months: "));

        inputData.setRewardDay(readValidRewardDayInput(scanner,
                "Enter reward payment day: "));

        inputData.setReinvestRewards(readBooleanDataInput(scanner,
                "Do you want to reinvest staking rewards? (yes/no): "));


        inputData.setAdditionalDataRequested(readBooleanDataInput(scanner,
                "Do you want to provide additional data (start date and yearly rate)? (yes/no): "));

        handleAdditionalDataInput(inputData,new Scanner(System.in));

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
    public static double readValidPositiveDoubleInput(Scanner scanner, String prompt) {
        double result = 0.0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                result = scanner.nextDouble();
                if (result > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("\tInvalid input! Please enter a positive number.");
                }
            } else {
                System.out.println("\tInvalid input! Please enter a valid number.");
                scanner.next();
            }
        }
        return result/100;
    }

    public static LocalDate readValidDateInput(Scanner scanner, String prompt) {
        LocalDate result = null;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);
            String input = scanner.next();
            try {
                result = LocalDate.parse(input);
                isValidInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("\tInvalid input! Please enter a valid date in the format YYYY-MM-DD.");
            }
        }
        return result;
    }
    public static int readValidPositiveIntegerInput(Scanner scanner, String prompt) {
        int result = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                if (result > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("\tInvalid input! Please enter a positive integer greater than 0.");
                }
            } else {
                System.out.println("\tInvalid input! Please enter a valid positive integer greater than 0.");
                scanner.next();
            }
        }
        return result;
    }
    public static int readValidRewardDayInput(Scanner scanner, String prompt) {
        int result = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                if (result > 0 && result < 32) {
                    isValidInput = true;
                    if (result > 28) {
                        System.out.println("\tNote: This day may differ for February and later months.");
                    }
                } else {
                    System.out.println("\tInvalid input! Please enter a positive integer between 1 and 31.");
                }
            } else {
                System.out.println("\tInvalid input! Please enter a valid positive integer between 1 and 31.");
                scanner.next();
            }
        }
        return result;
    }

    public static boolean readBooleanDataInput(Scanner scanner, String prompt) {
        boolean additionalDataRequested = false;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);
            String input = scanner.next().toLowerCase();
            if (input.equals("yes")) {
                additionalDataRequested = true;
                isValidInput = true;
            } else if (input.equals("no")) {
                isValidInput = true;
            } else {
                System.out.println("\tInvalid input! Please enter 'yes' or 'no'.");
            }
        }
        return additionalDataRequested;
    }

    public static void handleAdditionalDataInput(InputData inputData, Scanner scanner) {
        if (inputData.isAdditionalDataRequested()) {
            System.out.println("Enter additional date (YYYY-MM-DD): ");
            inputData.setAdjustedDate(readValidDateInput(scanner));

            System.out.println("Enter additional yearly staking reward rate in %: ");
            inputData.setAdjustedYearlyRate(readValidYearlyRateInput(scanner));
        }
    }

    private static LocalDate readValidDateInput(Scanner scanner) {
        boolean isValidInput = false;
        LocalDate date = null;

        while (!isValidInput) {
            try {
                date = LocalDate.parse(scanner.next());
                isValidInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("\tInvalid date format! Please enter the date in YYYY-MM-DD format.");
            }
        }
        return date;
    }

    private static double readValidYearlyRateInput(Scanner scanner) {
        boolean isValidInput = false;
        double yearlyRate = 0.0;

        while (!isValidInput) {
            System.out.println("\tEnter additional yearly staking reward rate in %: ");
            if (scanner.hasNextDouble()) {
                yearlyRate = scanner.nextDouble();
                if (yearlyRate > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("\tInvalid input! Yearly rate must be greater than 0.");
                }
            } else {
                System.out.println("\tInvalid input! Please enter a valid number.");
                scanner.next();
            }
        }
        return yearlyRate / 100;
    }


}

