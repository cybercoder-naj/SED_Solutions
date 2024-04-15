package Q2;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatsModelTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private final StatsModel model = new StatsModel();
  private final Observer observer = context.mock(Observer.class);

  @Before
  public void setup() {
    model.addObserver(observer);
  }

  @Test
  public void testModel0() {
    assertEquals(0, model.max());
    assertEquals(0.0d, model.mean(), 0.0);
  }

  @Test
  public void testModel1() {
    context.checking(new Expectations() {{
      ignoring(observer);
    }});
    model.addNumber(5);

    assertEquals(5, model.max());
    assertEquals(5.0d, model.mean(), 0.0);
  }

  @Test
  public void testModel2() {
    context.checking(new Expectations() {{
      ignoring(observer);
    }});
    model.addNumber(5);
    model.addNumber(2);
    model.addNumber(8);

    assertEquals(8, model.max());
    assertEquals((5.0d + 2 + 8) / 3, model.mean(), 0.0);
  }

  @Test
  public void observerListensToChanges() {
    context.checking(new Expectations() {{
      exactly(1).of(observer).update(5, 5.0d);
      exactly(1).of(observer).update(5, (5.0d + 2) / 2);
      exactly(1).of(observer).update(8, (5.0d + 2 + 8) / 3);
    }});

    model.addNumber(5);
    model.addNumber(2);
    model.addNumber(8);
  }
}