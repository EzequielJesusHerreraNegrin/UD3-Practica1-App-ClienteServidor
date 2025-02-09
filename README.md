# Casino Server Documentation

## Overview

This document describes the server-side and client-side implementation of the online casino application. The server consists of a main executable class `CrupierApp` and a thread handler class `PlayerHandler`. The client consists of `PlayerApp` and `CrupierListener`. The server listens for player connections, handles bets, and processes roulette game logic, while the client interacts with the server and places bets.

---

## **1. CrupierApp (Main Server Class)**

### **Package:** `com.casinoSalesiano.server`

### **Description:**

The `CrupierApp` class serves as the main entry point for the server. It initializes a `ServerSocket` and continuously waits for new player connections. Once a player connects, it spawns a new `PlayerHandler` thread to manage the player's interaction with the casino.

### **Key Functionalities:**

- Creates a server socket on a predefined port (`Constants.SERVER_PORT`).
- Listens for player connections.
- Handles incoming player data (name and wallet amount).
- Starts a new thread (`PlayerHandler`) for each player.

### **Code Snippet:**

```java
public class CrupierApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
        try {
            System.out.println("--- CASINO OPEN ---");
            while (true) {
                System.out.println("Waiting for players...");
                Socket playerSocket = serverSocket.accept();

                DataInputStream playerInputStream = new DataInputStream(
                        new BufferedInputStream(playerSocket.getInputStream()));
                DataOutputStream playerOutputStream = new DataOutputStream(
                        new BufferedOutputStream(playerSocket.getOutputStream()));

                String name = playerInputStream.readUTF();
                int wallet = Integer.parseInt(playerInputStream.readUTF());

                System.out.println("Player joined: " + name + " with " + wallet + " euros.");
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
```

---

## **2. PlayerHandler (Thread for Player Management)**

### **Package:** `com.casinoSalesiano.server.threads`

### **Description:**

The `PlayerHandler` class extends `Thread` and is responsible for handling the interaction between the server and an individual player. It processes bets, spins the roulette, and manages the player's wallet.

### **Key Functionalities:**

- Reads and validates player bets.
- Processes roulette results.
- Sends feedback to the player about game outcomes.
- Manages the player's wallet balance.
- Ends the session if the player runs out of money.

### **Class Fields:**

| Field           | Type               | Description                              |
| --------------- | ------------------ | ---------------------------------------- |
| `playerInput`   | `DataInputStream`  | Reads player input data                  |
| `crupierOutput` | `DataOutputStream` | Sends messages to the player             |
| `name`          | `String`           | Player's name                            |
| `wallet`        | `int`              | Player's current balance                 |
| `currentBet`    | `String`           | Stores the last bet placed by the player |

### **Methods:**

#### `public void crupierOutput(String text) throws IOException`

- Sends a message to the player.

#### `public String handleBet(String bet)`

- Validates the format and amount of the player's bet.
- Returns a confirmation or error message.

#### `public String spinRoulette()`

- Generates a random roulette result (number and color).

#### `public void handleResult(String currentBet, String rouletteResult, StringBuilder builder) throws IOException`

- Compares the player's bet with the roulette result.
- Updates the player's wallet accordingly.
- Sends the outcome to the player.

#### `public void run()`

- Main game loop for handling player interactions.
- Requests bets, processes the game, and determines the outcome.

---

## **3. PlayerApp (Client Main Class)**

### **Package:** `com.casinoSalesiano.client`

### **Description:**

The `PlayerApp` class is the main entry point for the client. It connects to the server, sends player information (name and wallet amount), and starts a listening thread (`CrupierListener`) to handle server responses.

### **Key Functionalities:**

- Connects to the server.
- Sends player name and wallet amount.
- Starts a listening thread to interact with the game.

---

## **4. CrupierListener (Client Thread)**

### **Package:** `com.casinoSalesiano.client.threads`

### **Description:**

The `CrupierListener` class extends `Thread` and continuously listens for messages from the server. It handles game interactions, processes bets, and waits for results.

### **Key Functionalities:**

- Reads and displays server messages.
- Sends player responses to the server.
- Handles game interactions and bet placement.

---

## **5. Utility Classes**

### **Package:** `com.casinoSalesiano.utils`

#### **Constants**

This class defines key constants used throughout the casino application, including:

- `SERVER_PORT`: The port where the server listens for connections.
- `RED_NUMBERS`: An array of red numbers on the roulette wheel.
- `frenchRoulette()`: Returns a formatted string representation of a French roulette board with color highlights.

#### **ConsoleUtils**

Provides utility methods for console interactions:

- `pressEnterToContinue(Scanner scanner)`: Pauses execution until the user presses enter.
- `crupierDisplayOutPust(CrupierDisplayOutPuts displayOutPut)`: Returns predefined messages for various game stages.

#### **CrupierDisplayOutPuts (Enum)**

Defines possible messages displayed by the casino's dealer:

- Welcome message
- Game rules
- Bet prompts and results
- Winning and losing messages
- Game continuation prompts

---

## **Conclusion**

This documentation provides an overview of both the server-side and client-side implementation of the casino application. The system ensures structured game flow with clear bet validation and result processing.
