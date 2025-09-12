package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket echSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo obtener E/S para la conexión: " + e.getMessage());
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        echSocket.close();
    }
}
