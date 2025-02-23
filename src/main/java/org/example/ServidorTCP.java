package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {


    public static void main(String[] args) {
        //definimos un puerto
        int port = 1234;
        // creamos el servidor bajo el try
        try {
            //lo creamos con el constructor del puerto
            ServerSocket server = new ServerSocket(port);

            System.out.println("EL servidor esta escuchando en el puerto " + port);

            // bucle infinito para aceptar conexiones sin que se cierre
            while (true) {

                //abrir conexion con cliente
                Socket cliente = server.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());
                System.out.println("Cliente conectado en el puerto " + cliente.getPort());

                //recibir el mensaje del cliente, el dni
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());

                //leer el dni enviado por el cliente
                String dni = entrada.readUTF();
                System.out.println("Dni recibido: " + dni);

                //validar dni
                String respuesta = validarDNI(dni) ? "DNI VALIDO" : "DNI INVALIDO";
                System.out.println("Servidor dice: " + respuesta);

                DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
                salida.writeUTF(respuesta);

                //cerrar la conexion del cliente
                cliente.close();


            }

        } catch (IOException a) {
            System.out.println("Error: " + a.getMessage());

        }
    }
    private static boolean validarDNI(String dni) {
        //verificar el dni si teiene 9 caracteres
        if (dni.length() !=9) return false;

        //extrar los primero 8 caracteres que deben ser numeros
        String numeros = dni.substring(0,8);
        //expresion regular que verifica si todos son numeros
        if(!numeros.matches("\\d+")) return false;

        //extraer ultima letra dni
        char letraRecibida = dni.charAt(dni.length() - 1);

        //calcular letra correcta
        char letraCorrecta = calcularLetraDNI(numeros);
        return Character.toUpperCase(letraRecibida) == letraCorrecta;

    }

    private static char calcularLetraDNI(String subCadenaNumeros)
    {
        int miDNI = Integer.parseInt(subCadenaNumeros);
        int resto = 0;
        String asignarLetra = "TRWAGMYFPDXBNJZSQVHLCKE";
        resto = miDNI % 23;
        char miLetra = asignarLetra.charAt(resto);
        return miLetra;
    }
}
