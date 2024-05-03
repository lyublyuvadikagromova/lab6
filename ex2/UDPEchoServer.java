import java.net.*;

public class UDPEchoServer {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("UDP Echo Server started on port " + PORT);

            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from client: " + received);


                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                DatagramPacket echoPacket = new DatagramPacket(packet.getData(), packet.getLength(), clientAddress, clientPort);
                socket.send(echoPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}