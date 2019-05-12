import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class ChildWindow extends JInternalFrame implements ActionListener, MouseListener,Serializable {
    private static int number = 0;
    private MainWindow mainWindow;
    private GoLViewer viewer;
    private Board model;
    //private Action action = new Action();
    private GameManager gameManager = new GameManager();
    private boolean druck = false;

    public ChildWindow (MainWindow mainWindow, GoLViewer viewer, Board model) {
        super("Game of Life " + number++, true, true);
        this.mainWindow = mainWindow;
        this.viewer = viewer;
        this. model = model;
        setSize(500, 500);
        setIconifiable(true);
        setMaximizable(true);

        createMenu();

        add(viewer);

        viewer.setAction(this);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        // JMenu: Modus + Items
        JMenu modus = new JMenu("Modus");
        JMenuItem laufen = new JMenuItem("Laufen");
        laufen.addActionListener(this);
        JMenuItem setzen = new JMenuItem("Setzen");
        setzen.addActionListener(this);
        JMenuItem malen = new JMenuItem("Malen");
        malen.addActionListener(this);
        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(this);
        modus.add(laufen);
        modus.add(setzen);
        modus.add(malen);
        modus.add(pause);
        menuBar.add(modus);
        // JMenu: Speed
        JMenu speed = new JMenu("Speed");
        JMenuItem schneller = new JMenuItem("schneller");
        schneller.addActionListener(this);
        JMenuItem langsamer = new JMenuItem("langsamer");
        schneller.addActionListener(this);
        speed.add(schneller);
        speed.add(langsamer);
        menuBar.add(speed);
        // JMenu Fenster
        JMenu fenster = new JMenu("Fenster");
        JMenuItem view = new JMenuItem("Neuer View");
        view.addActionListener(this);
        fenster.add(view);
        JMenuItem kopie = new JMenuItem("Neue Kopie");
        kopie.addActionListener(this);
        fenster.add(kopie);
        JMenuItem lebendig = new JMenuItem("Farbe lebendig");
        lebendig.addActionListener(this);
        fenster.add(lebendig);
        JMenuItem tot = new JMenuItem("Farbe tot");
        tot.addActionListener(this);
        fenster.add(tot);
        JMenuItem speichern = new JMenuItem("speichern");
        speichern.addActionListener(this);
        fenster.add(speichern);
        JMenuItem laden = new JMenuItem("laden");
        laden.addActionListener(this);
        fenster.add(laden);
        menuBar.add(fenster);
        // JMenu Figurgen
        JMenu figur = new JMenu("Figuren");
        JMenuItem gleiter = new JMenuItem("Gleiter");
        gleiter.addActionListener(this);
        figur.add(gleiter);
        menuBar.add(figur);

        add(menuBar, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = ((JMenuItem)e.getSource()).getText().toLowerCase();

        switch (command) {
            case "laufen":
                model.setMode(0);
                break;
            case "setzen":
                model.setMode(1);
                break;
            case "malen":
                model.setMode(2);
                break;
            case "pause":
                model.setMode(3);
                break;
            case "schneller":
                model.setSpeed((int) (model.getSpeed() - model.getSpeed() * 0.25));
                break;
            case "langsamer":
                model.setSpeed((int) (model.getSpeed() * 1.25));
                break;
            case "gleiter":
                model.addGleiter();
                break;
            case "farbe lebendig":
                viewer.setColorAlive(JColorChooser.showDialog(new Frame(), "Pick a color", Color.GREEN));
                break;
            case "farbe tot":
                viewer.setColorDead(JColorChooser.showDialog(new Frame(), "Pick a color", Color.RED));
                break;
            case "speichern":
                gameManager.saveGame(this);
                break;
            case "laden":
                gameManager.loadGame();
                break;
            case "neuer view":
                MainWindow.addView(model);
                break;
            case "neue kopie":
                MainWindow.addKopie(model);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (model.getMode() == 1){
            Cell a = (Cell)e.getComponent();
            if (model.getCellStatus(a.getPosX(), a.getPosY())){
                model.setCellDead(a.getPosX(), a.getPosY());
            }
            else{
                model.setCellAlive(a.getPosX(), a.getPosY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        druck = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        druck = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (model.getMode() == 2 && druck){
            Cell a = (Cell)e.getComponent();
            if (model.getCellStatus(a.getPosX(), a.getPosY())){
                model.setCellDead(a.getPosX(), a.getPosY());
            }
            else{
                model.setCellAlive(a.getPosX(), a.getPosY());
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}