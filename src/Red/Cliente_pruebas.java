package Red;

import java.io.*;
import java.net.Socket;

public class Cliente_pruebas extends Thread {

    Socket sk ;
    PrintWriter writer;
    String msg;
    BufferedReader reader;


    Runnable receptor_pantalla = new Runnable() {
        @Override
        public void run() {
            String msg ;
            while (true)
            {
                try {
                    System.out.println("_____Cliente______ entrando en lectura para pantalla");
                   msg =  reader.readLine();
                   System.out.println(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable envio_evento = new Runnable() {
        @Override
        public void run() {
            System.out.println("____________Cliente___________saliendo del sleep");
            writer.write("");
        }
    };






    public Cliente_pruebas()
    {
        this.start();
    }

    @Override
    public void run() {
        super.run();

        try {

            sk = new Socket("localhost",1568);
            writer = new PrintWriter(new PrintStream(sk.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            System.out.println("Conectando a la partida.....");
            msg = reader.readLine();

            reader.close();
            writer.close();

            int puerto = Integer.parseInt(msg);

            sk = new Socket("localhost",puerto);
            System.out.println("Cliente________ esperando a iniciar partida");

            reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            writer = new PrintWriter(new PrintStream(sk.getOutputStream()));

            System.out.println("Cliente________"+ this.getName() + " Comienza la partida!!!!!!");

            new Thread(receptor_pantalla).start();

            while (true)
            {
                /**
                 * cada toque debera ejecutar este codigo de escribir
                 * sera el botón.................
                 */
                sleep(1000);

                new Thread(envio_evento).start();

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
