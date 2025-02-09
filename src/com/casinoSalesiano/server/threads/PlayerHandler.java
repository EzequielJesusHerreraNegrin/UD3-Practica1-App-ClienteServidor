package com.casinoSalesiano.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;

import com.casinoSalesiano.utils.ConsoleUtils;
import com.casinoSalesiano.utils.Constants;
import com.casinoSalesiano.utils.displayoutpust.CrupierDisplayOutPuts;

public class PlayerHandler extends Thread {

    private DataInputStream playerInput;
    private DataOutputStream crupierOutput;
    private String name;
    private int wallet;
    private String currentBet = "";

    public PlayerHandler(DataInputStream playerInput, String name, DataOutputStream playerOutputs, int wallet) {
        this.playerInput = playerInput;
        this.name = name;
        this.crupierOutput = playerOutputs;
        this.wallet = wallet;
    }

    public void crupierOutput(String text) throws IOException {
        crupierOutput.writeUTF(text);
        crupierOutput.flush();
    }

    public String handleBet(String bet) {
        int amount;
        String crupierResponse = "";
        String[] deconstructedBet;
        bet = bet.trim();
        System.out.println("Apuesta de " + name + ": |" + bet + "|");

        if (bet.contains(".")) {
            deconstructedBet = bet.split("\\.", -1);

            if (deconstructedBet.length == 3) {

                switch (deconstructedBet[0]) {
                    case "N":

                        if (deconstructedBet[1].matches("\\d+")) {
                            int boarNumber = Integer.parseInt(deconstructedBet[1]);

                            if (boarNumber >= 0 && boarNumber <= 36) {

                                if (deconstructedBet[2].matches("\\d+")) {
                                    amount = Integer.parseInt(deconstructedBet[2]);

                                    if (amount > 0 && amount <= wallet) {

                                        crupierResponse = "\ncrupier: has apostado " + amount
                                                + " euros al número "
                                                + boarNumber;

                                        currentBet = bet;
                                    } else {
                                        crupierResponse = "\ncrupier: ERROR, parece que apuestas mas de lo que tienes.\n"
                                                +
                                                "Cartera: " + wallet + " euros.";
                                    }
                                } else {
                                    crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                            "crupier: despues de '[Tablero].' introduzca un numero.";
                                }
                            } else {
                                crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                        "crupier: debe ser entre 1 y 36.";
                            }
                        } else {
                            crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                    "crupier: despues de 'N.' introduzca un numero.";
                        }
                        break;
                    case "C":

                        if (deconstructedBet[1].matches("[R|B]")) {

                            if (deconstructedBet[2].matches("\\d+")) {

                                amount = Integer.parseInt(deconstructedBet[2]);
                                if (amount > 0 && amount <= wallet) {

                                    String color = deconstructedBet[1].equals("deconstructedBet") ? "rojo" : "blanco";
                                    crupierResponse = "\ncrupier: has apostado " + amount + " euros al color " + color;
                                    currentBet = bet;
                                } else {
                                    crupierResponse = "\ncrupier: ERROR, parece que apuestas mas de lo que tienes.\n" +
                                            "Cartera: " + wallet + " euros.";
                                }
                            } else {
                                crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                        "crupier: despues de '[Tablero].' introduzca un numero.";
                            }
                        } else {
                            crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                    "crupier: despues de 'C.' introduzca 'R' o 'B'.";
                        }
                        break;

                    default:
                        crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                                "crupier: elija entre 'N' o 'C'.";
                        break;
                }
            } else {
                crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo.\n" +
                        "crupier: debe incluir los 3 campos.";
            }
        } else if (bet.isBlank()) {
            crupierResponse = "\ncrupier: Apuesta nula.";
        } else {
            crupierResponse = "\ncrupier: ERROR, la entrada no sigue el ejemplo\n" +
                    "crupier: separe los 3 campos por '.'";
        }

        return crupierResponse;
    }

