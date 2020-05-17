package tddmicroexercises.telemetrysystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TelemetryDiagnosticControlsTest {

    private TelemetryDiagnosticControls telemetryDiagnosticControls;

    @Before
    public void setup() {
        telemetryDiagnosticControls = new TelemetryDiagnosticControls();
    }

    @Test
    public void testGetDiagnosticInfo() {
        // When ask the diagnostic info at the initial state of TelemetryDiagnosticControls
        String result = telemetryDiagnosticControls.getDiagnosticInfo();

        // Then the diagnostic info value is empty by default
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetDiagnosticInfo() {
        // Given the initial state of TelemetryDiagnosticControls where diagnostic info is empty
        final String DIAG_INFO = "new diagnostic info for test";
        assertTrue(telemetryDiagnosticControls.getDiagnosticInfo().isEmpty());

        // When set a specific diagnostic info
        telemetryDiagnosticControls.setDiagnosticInfo(DIAG_INFO);

        // Then TelemetryDiagnosticControls should have this specific diagnostic info
        assertEquals(DIAG_INFO, telemetryDiagnosticControls.getDiagnosticInfo());
    }

    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response() {
    }

}
