import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Observable;

public class Board extends Observable implements Serializable, Runnable {
    private boolean[][] neu;
    private boolean[][] aktuell;
    private int rows, cols, mode;
    private int speed = 1000;

    public Board(int rows, int cols) {
        mode = 0;
        this.rows = rows;
        this.cols = cols;
        neu = new boolean[rows][cols];
        aktuell = new boolean[rows][cols];
        reset();
        JOptionPane.showMessageDialog(null, rows + "\n" + cols, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
        addGleiter();

        (new Thread(this)).start();
    }

    public Board(Board board) {
        speed = board.speed;
        mode = board.getMode();
        this.rows = board.getRows();
        this.cols = board.getCols();
        neu = new boolean[rows][cols];
        aktuell = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                neu[i][j] = board.getCellStatus(i, j);
                aktuell[i][j] = board.getCellStatus(i, j);
            }
        }

        (new Thread(this)).start();
    }

    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                neu[i][j] = false;
                aktuell[i][j] = false;
            }
        }
        setChanged();
        notifyObservers();
    }

    public void addGleiter(){
        if (rows > 2 && cols > 2) {
            setCellAlive(0, 1);
            setCellAlive(1, 2);
            setCellAlive(2, 0);
            setCellAlive(2, 1);
            setCellAlive(2, 2);
            setChanged();
            notifyObservers();
        }
    }

    public void setMode(int modus) {
        this.mode = modus;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMode() {
        return mode;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getCellStatus(int x, int y){
        return aktuell[x][y];
    }

    public void setCellAlive(int x, int y){
        aktuell[x][y] =  true;
        neu[x][y] = true;
        setChanged();
        notifyObservers();
    }
    public void setCellDead(int x, int y){
        aktuell[x][y] =  false;
        neu[x][y] = false;
        setChanged();
        notifyObservers();
    }

    public void aktualisieren(){
        int anzahlLebenderNachbarn;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                anzahlLebenderNachbarn = getaAnzahlLebenderNachbarn(i, j);
                if (anzahlLebenderNachbarn == 3){
                    neu[i][j] = true;
                }
                else if (anzahlLebenderNachbarn < 2 || anzahlLebenderNachbarn > 3){
                    neu[i][j] = false;
                }
            }
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                    aktuell[i][j] = neu[i][j];
            }
        }

        setChanged();
        notifyObservers();
    }

    public int getaAnzahlLebenderNachbarn(int x, int y){
        int anzahl = 0, indexEins, indexZwei;
        for (int a = x-1; a < x+2; a++) {
            for (int b = y - 1; b < y + 2; b++) {
                indexEins = 0;
                indexZwei = 0;
                if (!(a == x && b == y)) {
                    if (a < rows && a >= 0) {
                        indexEins = a;
                    } else if (a < 0) {
                        indexEins = rows - 1;
                    }

                    if (b < cols && b >= 0) {
                        indexZwei = b;
                    } else if (b < 0) {
                        indexZwei = cols - 1;
                    }

                    if (aktuell[indexEins][indexZwei]) {
                        anzahl++;
                    }
                }
            }
        }
        return anzahl;
    }

    @Override
    public void run() {
        while (true){
            while (mode != 0){
                try {
                    Thread.sleep(5);
                }catch (InterruptedException e){}
            }
            if (mode == 0){
                try {
                    Thread.sleep(speed);
                }catch (InterruptedException e){}
                aktualisieren();
            }
        }
    }
}