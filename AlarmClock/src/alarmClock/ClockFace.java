package alarmClock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ClockFace extends JPanel {
	
	private static final int DIM_WIDTH = 10;
    private static final int DIM_HEIGHT = 10;
    private static final int SQ_SIZE = 10;

    boolean black = true;
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("HEre");
        g.drawString("HELLOOOOOOOOOOO", 50, 50);
	}

}
