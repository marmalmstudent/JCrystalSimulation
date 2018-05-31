package chalmers.tktfy.tin213;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class CrystalView extends JPanel {
    
    private BufferedImage m_image;
    private Graphics2D m_g;
    private CrystalModel m_cm;

    public CrystalView(CrystalModel cm) {
        setPreferredSize(new Dimension(cm.getBathWidth(),
				       cm.getBathWidth()));
	m_image = new BufferedImage(cm.getBathWidth(),
				    cm.getBathWidth(),
				    BufferedImage.TYPE_INT_RGB);
	m_g = (Graphics2D)m_image.getGraphics();
	m_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			     RenderingHints.VALUE_ANTIALIAS_ON);
        this.m_cm = cm;
    }
    
    private void drawBackground()
    {
	m_g.setColor(Color.BLACK);
	m_g.fillRect(0, 0, m_image.getWidth(), m_image.getHeight());
    }

    private void drawEdge()
    {
	int x_c = m_cm.xBathToModelRep(0);
	int y_c = m_cm.yBathToModelRep(0);

	m_g.setColor(Color.BLUE);
	m_g.drawArc(x_c-m_cm.getRadius(),
		    y_c-m_cm.getRadius(),
		    2*m_cm.getRadius(),
		    2*m_cm.getRadius(),
		    0, 360);
        
	m_g.setColor(Color.YELLOW);
	m_g.drawArc(x_c-m_cm.getRBounds(),
		    y_c-m_cm.getRBounds(),
		    2*m_cm.getRBounds(),
		    2*m_cm.getRBounds(),
		    0, 360);
    }

    private void drawCrystal()
    {
	int x = m_cm.getX();
	int y = m_cm.getY();
	int r = m_cm.getRadius();
	
	m_g.setColor(Color.RED);
        for(int i = -r; i <= r; i++) {
            for(int j = -r; j <= r; j++) {
                if (m_cm.getModelValue(i,j)) {
		    m_g.fillRect(m_cm.xBathToModelRep(i),
				 m_cm.yBathToModelRep(j),
				 1, 1);
                }
            }
        }
	m_g.setColor(Color.GREEN);
	m_g.fillRect(m_cm.xBathToModelRep(x),
		     m_cm.yBathToModelRep(y),
		     1, 1);
    }

    @Override
    public synchronized void paint(Graphics g) {
	drawBackground();
	drawEdge();
	drawCrystal();

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getScaleInstance(super.getWidth() / (double)m_image.getWidth(), super.getHeight() / (double)m_image.getHeight());
        BufferedImageOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(m_image, op, 0, 0);
    }
}
