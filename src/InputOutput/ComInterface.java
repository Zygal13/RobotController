package InputOutput;

import Logic.Main;

public abstract class ComInterface {
    /**
     * Connects to a device
     *
     * @param name, ip or port
     * @return true if connected
     */
    abstract boolean connect(String name);

    /**
     * Sends data to the device
     *
     * @param data to send
     */
    abstract void send(String data);

    /**
     * Checks if the connection is open
     *
     * @return true if connected
     */
    abstract boolean isConnected();

    /**
     * Closes the connection
     */
    abstract void close();
}
