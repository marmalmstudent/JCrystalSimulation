package chalmers.tktfy.tin213;

public class Point
{
    private int m_x;
    private int m_y;
    
    public Point(int x, int y) {
	m_x = x;
	m_y = y;
    }
    
    public Point() {
	this(0, 0);
    }
    
    public int x() { return m_x; }
    public void x(int x) { m_x = x; }
    
    public int y() { return m_y; }
    public void y(int y) { m_y = y; }
    
    public int length() {
	return (int) Math.sqrt(m_x*m_x + m_y*m_y);
    }

    public void add(Point p) {
	m_x += p.m_x;
	m_y += p.m_y;
    }
};
