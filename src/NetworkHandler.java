import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.awt.geom.Point2D;

public class NetworkHandler implements BallListener{

    public static final int BROADCAST_PORT = 6666;
    public static final int TCP_PORT = 6667;
    InetAddress group = InetAddress.getByName("224.6.6.6");
	private GameState state;
    private BallListener ballListener;
    MulticastSocket mcs;

	public NetworkHandler(GameState state, BallListener ballListener) throws IOException {
		this.state = state;
        this.ballListener = ballListener;


        ServerSocket ss = new ServerSocket();
        mcs = new MulticastSocket(BROADCAST_PORT);
        mcs.joinGroup(group);
    }

    public void broadcast()throws IOException{
        byte[] buf = new byte[256];
        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, group, BROADCAST_PORT);
        mcs.send(packet);
    }

    public void listenForBroadcast()throws IOException{
        byte[] buf = new byte[256];

        DatagramPacket packet;

            packet = new DatagramPacket(buf, buf.length);
            mcs.receive(packet);

            String received = new String(packet.getData());
            System.out.println(received + "has joined.");


        mcs.leaveGroup(group);
        mcs.close();
    }
    public void listensFortTCP()throws IOException {
        byte[] buf = new byte[256];

         MulticastSocket tcp;
        tcp = new MulticastSocket(TCP_PORT );
        ServerSocket tcpsocketListne = new ServerSocket(TCP_PORT);
        Socket tcpConecetion = tcpsocketListne.accept();



    }
    public void shutdown() {

    }

    // BallListener implementation

    public void ballSentIntoMotion(Ball b, float speed, float angle) {

    }
    public void ballRelocated(Ball b, Point2D p) {

    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

    }


}
