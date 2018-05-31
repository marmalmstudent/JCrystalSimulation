package chalmers.tktfy.tin213;

public class CSRandom {
    private static final int CS_RAND_MAX = 0x7fff;
    private static int next = 1;

    public static int cs_rand() {
	next = next * 0x41c64e6d + 0x3039;
	return (next >>> 16) & 0x7fff;
    }
    public static double cs_drand() {
	return cs_rand() / (double)0x8000;
    }
    public static void cs_srand(int seed) {
	next = seed;
    }
}
