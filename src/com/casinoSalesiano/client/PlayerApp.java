package com.casinoSalesiano.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.casinoSalesiano.client.threads.CrupierListenner;
import com.casinoSalesiano.utils.Constants;

public class PlayerApp {
    public static void main(String[] args) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", Constants.SERVER_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            System.out.println("Introduce tu nombre de usuario:");
            String name = scanner.nextLine();
            outputStream.writeUTF(name);
            outputStream.flush();

            System.out.println("Introduce una cantidad de dinero:");
            String wallet = scanner.nextLine();
            outputStream.writeUTF(wallet);
            outputStream.flush();

            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            CrupierListenner crupierListennerThread = new CrupierListenner(inputStream, outputStream);
            crupierListennerThread.start();
            crupierListennerThread.join();

        } catch (IOException e) {

        } finally {

        }

    }
}
