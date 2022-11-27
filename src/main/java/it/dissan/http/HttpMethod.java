package it.dissan.http;

public enum HttpMethod {
    GET, HEAD;
    public static final int MAX_LENGTH;

    //We are going to iterate all the name of methods.length with a temporary variable and compare them and set max val

    static {
        int tempMaxLength = -1;

        for (HttpMethod method: values()){
            if (method.name().length() > tempMaxLength){
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }




}
