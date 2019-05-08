package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.temporal.ChronoUnit;

import static edu.iis.mto.time.Order.State.SUBMITTED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class OrderTest {

    Order sut;
    Clock clockStub;

    @Before
    public void setUp() {
        clockStub = mock(Clock.class);
        sut = new Order(clockStub);
    }

    @Test(expected =  OrderExpiredException.class)
    public void shouldThrowExceptionAfterOrderExpiration() {
        doReturn(Clock.systemDefaultZone().instant().plus(1, ChronoUnit.DAYS)).when(clockStub).instant();

        sut.submit();
        sut.confirm();
    }

    @Test
    public void shouldConfirmationBeSuccessful() {
        doReturn(Clock.systemDefaultZone().instant()).when(clockStub).instant();

        sut.submit();
        sut.confirm();

        assertEquals(SUBMITTED, sut.getOrderState());
    }

}