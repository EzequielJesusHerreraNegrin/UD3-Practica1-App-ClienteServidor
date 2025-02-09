package com.casinoSalesiano.client.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CrupierListenner extends Thread {

    private DataInputStream crupierInput;
    private DataOutputStream playerOutput;
    private Scanner scanner;

    public CrupierListenner(DataInputStream inputStream, DataOutputStream outputStream) {
        this.crupierInput = inputStream;
        this.playerOutput = outputStream;
        this.scanner = new Scanner(System.in);
    }

    public void playerOutput(String text) throws IOException {
        playerOutput.writeUTF(text);
        playerOutput.flush();
    }

    public void handleLogOut(String input, Scanner scanner) {
        if (input.equals("s")) {

        }
    }

    @Override
    public void run() {
        while (true) {
            String bet = "";
            String playerText = "enter";
            boolean exit = false;

            try {
                while (!exit) {
                    System.out.println(crupierInput.readUTF());
                    scanner.nextLine();
                    playerOutput(playerText);

                    System.out.println(crupierInput.readUTF());
                    System.out.println(crupierInput.readUTF());
                    String stillInBet = "ERROR";

                    while (stillInBet.equals("ERROR")) {
                        System.out.println("\napuesta: ");
                        bet = scanner.nextLine();
                        playerOutput(bet);
                        String response = crupierInput.readUTF();
                        System.out.println(response);
                        stillInBet = response.substring(10, 15);
                    }
                    System.out.println(crupierInput.readUTF());

                    System.out.println(crupierInput.readUTF());

                    System.out.println(crupierInput.readUTF());

                    String crupier = crupierInput.readUTF();

                    if (crupier.endsWith("true")) {
                        System.out.println(crupier.substring(0, crupier.length() - 4));
                        System.exit(0);
                    } else {
                        System.out.println(crupier);
                        String input = scanner.nextLine();
                        System.out.println();
                        if (input.equals("LogOut")) {
                            playerOutput(input);
                            System.exit(0);
                        }
                    }

                }
                scanner.close();
                System.out.close();
            } catch (IOException e) {
                System.out.println("Problema recibiendo el mensaje.");
            }
        }
    }
}
