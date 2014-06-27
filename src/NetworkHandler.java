import java.io.*;
import java.io.IOException;
import java.net.*;
import java.awt.geom.Point2D;
import java.util.Enumeration;
import java.util.Scanner;

public class NetworkHandler implements BallListener{

    public static final int BROADCAST_PORT = 6666;
    public static final int TCP_PORT = 6667;
    InetAddress group = InetAddress.getByName("224.6.6.6");

	private GameState state;
    private BallListener ballListener;
    MulticastSocket mcs;
    PrintWriter out;
    ServerSocket serverSocket;



    public NetworkHandler(GameState state, BallListener ballListener) throws IOException {
		this.state = state;
        this.ballListener = ballListener;

        ServerSocket ss = new ServerSocket();
        mcs = new MulticastSocket(BROADCAST_PORT);
        mcs.joinGroup(group);


        // Start thread to listen for broadcasts
        Thread broadcastListener = new Thread(new Runnable(){
            public void run() {
                try {
                    listenForBroadcast();
                } catch (IOException e) {
                    System.err.println("Error listening for broadcasts:" + e.getMessage());
                }
            }
        });

        // Start thread to listen for TCP connections
        serverSocket = openFirstAvailablePort(TCP_PORT);
        System.out.println("Listening on " +serverSocket.getLocalPort());

        Thread TCPListener = new Thread(new Runnable() {

            public void run() {
                try{
                    listensFortTCP();
                }catch (IOException e){
                    System.err.println("Error listening for broadcasts:" + e.getMessage());
                }
            }
        });

        broadcastListener.start();
        TCPListener.start();
        broadcast();


    }

    public void broadcast()throws IOException{
        System.out.println("Sending broadcast");
        byte[] buf;
        buf = intToBytes(serverSocket.getLocalPort());
        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, group, BROADCAST_PORT);
        mcs.send(packet);
    }
    public byte[] intToBytes(int my_int) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeInt(my_int);
        out.close();
        byte[] int_bytes = bos.toByteArray();
        bos.close();
        return int_bytes;
    }
    public int bytesToInt(byte[] int_bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(int_bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        int my_int = ois.readInt();
        ois.close();
        return my_int;
    }
    public void listenForBroadcast()throws IOException{
        byte[] buf = new byte[10];

        DatagramPacket packet;

        packet = new DatagramPacket(buf, buf.length);

        System.out.println("Listening for broadcast on port " + mcs.getLocalPort());
        mcs.receive(packet);
        int remotePort = bytesToInt(buf);
        System.out.println("Received broadcast... ");

        if (state.isInGame()){
            System.out.println("... but I'm in game.");
            return;
        } else if (remotePort == serverSocket.getLocalPort()){
            System.out.println("... but it's from me.");
            System.out.println("... remote port in packet: " + remotePort);
            System.out.println("... my local port: " + serverSocket.getLocalPort());
            return;
        }

        sendTCP(packet.getAddress());
        String received = packet.getAddress().toString();
        System.out.println(received + " has joined.");
        System.out.println("No longer listening for broadcasts");
        mcs.leaveGroup(group);
        mcs.close();
    }

    private boolean isLocalHost(InetAddress address) {

        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    if (enumIpAddr.equals(address)){
                        return true;
                    }

                }
            }
        } catch (SocketException e) {

        }
        return false;
    }

    public void listensFortTCP()throws IOException {


        Socket tcpConecetion = serverSocket.accept();
        state.setConnected(true);


        Scanner s = new Scanner(tcpConecetion.getInputStream());

        if(s.next().contentEquals("THROW")){
            int ballnumber = s.nextInt();
            float speed = s.nextFloat();
            float angle = s.nextFloat();

            Ball ball = state.getBall(ballnumber);
            ball.setSpeed(speed);
            ball.setAngle(angle);


        }
        if(s.next().contentEquals("RELOCATED")){
            int ballnumber = s.nextInt();
            float x = s.nextFloat();
            float y = s.nextFloat();

            Ball ball = state.getBall(ballnumber);
            ball.setLocation(new Point2D.Float(x,y));
        }

        System.out.println("No longer listening for TCP connections");
    }
    public void shutdown() {


    }

    public void sendTCP(InetAddress destination)throws IOException{
        Socket tCPSend = new Socket(destination, TCP_PORT);

        out = new PrintWriter(tCPSend.getOutputStream(),true);

    }

    private ServerSocket openFirstAvailablePort(int startAtPort){
        int p = startAtPort;
        do{
            try{
                return new ServerSocket(p);

            }catch(IOException ex){
                System.err.println("Port already in use: " + p + "; trying another...");
                p++;
            }
        }while(p <= 65535);
        throw new RuntimeException("Could not open ServerSocket, ran out of ports");
    }

    // BallListener implementation

    public void ballSentIntoMotion(Ball b, float speed, float angle) {
        System.out.println("Sending THROW");
        out.println("THROW");
        out.println(b.getNumber());
        out.println(speed);
        out.println(angle);
    }
    public void ballRelocated(Ball b, Point2D p) {
        System.out.println("Sending RELOCATED");
        out.println("RELOCATED");
        out.println(b.getNumber());
        out.println(p.getX());
        out.println(p.getY());
    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

    }

	@Override
	public void ballCollidedWithWall(Ball b, float speed, float angle) {

	}


}

