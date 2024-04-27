package publications;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ArticleRepositoryTest {

  private static final Article ARTICLE = new Article("How to TDD?");

  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
  private final Summariser summariser = context.mock(Summariser.class, "Summariser");
  private final Subscriber alice = context.mock(Subscriber.class, "alice");
  private final Subscriber bob = context.mock(Subscriber.class, "bob");

  private final ArticleRepository articleRepository = new ArticleRepository(summariser);


  @Test
  public void summarisesArticleOnPublish() {
    context.checking(new Expectations(){{
      oneOf(summariser).summarise(ARTICLE);
      will(returnValue(new Topic[]{Topic.TESTING, Topic.JAVA}));
    }});

    articleRepository.publish(ARTICLE);
  }

  @Test
  public void interestedSubscribersAreNotified() {
    articleRepository.subscribeToTopic(alice, Topic.JAVA);
    articleRepository.subscribeToTopic(bob, Topic.TESTING);

    context.checking(new Expectations(){{
      oneOf(summariser).summarise(ARTICLE);
      will(returnValue(new Topic[]{Topic.TESTING, Topic.JAVA}));

      oneOf(alice).alert(ARTICLE);
      oneOf(bob).alert(ARTICLE);
    }});

    articleRepository.publish(ARTICLE);
  }

  @Test
  public void uninterestedSubscribersAreNotNotified() {
    articleRepository.subscribeToTopic(alice, Topic.JAVA);
    articleRepository.subscribeToTopic(bob, Topic.TESTING);

    context.checking(new Expectations(){{
      oneOf(summariser).summarise(ARTICLE);
      will(returnValue(new Topic[]{Topic.JAVA}));

      oneOf(alice).alert(ARTICLE);
      never(bob).alert(ARTICLE);
    }});

    articleRepository.publish(ARTICLE);
  }

  @Test
  public void interestedSubscribersAreNotifiedOnceOnly() {
    articleRepository.subscribeToTopic(alice, Topic.JAVA);
    articleRepository.subscribeToTopic(alice, Topic.TESTING);

    context.checking(new Expectations(){{
      oneOf(summariser).summarise(ARTICLE);
      will(returnValue(new Topic[]{Topic.TESTING, Topic.JAVA}));

      oneOf(alice).alert(ARTICLE);
      never(bob).alert(ARTICLE);
    }});

    articleRepository.publish(ARTICLE);
  }

  @Test
  public void unsubscribedSubscribersAreNotNotified() {
    interestedSubscribersAreNotified();

    articleRepository.unsubscribeToTopic(alice, Topic.JAVA);
    context.checking(new Expectations(){{
      oneOf(summariser).summarise(ARTICLE);
      will(returnValue(new Topic[]{Topic.TESTING, Topic.JAVA}));

      never(alice).alert(ARTICLE);
      oneOf(bob).alert(ARTICLE);
    }});

    articleRepository.publish(ARTICLE);
  }
}
