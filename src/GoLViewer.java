import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class GoLViewer extends JPanel implements Observer, Serializable {

    private Board model;
    private Cell[][] cells;
    private Color alive = Color.GREEN;
    private Color dead = Color.RED;

    public GoLViewer(Board model) {
        this.model = model;
        model.addObserver(this);

        setLayout(new GridLayout(model.getRows(), model.getCols()));
        cells = new Cell[model.getRows()][model.getCols()];
        createCells();
    }

    private void createCells() {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                cells[i][j] = new Cell(i, j);
                add(cells[i][j]);
            }
        }
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model)
            repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                if (model.getCellStatus(i, j))
                    cells[i][j].setBackground(getColorAlive());
                else
                    cells[i][j].setBackground(getColorDead());
            }
        }
        repaint();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Color getColorAlive() {
        return alive;
    }

    public void setColorAlive(Color alive) {
        this.alive = alive;
        repaint();
    }

    public Color getColorDead() {
        return dead;
    }

    public void setColorDead(Color dead) {
        this.dead = dead;
        repaint();
    }

    public void setAction(MouseListener action){
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                cells[i][j].addMouseListener(action);
            }
        }
    }
}
