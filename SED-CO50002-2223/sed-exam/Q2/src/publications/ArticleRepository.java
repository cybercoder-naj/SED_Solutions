package publications;

import java.util.*;

public class ArticleRepository {
    private final Summariser summariser;
    private final Map<Topic, List<Subscriber>> subscribers;

    public ArticleRepository(Summariser summariser) {
        this.summariser = summariser;
        this.subscribers = new HashMap<>();
    }

    public void publish(Article article) {
        Topic[] keywords = summariser.summarise(article);

        Set<Subscriber> alerted = new HashSet<>();
        for (Topic topic : keywords) {
            List<Subscriber> interestedSubscribers = subscribers.getOrDefault(topic, new ArrayList<>());
            for (Subscriber subscriber : interestedSubscribers) {
                if (!alerted.contains(subscriber))
                    subscriber.alert(article);
                alerted.add(subscriber);
            }
        }
    }

    public void subscribeToTopic(Subscriber subscriber, Topic topic) {
        if (!subscribers.containsKey(topic))
            subscribers.put(topic, new ArrayList<>());

        List<Subscriber> current = subscribers.get(topic);
        current.add(subscriber);
    }

    public void unsubscribeToTopic(Subscriber subscriber, Topic topic) {
        if (!subscribers.containsKey(topic)) {
            throw new RuntimeException("Topic was never subscribed");
        }

        List<Subscriber> current = subscribers.get(topic);
        if (!current.contains(subscriber)) {
            throw new RuntimeException(subscriber + " was never subscribed to " + topic);
        }

        current.remove(subscriber);
    }
}
