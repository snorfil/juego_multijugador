package Partidas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Jugador extends Thread {

    private PrintWriter out;
    private BufferedReader in;
    private comunicacion inter;
    private int jugador ;


    public Jugador(PrintWriter writer , BufferedReader reader, comunicacion comunication, int player) throws IOException
    {
        inter       = comunication;
        out         = writer;
        in          = reader;
        jugador     = player;
    }
    public void destroy()
    {
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            // secuencia de incio de partida 3,2,1
            out.println("ready");
            out.println("3");
            out.println("2");
            out.println("1");
            out.println("GO!!!!");


            while (true)
            {
                System.out.println("_____Jugador_____ :"+ jugador + " empezando recepccion de eventos de cliente");
                in.readLine();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        inter.broadcast(jugador);
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
            out.close();
            inter.destruir();
            }
    }

    public void write(String resultado)
    {
        out.println(""+resultado);
        System.out.println("enviando al jugador el indicador");
    }
}
