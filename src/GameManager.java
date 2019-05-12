import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GameManager extends Component implements Serializable{

    public void saveGame(ChildWindow childWindow) {
        JFileChooser c = new JFileChooser(new File("c://"));
        c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fs = new FileOutputStream(""+c.getSelectedFile().getPath());
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(childWindow);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

   public void loadGame() {
       ChildWindow childWindow;
       JFileChooser c = new JFileChooser(new File("c://"));
       c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fs = new FileInputStream("" + c.getSelectedFile().getPath());
                ObjectInputStream is = new ObjectInputStream(fs);
                childWindow = (ChildWindow) is.readObject();
                childWindow.setVisible(true);
            } catch (FileNotFoundException | ClassNotFoundException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
