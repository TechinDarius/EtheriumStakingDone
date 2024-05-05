package org.example.BA.solution;

import java.time.LocalDate;

public record ProfitRecord(int lineNumber,
                           LocalDate rewardDate,
                           double investmentAmount,
                           double rewardAmount,
                           double totalRewardAmount,
                           double yearlyStakingRewardRate) { }




