package it.dissan.core;

import it.dissan.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private int port;
    private String webroot;

    private final ServerSocket serverSocket;

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    /**
     * Creating server socket which will handle our HTTP request from browser
     * @param port
     * @param webroot
     * @throws IOException
     */

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }



    @Override
    public void run() {

        try {

            while (serverSocket.isBound() && !serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                LOGGER.info("Got connection from: " + socket.getInetAddress() + ":" + socket.getPort());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting Socket: ", e);
            throw new RuntimeException(e);
        } finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    LOGGER.error("Problem with closing Socket: ", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
