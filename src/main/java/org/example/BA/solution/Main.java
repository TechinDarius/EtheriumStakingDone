package org.example.BA.solution;

public class Main {
    public static void main(String[] args) {
//        System.out.println("\tLets run our staking calculator \n");
        System.out.println("""
                  ______     ___       __        ______  __    __   __          ___   .___________.  ______   .______     \s
                 /      |   /   \\     |  |      /      ||  |  |  | |  |        /   \\  |           | /  __  \\  |   _  \\    \s
                |  ,----'  /  ^  \\    |  |     |  ,----'|  |  |  | |  |       /  ^  \\ `---|  |----`|  |  |  | |  |_)  |   \s
                |  |      /  /_\\  \\   |  |     |  |     |  |  |  | |  |      /  /_\\  \\    |  |     |  |  |  | |      /    \s
                |  `----./  _____  \\  |  `----.|  `----.|  `--'  | |  `----./  _____  \\   |  |     |  `--'  | |  |\\  \\----.
                 \\______/__/     \\__\\ |_______| \\______| \\______/  |_______/__/     \\__\\  |__|      \\______/  | _| `._____|
                                                                                                                         \s""");
        System.out.println("\tPlease enter your data below:");
        StakingProfitCalculator.run();
    }
}