    public String spinRuolette() {
        String color = "BLANCO";
        int winnerNumber = (int) Math.floor(Math.random() * (36 - 1 + 1) + (1));

        for (int redNumber : Constants.RED_NUMBERS) {
            if (redNumber == winnerNumber) {
                color = "ROJO";
            }
        }

        return winnerNumber + " " + color;
    }

    public void handleResult(String currentBet, String rouletteResult, StringBuilder builder) throws IOException {

        if (!currentBet.isBlank()) {
            String[] deconstructedBet;
            currentBet = currentBet.trim();
            deconstructedBet = currentBet.split("\\.", -1);
            String[] deconstructedRouletteResult = rouletteResult.split("\\s+");
            String rouletteNumber = deconstructedRouletteResult[0];
            String rouletteColor = deconstructedRouletteResult[1];
            String betTipe = deconstructedBet[0];
            String betBoardNumber = deconstructedBet[1];
            char betColor = deconstructedBet[1].toCharArray()[0];
            String betAmount = deconstructedBet[2];

            int parsedWinAmount;

            if (betTipe.equals("N")) {

                parsedWinAmount = Integer.parseInt(betAmount) * 2;
                if (betBoardNumber.equals(rouletteNumber)) {

                    builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.winner));
                    builder.append("\nCartera: " + wallet + " + " + parsedWinAmount);
                    wallet += parsedWinAmount;
                    builder.append("\nCartera: " + wallet);
                    crupierOutput(builder.toString());
                    builder.setLength(0);

                } else {
                    builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.looser));
                    builder.append("\nCartera: " + wallet + " - " + betAmount);
                    wallet -= Integer.parseInt(betAmount);
                    builder.append("\nCartera: " + wallet);
                    crupierOutput(builder.toString());
                    builder.setLength(0);
                }
            } else if (betTipe.equals("C")) {
                parsedWinAmount = (int) Math.floor(Integer.parseInt(betAmount) * 1.5);

                if (betColor == rouletteColor.charAt(0)) {
                    builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.winner));
                    builder.append("\nCartera: " + wallet + " + " + parsedWinAmount);
                    wallet += (int) Math.floor(Integer.parseInt(betAmount) * 1.5);
                    builder.append("\nCartera: " + wallet);
                    crupierOutput(builder.toString());
                    builder.setLength(0);
                } else {
                    builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.looser));
                    builder.append("\nCartera: " + wallet + " - " + betAmount);
                    wallet -= Integer.parseInt(betAmount);
                    builder.append("\nCartera: " + wallet);
                    crupierOutput(builder.toString());
                    builder.setLength(0);
                }
            }
        }
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.welcome));
            while (true) {
                builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.rules));
                builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.waitForplayer));
                crupierOutput(builder.toString());
                builder.setLength(0);
                ;

                playerInput.readUTF();
                builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.handStarts));
                builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.betExample));
                builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.initBets));
                builder.append("\nCartera: " + wallet + " euros.");
                crupierOutput(builder.toString());
                crupierOutput(Constants.frenchRoulette());
                builder.setLength(0);
                ;

                String stillInBet = "ERROR";
                while (stillInBet.equals("ERROR")) {
                    String checkedBet = handleBet(playerInput.readUTF());
                    stillInBet = checkedBet.substring(10, 15);
                    crupierOutput(checkedBet);
                }

                crupierOutput(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.closeBets));

                String rouletteResult = spinRuolette();
                crupierOutput(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.waitForRulette) + "..");

                crupierOutput(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.number) + rouletteResult);

                handleResult(currentBet, rouletteResult, builder);

                if (wallet == 0) {
                    builder.append(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.noMoney));
                    builder.append("\ntrue");
                    crupierOutput(builder.toString());
                    builder.delete(0, builder.length());
                } else {
                    crupierOutput(ConsoleUtils.crupierDisplayOutPust(CrupierDisplayOutPuts.stillInGame));
                    System.out.println("El cliente " + name + ": " + playerInput.readUTF());
                }

            }
        } catch (SocketException e) {
            System.out.println("Conexión cerrada con el cliente " + name + ".");
        } catch (IOException e) {
            System.out.println("Input output exception.");
        }
        super.run();
    }
}
