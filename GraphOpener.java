import java.awt.*;
import java.awt.event.*;

public class GraphOpener implements ActionListener {

    public GraphOpener() {
    }

    public void actionPerformed(ActionEvent e) {
	Grapher g = new Grapher();
	g.setVisible(true);
    }
}