package it.dissan.config;

public class Configuration {

    private int port = 8000;
    private String webroot;

    /**
     * Getting port
     * @return port configured
     */
    public int getPort() {
        return port;
    }

    /**
     * setting port
     * @param port : int
     */

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Getting web root from configuration
     * @return : String
     */
    public String getWebroot() {
        return webroot;
    }

    /**
     * Setup webroot
     * @param webroot: String
     */

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
