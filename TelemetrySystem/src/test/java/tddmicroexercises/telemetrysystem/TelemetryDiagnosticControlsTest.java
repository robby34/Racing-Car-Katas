package tddmicroexercises.telemetrysystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TelemetryDiagnosticControlsTest {

    private TelemetryDiagnosticControls telemetryDiagnosticControls;

    @Mock
    ITelemetryClient mockedTelemetryClient;

    @Before
    public void setup() throws Exception {
        telemetryDiagnosticControls = new TelemetryDiagnosticControls(mockedTelemetryClient);
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
    public void testCheckTransmissionSuccess() throws Exception {
        // Given the mocked TelemetryClient responding false and then always true on 'getOnlineStatus'
        Mockito.when(mockedTelemetryClient.getOnlineStatus()).thenReturn(false).thenReturn(true);
        // And responding a fake diagnostic result on 'receive'
        Mockito.when(mockedTelemetryClient.receive()).thenReturn("fake diagnostic result");

        // When checkTransmission is called
        telemetryDiagnosticControls.checkTransmission();

        // Then it should call the TelemetryClient for 1 disconnection, 1 connection, and 3 status
        Mockito.verify(mockedTelemetryClient, Mockito.times(1)).disconnect();
        Mockito.verify(mockedTelemetryClient, Mockito.times(1)).connect("*111#");
        Mockito.verify(mockedTelemetryClient, Mockito.times(3)).getOnlineStatus();
        // Then it should call it for sending a diagnostic message
        Mockito.verify(mockedTelemetryClient, Mockito.times(1)).send("AT#UD");
        // Then it should call it to receive the diagnostic result message
        Mockito.verify(mockedTelemetryClient, Mockito.times(1)).receive();
        // Then it should have the diagnostic result message
        assertEquals("fake diagnostic result", telemetryDiagnosticControls.getDiagnosticInfo());
    }

    @Test
    public void testCheckTransmissionConnectionFailure() throws Exception {
        // Given the mocked TelemetryClient responding always false on 'getOnlineStatus'
        Mockito.when(mockedTelemetryClient.getOnlineStatus()).thenReturn(false);

        // When checkTransmission is called, an Exception is raised
        try {
            telemetryDiagnosticControls.checkTransmission();
            Assert.fail("Should have thrown Exception");
        } catch (Exception e) {
            assertEquals("Unable to connect.", e.getMessage());
        }

        // Then it should call the TelemetryClient for 1 disconnection, 3 connection attempts , and 5 status
        Mockito.verify(mockedTelemetryClient, Mockito.times(1)).disconnect();
        Mockito.verify(mockedTelemetryClient, Mockito.times(3)).connect("*111#");
        Mockito.verify(mockedTelemetryClient, Mockito.times(5)).getOnlineStatus();
        // Then it should never call 'send' and 'receive' methods
        Mockito.verify(mockedTelemetryClient, Mockito.never()).send(Mockito.anyString());
        Mockito.verify(mockedTelemetryClient, Mockito.never()).receive();
    }

}
