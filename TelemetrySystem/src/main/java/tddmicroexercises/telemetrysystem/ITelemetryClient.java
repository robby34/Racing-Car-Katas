package tddmicroexercises.telemetrysystem;

/**
 * Interface representing a telemetry client.
 */
public interface ITelemetryClient {

    String DIAGNOSTIC_MESSAGE = "AT#UD";

    /**
     * Establish a connection to the telemetry server identified by the given name.
     *
     * @param telemetryServerConnectionString the connection name of the telemetry server.
     */
    void connect(String telemetryServerConnectionString);

    /**
     * Close the connection with the telemetry server.
     */
    void disconnect();

    /**
     * Indicates if connected to the telemetry server.
     *
     * @return {@code true} if connected, {@code false} otherwise.
     */
    boolean getOnlineStatus();

    /**
     * Send the given message to the telemetry server.
     *
     * @param message the message to send.
     */
    void send(String message);

    /**
     * Get the received response following the previous message sending.
     *
     * @return the response to the message previously sent.
     */
    String receive();

}
