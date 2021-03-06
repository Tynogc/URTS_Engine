package debug.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import debug.TerminalMenu;
import term.VisualisedTerminal;

public class DebugFrame extends JPanel implements Runnable{

	private static final long serialVersionUID = -7439711112932213033L;
	
	private VisualisedTerminal vt;
	
	private PerformanceMenu per;
	private TerminalMenu term;
	
	private JFrame frame;
	
	public DebugFrame(){
		frame = new JFrame("Seypris Debug");
		frame.setBounds(100,100,400,700);
		frame.setResizable(false);
		setBounds(0,0,400,700);
		setVisible(true);
		frame.add(this);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		vt = new VisualisedTerminal();
		vt.setSize(400, 550);
		debug.Debug.init(vt);
		debug.Timing.init();
		term = new TerminalMenu(0, 0, vt, null);
		
		new Thread(this, "DebugPainter").start();
	}

	@Override
	public void run() {
		while (true) {
			frame.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 550, 400, 150);
		g.setColor(Color.white);
		g.setFont(main.Fonts.font14);
		float[] t = debug.Timing.getFps();
		g.drawString("FPS(SO) "+(int)t[0], 10, 590);
		g.drawString("FPS(05) "+(int)t[1], 10, 610);
		g.drawString("FPS(10) "+(int)t[2], 10, 630);
		g.drawString("T "+(int)t[3]+"us", 10, 650);
		
		t = debug.Timing.getFpsTh();
		g.drawString(""+(int)t[0], 110, 590);
		g.drawString(""+(int)t[1], 110, 610);
		g.drawString(""+(int)t[2], 110, 630);
		g.drawString(""+(int)t[3]+"us", 110, 650);
		
		if(term != null)
			term.paint(g);
	}

}
