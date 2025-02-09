package com.casinoSalesiano.utils;

public class Constants {
    public static final int SERVER_PORT = 8082;

    public static final int[] RED_NUMBERS = {
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    public static String frenchRoulette() {
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        String frenchRoullete = """
                \n\n+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
                |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  |
                |-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----|
                | 13  | 14  | 15  | 16  | 17  | 18  | 19  | 20  | 21  | 22  | 23  | 24  |
                |-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----|
                | 25  | 26  | 27  | 28  | 29  | 30  | 31  | 32  | 33  | 34  | 35  | 36  |
                +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
                |  0  |
                +-----+
                """;
        frenchRoullete = frenchRoullete.replace("  1 ", RED + "  1 " + RESET)
                .replace("  3 ", RED + "  3 " + RESET)
                .replace("  5 ", RED + "  5 " + RESET)
                .replace("  7 ", RED + "  7 " + RESET)
                .replace("  9 ", RED + "  9 " + RESET)
                .replace(" 12 ", RED + " 12 " + RESET)
                .replace(" 14 ", RED + " 14 " + RESET)
                .replace(" 16 ", RED + " 16 " + RESET)
                .replace(" 18 ", RED + " 18 " + RESET)
                .replace(" 19 ", RED + " 19 " + RESET)
                .replace(" 21 ", RED + " 21 " + RESET)
                .replace(" 23 ", RED + " 23 " + RESET)
                .replace(" 25 ", RED + " 25 " + RESET)
                .replace(" 27 ", RED + " 27 " + RESET)
                .replace(" 30 ", RED + " 30 " + RESET)
                .replace(" 32 ", RED + " 32 " + RESET)
                .replace(" 34 ", RED + " 34 " + RESET)
                .replace(" 36 ", RED + " 36 " + RESET);
        return frenchRoullete;
    }

    public static void main(String[] args) {
        String output = "\ncrupier: Te has quedado sin dinero.\n" +
                "crupier: Debes abandonar la sala." + "\ntrue";
        System.out.println(output.substring(output.length() - 4));
    }

}