package it.dissan.core;

import it.dissan.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Class (Thread) needed to compute requests from client while server is accepting
 * Requests
 */

public class HttpConnectionWorkerThread extends Thread{

    private final Socket socket;

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);


    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //TODO we would do reading


            String html = "<html>" +
                    "<head>" +
                    "<title>HELLO WORLD</title>" +
                    "</head>" +
                    "<body>" +
                    "<h1>HELLO WORLD! FROM JAVA</h1>" +
                    "</body>" +
                    "</html>";

                final String CRLF = "\n\r"; //Character Return Line Field in ascii(\n, \r) -> [13, 10]

                String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status Line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            //TODO we would do writing

            LOGGER.info("Closing connection handler...");


    } catch (IOException e) {//serverSocket.close(); //is not close
            throw new RuntimeException(e);
            //TODO handle later
        }finally {

            assert inputStream != null;
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("Problem with inputStream closing: ");
            }

            assert outputStream != null;
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("Problem with outputStream closing: ");
            }

            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Problem with communication: ");
            }


        }
    }
}
