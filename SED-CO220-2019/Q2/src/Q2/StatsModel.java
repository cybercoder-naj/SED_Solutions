package Q2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatsModel {
  private final List<Integer> numbers = new ArrayList<>();
  private final List<Observer> observers = new ArrayList<>();

  public void addNumber(int n) {
    numbers.add(n);
    notifyObservers();
  }

  public int max() {
    return numbers.stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
  }

  public double mean() {
    return numbers.stream().mapToDouble(val -> val).average().orElse(0.0);
  }

  public void addObserver(Observer observer) {
    this.observers.add(observer);
  }

  private void notifyObservers() {
    int max = max();
    double mean = mean();
    for (Observer observer : observers) {
      observer.update(max, mean);
    }
  }
}
