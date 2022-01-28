package com.company.AdivinaUDP.Tasca2;

import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class ClientUDP {
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;
    Scanner sc;
    int enter;
    int intents=0;

    public ClientUDP() {
        sc = new Scanner(System.in);
    }

    public void init(String host, int port) throws SocketException, UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte[] receivedData = new byte[1024];
        byte[] sendingData;

        sendingData = getFirstRequest();
        while (mustContinue(sendingData)) {
            DatagramPacket packet = new DatagramPacket(sendingData, sendingData.length, serverIP, serverPort);
            socket.send(packet);
            packet = new DatagramPacket(receivedData, 1024);
            socket.receive(packet);
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }

    }

    //Resta de conversa que se li envia al server
    private byte[] getDataToRequest(byte[] data, int length) {
        intents++;
        int rebut = ByteBuffer.wrap(data).getInt();
        if (rebut == 0) {
            System.out.println("Correcte!  | " + intents + " intents");
            intents = 0;
            System.out.println("Torna a començar! \n" +
                    "Escriu un enter:");
        }
        if (rebut == 1) System.out.println("Més petit  | " + intents + " intents");
        if (rebut == 2) System.out.println("Més gran  | " + intents + " intents");

        enter = sc.nextInt();
        byte[] resposta = ByteBuffer.allocate(4).putInt(enter).array();
        return resposta;
    }

    //primer missatge que se li envia al server
    private byte[] getFirstRequest() {
        System.out.println("Hola avui jugarem, adivinar un numero comença tu.");
        System.out.println("Escriu un numero: ");
        enter = sc.nextInt();
        return ByteBuffer.allocate(4).putInt(enter).array();
    }

    //Si se li diu adeu al server el client es desconnecta
    private boolean mustContinue(byte[] data) {
        String msg = new String(data).toLowerCase();
        return !msg.equals("adeu");
    }

    public static void main(String[] args) {
        ClientUDP client = new ClientUDP();
        try {
            client.init("localhost", 5555);
            client.runClient();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

}
