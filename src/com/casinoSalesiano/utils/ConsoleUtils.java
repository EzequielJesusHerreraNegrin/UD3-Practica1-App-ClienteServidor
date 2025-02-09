package com.casinoSalesiano.utils;

import java.util.Scanner;

import com.casinoSalesiano.utils.displayoutpust.CrupierDisplayOutPuts;

public class ConsoleUtils {

    public static void pressEnterToContinute(Scanner scanner) {
        System.out.println("\nPresione enter para continuar ");
        scanner.nextLine();
    }

    //
    public static String crupierDisplayOutPust(CrupierDisplayOutPuts displayOutPut) {
        String output = "";
        switch (displayOutPut) {
            case rules ->
                output = """
                        \n\n******************* REGLAS DEL JUEGO *******************
                        - "No va mas": se acabó el tiempo de apuesta.
                        - "Hagan sus apuestas": momento de apostar.
                        - Apuetas:
                            - Apuetas una cantidad al NUMERO que desees
                                entre 1 y 36 o COLOR rojo o blanco.
                            - numero: recibes un x2 sobre tu apuesta
                            - color: recibes un x1'5 sobre tu apuesta
                        - Ganar:
                            - Acertar el número o el color.
                        - Peder:
                            - Pierde la cantidad apostada.
                            - Sin dinero no puede continuar.
                        """;

            case welcome ->
                output = "\n******************* BIENVENIDO AL CASINO *******************";
            case handStarts ->
                output = "\n******************* EMPIEZA LA TIRADA *******************";

            case initBets ->
                output = "\ncrupier: HAGAN SUS APUESTAS.";

            case closeBets ->
                output = "\ncrupier: No va más";

            case waitingPlayers ->
                output = "\ncrupier: Esperando jugadores....";
            case waitForRulette ->
                output = "\ncrupier: Espera al resultado de la ruleta.";

            case winner ->
                output = "\n******************* GANASTES *******************";

            case looser ->
                output = "\n******************* MALA SUERTE *******************";
            case number ->
                output = "\ncrupier: Y el numero ganador es: ";
            case noMoney ->
                output = "\ncrupier: Te has quedado sin dinero.\n" +
                        "crupier: Debes abandonar la sala.";

            case stillInGame ->
                output = "\ncrupier: ¿Desea jugar otra tirada? (LogOut)";
            case waitForplayer ->
                output = "\ncrupier: Presione enter para continuar ";
            case betExample ->
                output = "\ncrupier: ej de consola. >[Tipo].[Tablero].[Dinero]\n" +
                        "\ncrupier: al numero es >N .\n" +
                        "\ncrupier: al color es >C .\n" +
                        "\ncrupier: color rojo es >R .\n" +
                        "\ncrupier: color blanco es >B .\n" +
                        "\ncrupier: ej numero >N .\n"

                ;
        }

        return output;
    }
}
