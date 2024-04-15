package Q2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * a)
 * i) MVC separates the concerns. Different groups of people can work on business logic and ui logic
 * individually without any conflicts.
 * ii) Separating concerns helps in testing the model, ui and controller in isolation,
 * as well and system tests. This implies greater code quality and easier maintainability.
 *
 * iii) You might use MVP (Model-View-Presenter) or MVVM (Model-View-ViewModel), which are more complex
 * variations of MVC that are also popular.
 */
public class SimpleStats implements Observer {

  private final StatsModel model;
  private JTextField currentMax, currentMean;

  public SimpleStats(StatsModel model) {
    this.model = model;
    display();
  }

  private void display() {

    JFrame frame = new JFrame("Simple Stats");
    frame.setSize(250, 350);

    JPanel panel = new JPanel();

    currentMax = new JTextField(11);
    currentMean = new JTextField(11);

    panel.add(new JLabel("Max: value "));
    panel.add(currentMax);
    panel.add(new JLabel("Mean: value "));
    panel.add(currentMean);

    for (int i = 1; i <= 12; i++) {
      final int n = i;
      JButton button = new JButton(String.valueOf(i));
      button.addActionListener(e -> model.addNumber(n));
      panel.add(button);
    }

    frame.getContentPane().add(panel);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  @Override
  public void update(int max, double mean) {
    currentMax.setText(String.valueOf(model.max()));
    currentMean.setText(String.valueOf(model.mean()));
  }

  public static void main(String[] args) {
    StatsModel model = new StatsModel();
    SimpleStats app = new SimpleStats(model);
    model.addObserver(app);
  }
}
