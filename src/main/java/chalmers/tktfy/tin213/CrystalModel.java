package chalmers.tktfy.tin213;

import java.util.*;

public class CrystalModel {
    
    private static final String VBOUND = "|";
    private static final String HBOUND = "-";
    private static final String EMPTY = " ";
    private static final String POPULATED = "*";
    private static final String POPULATED_LAST = "#";
    
    private Matrix m_mat;
    private Point m_p;

    private int rStart;
    private int rEscape;
    private int bathWidth;

    private static final Point dp[] = new Point[]{
	new Point(1, 0),
	new Point(-1, 0),
	new Point(0, 1),
	new Point(0, -1)
    };

    public CrystalModel(int bathWidth, int rStart, int rEscape) {
	m_mat = new Matrix(bathWidth);
	m_p = new Point();
        this.bathWidth = bathWidth;
	this.rStart = rStart;
	this.rEscape = rEscape;

	populate(new Point(0, 0));
    }

    public boolean crystallizeOneIon() {
	Point p = new Point();
	for (dropNewIon(rStart, p); !anyNeighbours(p); stepOnce(p)) {
	    if (outsideCircle(rEscape, p)) {
		dropNewIon(rStart, p);
		
		// smashes the stack in java
		// crystallizeOneIon();
	    }
	}
	m_p = p;
	populate(m_p);
        return !outsideCircle(rStart, m_p);      
    }

    public void reset() {
	m_mat.clear();
	populate(new Point(0, 0));
    }

    public boolean runSomeSteps(int steps) {
	if (steps > 0) {
	    return crystallizeOneIon() && runSomeSteps(steps-1);
	}
        return true;
    }

    @Override
    public String toString() {
        int x = getX();
        int y = getY();
        int size = getRadius();
        StringBuffer s = new StringBuffer(1000);
        for(int i = -(size+1); i <= (size+1); i++) {
            s.append(HBOUND);
        }
        s.append("\n");
        for(int i = -size; i <= size; i++) {
            s.append(VBOUND);
            for(int j = -size; j <= size; j++) {
                if (getModelValue(i, j)) {
		    s.append((i == x && j == y) ? POPULATED_LAST : POPULATED);
                } else {
                    s.append(EMPTY);
                }
            }
            s.append(VBOUND);
            s.append("\n");
        }
        for(int i = -(size+1); i <= (size+1); i++) {
            s.append(HBOUND);
        }
        s.append("\n");
        return s.toString();
    }
    
    private boolean anyNeighbours(Point p) {
        return getModelValue(p.x()+1, p.y())
	    || getModelValue(p.x()-1, p.y())
	    || getModelValue(p.x(), p.y()+1)
	    || getModelValue(p.x(), p.y()-1);
    }

    private void dropNewIon(int radius, Point p) {
        double alpha = 2 * Math.PI * CSRandom.cs_drand();
	p.x((int)(radius*Math.cos(alpha)));
	p.y((int)(radius*Math.sin(alpha)));
    }
    
    public boolean getModelValue(int x, int y) {
	return isPopulated(new Point(x, y));
    }

    public int xBathToModelRep(int x) {
        return x+bathWidth/2;
    }

    public int yBathToModelRep(int y) {
        return bathWidth/2-y;
    }

    public int getX() {
        return m_p.x();
    }

    public int getY() {
        return m_p.y();
    }

    public int getRBounds() {
        return rStart;
    }

    public int getRadius() {
        return rEscape;
    }

    public int getBathWidth() {
        return bathWidth;
    }

    public void srand(int seed) {
	CSRandom.cs_srand(seed);
    }

    private boolean outsideCircle(int radius, Point p) {
        return p.length() >= radius;
    }

    private void stepOnce(Point p) {
	p.add(dp[CSRandom.cs_rand() & 3]);
    }

    private boolean isPopulated(Point p) {
	return m_mat.get(xBathToModelRep(p.x()),
			 yBathToModelRep(p.y())) != 0;
    }

    private void populate(Point p) {
	m_mat.set(xBathToModelRep(p.x()),
		  yBathToModelRep(p.y()),
		  1);
    }
}
