package com.company.AdivinaUDP.Tasca1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorUDP {
    DatagramSocket socket;

    //Instàciar el socket
    public void init(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        while(true) {
            DatagramPacket packet = new DatagramPacket(receivingData,1024);
            socket.receive(packet);
            sendingData = processData(packet.getData(),packet.getLength());
            //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
            clientIP = packet.getAddress();
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData,sendingData.length,clientIP,clientPort);
            socket.send(packet);
        }
    }

    //El server retorna al client el mateix missatge que li arriba però en majúscules
    private byte[] processData(byte[] data, int lenght) {
        String msg = new String(data,0,lenght);
        msg = msg.toUpperCase();
        //Imprimir el missatge rebut i retornar-lo
        System.out.println(msg);
        return msg.getBytes();
    }

    public static void main(String[] args) {
        ServidorUDP server = new ServidorUDP();
        try {
            server.init(5555);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}