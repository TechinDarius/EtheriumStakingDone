# Staking Profit Calculator

This Java program calculates the profit schedule for staking cryptocurrency based on user input.

## How to Use

1. Run the `Main` class.
2. Follow the prompts to enter your staking parameters.
3. The program will generate a CSV file with the profit schedule.

## Input Data

- Initial investment amount of ETH
- Yearly staking reward rate in %
- Staking start date (YYYY-MM-DD)
- Staking duration in months
- Reward payment day
- Whether to reinvest staking rewards

## Additional Data (Optional)

If you choose to provide additional data:
- Adjusted start date (YYYY-MM-DD)
- Adjusted yearly staking reward rate in %

## CSV File Format

The generated CSV file contains the following columns:
1. Line #
2. Reward Date
3. Investment Amount
4. Reward Amount
5. Total Reward Amount Till that date
6. Staking Reward Rate

## Dependencies

- Java 8 or higher
