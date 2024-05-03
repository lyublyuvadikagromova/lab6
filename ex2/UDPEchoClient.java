import java.net.*;

public class UDPEchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);


            String message = "Hello from client!";
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);
            socket.send(packet);
            System.out.println("Message sent to server: " + message);


            byte[] receivedBuffer = new byte[BUFFER_SIZE];
            DatagramPacket receivedPacket = new DatagramPacket(receivedBuffer, receivedBuffer.length);
            socket.receive(receivedPacket);
            String echoedMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received from server: " + echoedMessage);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}