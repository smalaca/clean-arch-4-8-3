package com.smalaca.rentalapplication.domain.booking;

import com.smalaca.rentalapplication.domain.clock.Clock;
import com.smalaca.rentalapplication.domain.event.EventIdFactory;
import com.smalaca.rentalapplication.domain.eventchannel.EventChannel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.mock;

class BookingDomainServiceTest {
    private static final String RENTAL_PLACE_ID = "1234";
    private static final String TENANT_ID = "5678";
    private static final List<LocalDate> DAYS = asList(LocalDate.now(), LocalDate.now().plusDays(1));
    private static final List<Booking> NO_BOOKINGS_FOUND = emptyList();

    private final EventIdFactory eventIdFactory = mock(EventIdFactory.class);
    private final Clock clock = mock(Clock.class);
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final BookingDomainService service = new BookingDomainServiceFactory().create(eventIdFactory, clock, eventChannel);

    @Test
    void shouldAcceptBookingWhenNoOtherBookingsFound() {
        Booking booking = givenBooking();

        service.accept(booking, emptyList());

        BookingAssertion.assertThat(booking).isAccepted();
    }

    @Test
    void shouldPublishBookingAcceptedEventWhenBookingIsAccepted() {
        ArgumentCaptor<BookingAccepted> captor = ArgumentCaptor.forClass(BookingAccepted.class);

        service.accept(givenBooking(), NO_BOOKINGS_FOUND);

        BDDMockito.then(eventChannel).should().publish(captor.capture());
        BookingAccepted actual = captor.getValue();
        Assertions.assertThat(actual.getRentalType()).isEqualTo("HOTEL_ROOM");
        Assertions.assertThat(actual.getRentalPlaceId()).isEqualTo(RENTAL_PLACE_ID);
        Assertions.assertThat(actual.getTenantId()).isEqualTo(TENANT_ID);
        Assertions.assertThat(actual.getDays()).containsExactlyElementsOf(DAYS);
    }

    private Booking givenBooking() {
        return Booking.hotelRoom(RENTAL_PLACE_ID, TENANT_ID, DAYS);
    }
}