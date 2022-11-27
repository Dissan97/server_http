package it.dissan.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void parserHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parserHttpRequest(
                    generateValidTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }

        assertNotNull(request);
        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getRequestTarget(), "/");
    }

    @Test
    void parserHttpRequestBadMethodOne() {
        try {
            HttpRequest request = httpParser.parserHttpRequest(
                    generateBadTestCaseMethodNameOne()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parserHttpRequestBadMethodTwo() {
        try {
            HttpRequest request = httpParser.parserHttpRequest(
                    generateBadTestCaseMethodNameTwo()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parserHttpRequestInvReqNumOfItemsOne() {
        try {
            HttpRequest request = httpParser.parserHttpRequest(
                    generateBadTestCaseRequestLineInvalidNumOfItems()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parserHttpRequestEmptyReqLine() {
        try {
            httpParser.parserHttpRequest(
                    generateBadTestCaseEmptyRequestLine()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parserHttpRequestCRnoLF() {
        try {
            httpParser.parserHttpRequest(
                    generateBadTestCaseRequestLineInvalidOnlyCRnoLF()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidTestCase(){
        String raw = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:106.0) Gecko/20100101 Firefox/106.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: username-localhost-8888=\"2|1:0|10:1668802749|23:username-localhost-8888|44:MjAzZGIyZTM0ZjVlNDQ3YmE0MWFmYzBhYzMzZjAwMzg=|ca9449c874e8ff013b384f491cadd719a3253627835261511afe458e74631c24\"; _xsrf=2|320993a4|5594423e1d38ae0fb516b020673f55dd|1668802699\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-User: ?1\r\n"+
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
                );

        return inputStream;

    }

    private InputStream generateBadTestCaseMethodNameOne(){
        String raw = "BadGET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
        );

        return inputStream;

    }

    private InputStream generateBadTestCaseMethodNameTwo(){
        String raw = "GEEETTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
        );

        return inputStream;

    }

    private InputStream generateBadTestCaseRequestLineInvalidNumOfItems(){
        String raw = "GET / IVALIDMETHOD HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
        );

        return inputStream;

    }

    private InputStream generateBadTestCaseEmptyRequestLine(){
        String raw = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
        );

        return inputStream;

    }

    private InputStream generateBadTestCaseRequestLineInvalidOnlyCRnoLF(){
        String raw = "GET / IVALIDMETHOD HTTP/1.1\r" + // <---- no LF
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n";

        InputStream inputStream =  new ByteArrayInputStream(
                raw.getBytes(StandardCharsets.UTF_8)
        );

        return inputStream;

    }
}