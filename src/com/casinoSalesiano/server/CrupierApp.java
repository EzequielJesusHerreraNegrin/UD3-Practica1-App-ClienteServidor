package com.casinoSalesiano.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.casinoSalesiano.server.threads.PlayerHandler;
import com.casinoSalesiano.utils.Constants;

public class CrupierApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
        try {
            Socket playerSocket;

            System.out.println("--- CASINO ABIERTO ---");

            while (true) {
                System.out.println("Esperando jugadores....");
                playerSocket = serverSocket.accept();

                DataInputStream playerInputStream = new DataInputStream(
                        new BufferedInputStream(playerSocket.getInputStream()));

                DataOutputStream playerOutputStream = new DataOutputStream(
                        new BufferedOutputStream(playerSocket.getOutputStream()));

                String name = playerInputStream.readUTF();

                System.out.println("Ha entrado un cliente: " + name);

                int wallet = Integer.parseInt(playerInputStream.readUTF());

                System.out.println("El cliente " + name + " a resgitrado " + wallet + "euros.");
                System.out.println("El cliente " + name + " a empezado una partida.");
                PlayerHandler crupier = new PlayerHandler(playerInputStream, name, playerOutputStream, wallet);
                crupier.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();

        }

    }
}
