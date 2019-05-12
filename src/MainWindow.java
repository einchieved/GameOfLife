import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private static Starter starter;
    private static MainWindow main;
    private JDesktopPane mainDesk;
    private int rows, cols;

    public MainWindow() {
        mainDesk = new JDesktopPane();
        mainDesk.setDesktopManager(new DefaultDesktopManager());
        setContentPane(mainDesk);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setTitle("Games");
        setVisible(true);
    }

    public static void main(String[] args) {
        main = new MainWindow();
        main.addChild(starter = new Starter(main), 500, 50);
    }

    /*
     *
     */
    public void addChild(JInternalFrame child, int x, int y) {
        child.setLocation(x, y);
        child.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainDesk.add(child);
        child.setVisible(true);
    }

    public static void addView(Board model){
        ChildWindow child = new ChildWindow(main, new GoLViewer(model), model);
        main.addChild(child, 20, 20);
    }

    public static void addKopie(Board model){
        Board board = new Board(model);
        ChildWindow child = new ChildWindow(main, new GoLViewer(board), board);
        main.addChild(child, 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            try {
                rows = (Integer.parseInt(starter.getZeilenText()));
                cols = (Integer.parseInt(starter.getSpaltenText()));
            } catch (NumberFormatException ex) {
                rows = 10;
                cols = 10;
            }
            Board board = new Board(rows, cols);
            GoLViewer viewer = new GoLViewer(board);
            ChildWindow child = new ChildWindow(main, viewer, board);
            main.addChild(child, 10, 10);
        }
    }
}
