package ic.doc.streaming;

import ic.doc.Clock;
import ic.doc.User;

import java.time.LocalTime;

public class StreamTracker {
    private final User user;
    private final LocalTime timestamp;

    public StreamTracker(User user) {
        this.user = user;
        this.timestamp = LocalTime.now();
    }

    public StreamTracker(User user, Clock clock) {
        this.user = user;
        this.timestamp = clock.getTime();
    }

    public LocalTime startTime() {
        return timestamp;
    }

    public User user() {
        return user;
    }
}
