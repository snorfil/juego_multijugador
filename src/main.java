import Red.Cliente_pruebas;

import java.io.IOException;
import java.util.Scanner;

public class main {

    public static servidor server;

    // creador de clientes _____________________________________
    public static Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Scanner in = new Scanner(System.in);

            while (true)
            {
                System.out.println("Escribe alguna letra y pulse ENTER para crear un cliente........");
                System.out.println("..... creando" + in.nextLine());

                new Cliente_pruebas();
                System.out.println("Acabas de crear un cliente");
            }
        }
    };
    // _________________________________________________________


    public static void main(String[] args) {

        new Thread(runnable).start();

        try {
            server = new  servidor();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
