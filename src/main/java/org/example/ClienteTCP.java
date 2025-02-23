package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {

        //definimos puerto
        int port = 1234;

        //creamos el socket para conectar al servidor
        try {
            Socket cliente = new Socket("localhost", port);

            //creamos el flujo de salida para enviar los datos
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

            //pedir al usuario que introduzca el dni
            Scanner sc = new Scanner(System.in);

            System.out.println("Introduce el DNI: ");
            String dni = sc.nextLine();

            // enviar el dni al servidor
            salida.writeUTF(dni);

            //recibir respuesta
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            String respuesta = entrada.readUTF();
            System.out.println("La respuesta del servidor es " + respuesta);


            //cerar la conexion con el servidor
            cliente.close();



        } catch (Exception e) {
            System.out.println("erro en el cliente " + e.getMessage());
        }


    }



}
