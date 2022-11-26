package it.dissan.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; //32 indicate the space in ascii
    private static final int CR = 0x0D; //13 indicate the CR in ascii
    private static final int LF = 0x0A; //10 indicate the Line field in ascii
    public HttpRequest parserHttpRequest(InputStream inputStream) {

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        int byteRead;

        while ((byteRead = reader.read()) >= 0){
            if (byteRead == CR){
                byteRead = reader.read();
                if (byteRead == LF){
                    return;
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader inputStreamReader, HttpRequest request) {

    }

    private void parseBody(InputStreamReader inputStreamReader, HttpRequest request) {

    }



}