package Model;

public class Grille {
	private static int[] grille = new int[9];
	
	public Grille() {
		for (int i = 0; i < grille.length; i++) {
				grille[i] = 0;
		}
	}
	
	public static boolean putInPosition(boolean tour, int pos)
	{
		if (!verification(pos)) return false;
		
		int nb;
		
		if(tour) { nb = 1; }
		else { nb = 2; }
		grille[pos] = nb;
		
		return true;
	}
	
	private static boolean verification(int pos) {
		
		if (grille[pos] != 0) return false;
		else return true;
	}
	
	public static void resetGrille()
	{
		for (int i = 0; i < grille.length; i++) {
			grille[i] = 0;
		}
	}
	
	public static boolean verificationWin(int pos, WinRectangles winrectangles)
	{
		if(verificationWinLigne(pos, winrectangles)) { return true; }
		else if(verificationWinColonne(pos, winrectangles)) { return true; }
		else if(verificationWinDiagonale(pos, winrectangles)) { return true; }
		else { return false; }
	}
	
	private static boolean verificationWinLigne(int pos, WinRectangles winrectangles)
	{
		int color = grille[pos];
		if(pos == 0 || pos == 1 || pos == 2)
		{ if(grille[0] == color && grille[1] == color && grille[2] == color) { winrectangles.setWinrectangles(0, 1, 2); return true; } }
		else if(pos == 3 || pos == 4 || pos == 5)
		{ if(grille[3] == color && grille[4] == color && grille[5] == color) { winrectangles.setWinrectangles(3, 4, 5); return true; } }
		else if(pos == 6 || pos == 7 || pos == 8)
		{ if(grille[6] == color && grille[7] == color && grille[8] == color) { winrectangles.setWinrectangles(6, 7, 8); return true; } }
		return false;
	}
	
	private static boolean verificationWinColonne(int pos, WinRectangles winrectangles)
	{
		int color = grille[pos];
		if(pos == 0 || pos == 3 || pos == 6)
		{ if(grille[0] == color && grille[3] == color && grille[6] == color) { winrectangles.setWinrectangles(0, 3, 6); return true; } }
		else if(pos == 1 || pos == 4 || pos == 7)
		{ if(grille[1] == color && grille[4] == color && grille[7] == color) { winrectangles.setWinrectangles(1, 4, 7); return true; } }
		else if(pos == 2 || pos == 5 || pos == 8)
		{ if(grille[2] == color && grille[5] == color && grille[8] == color) { winrectangles.setWinrectangles(2, 5, 8); return true; } }
		return false;
	}
	private static boolean verificationWinDiagonale(int pos, WinRectangles winrectangles)
	{
		int color = grille[pos];
		if(pos == 4)
		{ if(grille[0] == color && grille[4] == color && grille[8] == color) { winrectangles.setWinrectangles(0, 4, 8); return true; }
		else if (grille[2] == color && grille[4] == color && grille[6] == color) { winrectangles.setWinrectangles(2, 4, 6); return true; }
		}
		else if(pos == 0 || pos == 8)
		{ if(grille[0] == color && grille[4] == color && grille[8] == color) { winrectangles.setWinrectangles(0, 4, 8); return true; } }
		else if(pos == 2 || pos == 6)
		{ if(grille[2] == color && grille[4] == color && grille[6] == color) { winrectangles.setWinrectangles(2, 4, 6); return true; } }
		return false;
	}
	
	public static int[] getGrille() {
		return grille;
	}

	public static void setGrille(int[] grille) {
		Grille.grille = grille;
	}
	
	
}
