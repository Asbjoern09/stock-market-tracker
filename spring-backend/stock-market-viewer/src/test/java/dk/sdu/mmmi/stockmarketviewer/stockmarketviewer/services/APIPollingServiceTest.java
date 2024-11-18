package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class APIPollingServiceTest {

    private APIPollingService apiPollingService;

    @Before
    public void setUp() {
        apiPollingService = new APIPollingService(null, null, null);
    }

    @Test
    public void testCalculateMid() {
        double a = 10.0;
        double b = 20.0;
        double expectedMid = 15.0;
        assertEquals(expectedMid, apiPollingService.calculateMid(a, b), 0.001);

        a = 15.0;
        b = 15.0;
        expectedMid = 15.0;
        assertEquals(expectedMid, apiPollingService.calculateMid(a, b), 0.001);

        a = 0.0;
        b = 10.0;
        expectedMid = 5.0;
        assertEquals(expectedMid, apiPollingService.calculateMid(a, b), 0.001);

        a = -10.0;
        b = -20.0;
        expectedMid = -15.0;
        assertEquals(expectedMid, apiPollingService.calculateMid(a, b), 0.001);

        a = -10.0;
        b = 10.0;
        expectedMid = 0.0;
        assertEquals(expectedMid, apiPollingService.calculateMid(a, b), 0.001);
    }
}
