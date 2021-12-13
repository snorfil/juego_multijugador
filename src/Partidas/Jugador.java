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
            out.println("");
            out.println("");

            while (true)
            {
                System.out.println("____DEBUG____ Partidas.Jugador :"+ jugador + " empezando recepccion de eventos de cliente");
                in.readLine();
                System.out.println("____DEBUG____ Partidas.Jugador :"+ jugador + " recibido");
                inter.broadcast(jugador);
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
