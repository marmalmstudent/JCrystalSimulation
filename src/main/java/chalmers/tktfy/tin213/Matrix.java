package chalmers.tktfy.tin213;

public class Matrix
{
    private int m_array[];
    private int m_size;
    
    public Matrix(int size)
    {
	m_array = new int[size*size];
	m_size = size;
    }

    public void clear()
    {
	for (int i = 0; i < m_array.length; ++i) {
	    m_array[i] = 0;
	}
    }

    public int get(int x, int y) {
	try {
	    return m_array[x + y*m_size];
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.printf("Attempted to access (%d,%d) in a (%d x %d matrix)\n", x, y, m_size, m_size);
	    return 0;
	}
    }
    
    public void set(int x, int y, int val) {
	try {
	    m_array[x + y*m_size] = val;
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.printf("Attempted to access (%d,%d) in a (%d x %d matrix)\n", x, y, m_size, m_size);
	}
    }
};
