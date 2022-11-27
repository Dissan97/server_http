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
    public HttpRequest parserHttpRequest(InputStream inputStream) throws HttpParsingException {

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeaders(reader, request);
        parseBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {

        StringBuilder processingDataBuffer = new StringBuilder();

        // Differentiate each item in the debugger we are looking at
        boolean methodParser = false;
        boolean requestTargetParser = false;

        int byteRead;

        while ((byteRead = reader.read()) >= 0){
            if (byteRead == CR){
                byteRead = reader.read();
                if (byteRead == LF){
                    LOGGER.debug("Request Line VERSION to process: {}", processingDataBuffer.toString());
                    if(!methodParser || !requestTargetParser) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (byteRead == SP){
                //TODO Process previous data

                if (!methodParser){

                    LOGGER.debug("Request Line METHOD to process: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParser = true;
                } else if (!requestTargetParser) {

                    LOGGER.debug("Request Line REQUEST TARGET to process: {}", processingDataBuffer.toString());
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParser = true;
                } else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }

                processingDataBuffer.delete(0, processingDataBuffer.length());
            }else {
                processingDataBuffer.append((char) byteRead);

                if(!methodParser){
                    if(processingDataBuffer.length() > HttpMethod.MAX_LENGTH){
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader inputStreamReader, HttpRequest request) {

    }

    private void parseBody(InputStreamReader inputStreamReader, HttpRequest request) {

    }



}