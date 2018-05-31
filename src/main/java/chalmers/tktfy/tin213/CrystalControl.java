package chalmers.tktfy.tin213;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CrystalControl extends JPanel implements Runnable {

    private CrystalView panel;
    private CrystalModel m_cm;
    private int m_speed = 10;
    private boolean simRunning;
    private Thread m_thread;

    public CrystalControl (CrystalModel cm, CrystalView cv) {
        this.m_cm = cm;
	this.panel = cv;
	JPanel buttonPan = new JPanel();
	buttonPan.setLayout(new GridLayout(1, 3));
	
        JButton changeSpeed = new JButton("ChangeSpeed");
        changeSpeed.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed (ActionEvent e) {
		    signalStopSimulation();
		    String input = JOptionPane.showInputDialog(null, "Enter a new speed:", "ChangeSpeed", JOptionPane.QUESTION_MESSAGE);
		    if (input != null) {
			try {
			    int modifier = Integer.parseInt(input);
			    if (modifier > 0) {
				m_speed = modifier;
			    }
			} catch(NumberFormatException ex) {
			    JOptionPane.showMessageDialog(null, "Input error: "+input, "ChangeSpeed", JOptionPane.ERROR_MESSAGE);
			}
		    }
		} 
	    });
	
        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed (ActionEvent e) {
		    signalStopSimulation();
  
		    m_cm.reset();
		    panel.repaint();
  
		    if (!simRunning) {
			m_cm.srand(0);
			simRunning = true;
			m_thread = new Thread(CrystalControl.this);
			m_thread.start();
		    }
		}
	    });
        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed (ActionEvent e) {
		    signalStopSimulation();
		}
	    });
	
        buttonPan.add(run);
        buttonPan.add(changeSpeed);
        buttonPan.add(stop);
	
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
	add(buttonPan, BorderLayout.SOUTH);
	    
    }

    private void signalStopSimulation()
    {
	if (simRunning) {
	    simRunning = false;
	}
	if (m_thread != null) {
	    try {
		m_thread.join();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    private double getTime() {
	return System.nanoTime() / 1e9;
    }

    public void run() {
	double t0 = getTime();
	while (simRunning) {
	    if (!m_cm.runSomeSteps(m_speed)) {
		simRunning = false;
	    }
	    panel.repaint();
	}
	double t1 = getTime();
	System.out.printf("Java Simulation took %f s\n", t1-t0);
    }
}
