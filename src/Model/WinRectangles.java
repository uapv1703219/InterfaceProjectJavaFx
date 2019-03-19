package Model;

import java.util.Arrays;

public class WinRectangles {
	private int[] winrectangles = new int[3];
	
	public WinRectangles() {
		for (int i = 0; i < winrectangles.length; i++) {
			winrectangles[i] = -1;
		}
		
	}
	
	public int[] getWinrectangles() {
		return winrectangles;
	}

	public void setWinrectangles(int id1, int id2, int id3) {
		this.winrectangles[0] = id1;
		this.winrectangles[1] = id2;
		this.winrectangles[2] = id3;
	}

	public void reset() {
		for (int i = 0; i < winrectangles.length; i++) {
			winrectangles[i] = -1;
		}
		
	}

	@Override
	public String toString() {
		return "WinRectangles [winrectangles=" + Arrays.toString(winrectangles) + "]";
	}
	
	
}
