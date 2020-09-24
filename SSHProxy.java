import com.jcraft.jsch.ChannelDirectTCPIP;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxySSH implements Proxy {

    public ProxySSH(Session proxySession) {
        this.proxySession = proxySession;
    }

    private final Session proxySession;
    private ChannelDirectTCPIP channel;
    private InputStream inputStream;
    private OutputStream outputStream;


    public void connect(SocketFactory ignore, String host, int port, int timeout) throws Exception {
        channel = (ChannelDirectTCPIP) proxySession.openChannel("direct-tcpip");
        channel.setHost(host);
        channel.setPort(port);
        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();
        channel.connect();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getSocket() {
        return null;
    }

    public void close() {
        channel.disconnect();
    }

}