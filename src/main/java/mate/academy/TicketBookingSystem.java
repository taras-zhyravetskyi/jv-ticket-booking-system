package mate.academy;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketBookingSystem {
    private Semaphore semaphore;
    private AtomicInteger counter;

    public TicketBookingSystem(int totalSeats) {
       semaphore = new Semaphore(totalSeats);
       counter = new AtomicInteger(totalSeats);
    }

    public BookingResult attemptBooking(String user) {
        BookingResult bookingResult;
        try {
            semaphore.tryAcquire();
            bookingResult = new BookingResult(user, true, "Booking successful.");
            semaphore.release(counter.decrementAndGet());
        } catch (RuntimeException e) {
            return new BookingResult (user, false, "No seats available.");
        }
        return bookingResult;
    }
}
