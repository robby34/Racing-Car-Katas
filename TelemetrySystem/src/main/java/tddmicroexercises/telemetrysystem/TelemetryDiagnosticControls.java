package tddmicroexercises.telemetrysystem;

public class TelemetryDiagnosticControls {
    private final String DiagnosticChannelConnectionString = "*111#";

    private final ITelemetryClient telemetryClient;
    private String diagnosticInfo = "";

    public TelemetryDiagnosticControls(ITelemetryClient telemetryClient) {
        this.telemetryClient = telemetryClient;
    }

    public String getDiagnosticInfo() {
        return diagnosticInfo;
    }

    public void setDiagnosticInfo(String diagnosticInfo) {
        this.diagnosticInfo = diagnosticInfo;
    }

    public void checkTransmission() throws Exception {
        diagnosticInfo = "";

        telemetryClient.disconnect();

        int retryLeft = 3;
        while (telemetryClient.getOnlineStatus() == false && retryLeft > 0) {
            telemetryClient.connect(DiagnosticChannelConnectionString);
            retryLeft -= 1;
        }

        if (telemetryClient.getOnlineStatus() == false) {
            throw new Exception("Unable to connect.");
        }

        telemetryClient.send(ITelemetryClient.DIAGNOSTIC_MESSAGE);
        diagnosticInfo = telemetryClient.receive();
    }
}
