import java.io.*;
import java.net.*;

public class TextConferenceServer {
    private static final String MULTICAST_ADDRESS = "230.0.0.0";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group);

            System.out.println("Text Conference Server started on port " + PORT);

            Thread senderThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    while (true) {
                        String message = reader.readLine();
                        byte[] buffer = message.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            senderThread.start();

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from client: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
