import javax.swing.*;
import java.awt.*;

public class Starter extends JInternalFrame {
  private JTextField zeilenText, spaltenText;

  public Starter(MainWindow mainWindow) {
    super("Starter", true, true);
    setSize(200, 150);
    setBackground(Color.WHITE);
    setIconifiable(true);
    setMaximizable(true);
    setLayout(new GridLayout(3, 1));

    Container container = new Container();
    container.setLayout(new GridLayout(1, 2));
    JLabel zeilenLabel = new JLabel("Zeilen:");
    zeilenLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    container.add(zeilenLabel);
    zeilenText = new JTextField();
    container.add(zeilenText);
    add(container);

    Container container1 = new Container();
    container1.setLayout(new GridLayout(1, 2));
    JLabel spaltenLabel = new JLabel("Spalten:");
    spaltenLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    container1.add(spaltenLabel);
    spaltenText = new JTextField();
    container1.add(spaltenText);

    Container container2 = new Container();
    container2.setLayout(new GridLayout(1, 1));
    JButton startBtn = new JButton("Start");
    startBtn.addActionListener(mainWindow);
    startBtn.setActionCommand("start");
    container2.add(startBtn);

    add(container);
    add(container1);
    add(container2);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  public String getZeilenText() {
    return zeilenText.getText();
  }

  public String getSpaltenText() {
    return spaltenText.getText();
  }
}
