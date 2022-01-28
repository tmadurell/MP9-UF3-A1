package com.company.AdivinaUDP.Tasca1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUDP_Personal {
    String hostname;
    int port;
    InetAddress serverIP;
    DatagramSocket socket;
    Scanner sc;


    public ClientUDP_Personal() {
        sc = new Scanner(System.in);
    }

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        this.port = port;
        socket = new DatagramSocket();
    }

    public void run() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

        sendingData = getFirstRequest();
        while (close(sendingData)) {
            DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,serverIP, port);
            socket.send(packet);
            packet = new DatagramPacket(receivedData,1024);
            socket.receive(packet);
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }

    }

    //Primer missatge que hem donara el sevidor
    private byte[] getFirstRequest() {
        System.out.println("Hola! ");
        System.out.println("Introdueix el teu nom: ");
        hostname = sc.nextLine();
        System.out.println("Benvingut: " + hostname);
        System.out.print("Faig majúscula en tot lo que dius, per exemple el teu nom: ");
        return hostname.getBytes();
    }

    //Resposta que enviara en bucle el servidor despres de donar el meu nom
    private byte[] getDataToRequest(byte[] data, int length) {
        String msg = new String(data,0, length);
        //Imprimeix el nom del client + el que es reb del server i demana més dades
        System.out.println(msg);
        System.out.print("Vols que repeteixi alguna paraula amb majúscula? ");
        String resposta = sc.nextLine();
        return resposta.getBytes();
    }


    //Si al servidor li diem adeu, el nostre servidor desconnecta el client
    private boolean close(byte [] data) {
        String msg = new String(data).toLowerCase();
        //System.out.println("Okay, adeu " + hostname+ " fins un altre.");
        return !msg.equals("adeu");
    }

    public static void main(String[] args) {
        ClientUDP_Personal client = new ClientUDP_Personal();
        try {
            client.init("localhost",5555);
            client.run();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

}
