package chalmers.tktfy.tin213;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static void CLISim(int size) {
	int m_r_start = size/2;
	int m_r_escape = 11 * m_r_start / 10;
	int m_bath_width = 2 * (m_r_escape + 2);
	CrystalModel cm = new CrystalModel(m_bath_width, m_r_start, m_r_escape);
	while (cm.crystallizeOneIon()) {
	}
	System.out.println(cm);
    }

    private static void GUISim(int size) {
	int m_r_start = size/2;
	int m_r_escape = 11 * m_r_start / 10;
	int m_bath_width = 2 * (m_r_escape + 2);
	CrystalModel cm = new CrystalModel(m_bath_width, m_r_start, m_r_escape);
	CrystalView cv = new CrystalView(cm);
	CrystalControl cc = new CrystalControl(cm, cv);
	JFrame cf = new JFrame();
	cf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	cf.setSize(size, size);
	cf.setLocation(300,300);
	cf.setTitle("Java Crystal Simulation");
	cf.add(cc);
	cf.pack();
	cf.setVisible(true);
    }

    public static void main(String[] args) {
	int size = 0;
	String mode = "";
	for (String str : args) {
	    if (str.startsWith("mode=")) {
		mode = str.substring(5);
	    } else if (str.startsWith("size=")) {
		try {
		    size = Integer.parseInt(str.substring(5));
		} catch (Exception ignored) {
			
		}
	    }
	}
	if (size < 10) {
	    size = 10;
	}

	mode = mode.toLowerCase();
	if (mode.equals("cli")) {
	    CLISim(size);
	} else if (mode.equals("gui")) {
	    GUISim(size);
	} else {
	    System.out.println("Available modes are gui and cli");
	}
    }
}
