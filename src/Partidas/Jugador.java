package Partidas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Jugador extends Thread {

    public PrintWriter out;
    private BufferedReader in;
    private comunicacion inter;
    private int num_jugador;

    Runnable escuchador = new Runnable() {
        @Override
        public void run() {
            while (true) {
                System.out.println("_____Jugador_____ :" + num_jugador + " empezando recepccion de eventos de cliente");
                try {
                    in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inter.broadcast(num_jugador);
            }
        }
    };

    public Jugador(PrintWriter writer, BufferedReader reader, comunicacion comunication, int player) throws IOException {
        inter = comunication;
        out = writer;
        in = reader;
        num_jugador = player;
    }

    public void destroy() {
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        new Thread(escuchador).start();
    }
}
