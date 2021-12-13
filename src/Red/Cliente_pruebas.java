package Red;

import java.io.*;
import java.net.Socket;

public class Cliente_pruebas extends Thread {

    Socket sk ;
    PrintWriter writer;
    String dato;
    BufferedReader reader;


    Runnable lector = new Runnable() {
        @Override
        public void run() {
            String msg = "";
            while (true)
            {
                try {
                   msg =  reader.readLine();
                   System.out.println("llego el broadcast");
                   System.out.println(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            dato = reader.readLine();

            reader.close();
            writer.close();
            int puerto = Integer.parseInt(dato);

            sk = new Socket("localhost",puerto);
            System.out.println("Cliente________ esperando a iniciar partida");

            reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            writer = new PrintWriter(new PrintStream(sk.getOutputStream()));

            reader.readLine();
            System.out.println("Cliente________"+ this.getName() + " Comienza la partida!!!!!!");


            new Thread(lector).start();

            while (true)
            {
                /**
                 * cada toque debera ejecutar este codigo de escribir
                 * sera el botón.................
                 */
                Thread.currentThread().sleep(1000);
                System.out.println("saliendo del sleep");
                writer.println("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
