package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor TCP que recibe un número por línea y responde su cuadrado.
 * <p>
 * Protocolo simple de texto:
 * <ul>
 *   <li>Enviar un número (entero o decimal) y el servidor responde el cuadrado.</li>
 *   <li>Para terminar la sesión enviar: {@code "Adiós."}, {@code "Adios."}, {@code "bye"} o {@code "exit"}.</li>
 * </ul>
 * Puerto por defecto: {@code 35000}. Puede pasarse un puerto como primer argumento.
 * </p>
 *
 * @author Diego Cardenas
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class SquareServer {

    /**
     * Punto de entrada de la aplicación. Inicia un {@link java.net.ServerSocket}
     * en el puerto indicado (por defecto 35000), acepta una conexión y procesa
     * líneas de texto calculando el cuadrado de los números recibidos hasta que el
     * cliente envíe una palabra de salida.
     *
     * @param args si se especifica, {@code args[0]} es el puerto TCP a usar
     */
    public static void main(String[] args) {
        int port = 35000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("SquareServer escuchando en el puerto " + port + "…");

            // Acepta un cliente (como el ejemplo EchoServer). Para múltiples clientes,
            // encapsular en un bucle y/o usar hilos.
            try (
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                System.out.println("Cliente conectado desde " + clientSocket.getRemoteSocketAddress());
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String trimmed = inputLine.trim();
                    System.out.println("Recibido: " + trimmed);

                    if (trimmed.equalsIgnoreCase("adiós.") || trimmed.equalsIgnoreCase("adios.")
                        || trimmed.equalsIgnoreCase("bye") || trimmed.equalsIgnoreCase("exit")) {
                        out.println("Hasta luego.");
                        break;
                    }

                    if (trimmed.isEmpty()) {
                        out.println("ERROR: ingrese un número");
                        continue;
                    }

                    try {
                        double n = Double.parseDouble(trimmed);
                        double square = n * n;

                        // Formateo sencillo: sin decimales si es entero, de lo contrario en decimal.
                        String result;
                        if (Math.floor(square) == square && Math.abs(square) < Long.MAX_VALUE) {
                            result = String.valueOf((long) square);
                        } else {
                            result = String.valueOf(square);
                        }
                        out.println(result);
                    } catch (NumberFormatException e) {
                        out.println("ERROR: '" + trimmed + "' no es un número válido");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
