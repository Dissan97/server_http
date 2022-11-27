package it.dissan.http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;
    private String originalHttpVersion; //literal from the request
    private HttpVersion bestCompatibleVersion;

    HttpRequest(){    };

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    protected void setMethod(String methodName) throws HttpParsingException {
        //Need to iterate all the value to catch bad arguments
        for(HttpMethod httpMethod: HttpMethod.values()){
            if(methodName.equals(httpMethod.name())){
                    this.method = httpMethod;
                    return;
            }
        }

        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );

    }

    protected void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_ERROR);
        }

        this.requestTarget = requestTarget;
    }

    protected void setHttpVersion(String originalHttpVersion) {
        this.originalHttpVersion = originalHttpVersion;
    }
}
