package Partidas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Partida extends Thread implements comunicacion {

    final int puerto;
    private boolean enable = true;
    Runnable msg_newServer = new Runnable() {
        @Override
        public void run() {

            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (PrintWriter i : out) {
                i.println(puerto);
                i.close();
            }

        }
    };

    private ArrayList<PrintWriter> out;
    private ArrayList<Jugador> jugadores;
    private Partida partida = this;
    int resultado = 0;
    private ServerSocket serverSocket;
    private int num_partida;


    public Partida(ArrayList<PrintWriter> salidas, int port, final int num) {
        num_partida = num;
        puerto = port;
        out = salidas;
        jugadores = new ArrayList<>();
        partida.start();
        System.out.println("Partida " + num_partida);
    }


    @Override
    public void run() {
        super.run();
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("enviando puerto a los clientes");

            int contador = 1;
            // Reconexion de los jugadores a la partida
            new Thread(msg_newServer).start();


            while (true) {
                Socket sk = serverSocket.accept();
                System.out.println("___Partida___ Aceptado jugador a partida....");

                BufferedReader reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                PrintWriter writer = new PrintWriter(sk.getOutputStream(), true);
                jugadores.add(new Jugador(writer, reader, partida, contador));

                contador++;

                if (jugadores.size() == 2) {
                    System.out.println("___Partida___ iniciando juego!!!!!");
                    for (Jugador i : jugadores) {
                        i.start();
                    }
                    for (PrintWriter i : out) {
                        i.write("ready");
                        i.write("3");
                        i.write("2");
                        i.write("1");
                        i.write("GO!!!!");
                    }
                    break;
                }
                // secuencia de incio de partida 3,2,1


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void broadcast(int jugador) {

                System.out.println("jugador : " + jugador);
                if (jugador == 1) {
                    resultado--;
                } else {
                    resultado++;
                }

                for (PrintWriter i : out) {
                    i.write("" + resultado);
                }
                System.out.println("" + resultado);
    }

    @Override
    public void destruir() {

        if (enable) {
            System.out.println("Destruyendo partida");
            enable = false;
            for (Jugador i : jugadores) {
                i.destroy();
                i.interrupt();
            }
            this.interrupt();
            System.out.println("DESTRUYENDO LA PARTIDA");
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

