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
	
	public static boolean verificationWin(int pos)
	{
		if(verificationWinLigne(pos)) { return true; }
		else if(verificationWinColonne(pos)) { return true; }
		else if(verificationWinDiagonale(pos)) { return true; }
		else { return false; }
	}
	
	private static boolean verificationWinLigne(int pos)
	{
		int color = grille[pos];
		if(pos == 0 || pos == 1 || pos == 2)
		{ if(grille[0] == color && grille[1] == color && grille[2] == color) { return true; } }
		else if(pos == 3 || pos == 4 || pos == 5)
		{ if(grille[3] == color && grille[4] == color && grille[5] == color) { return true; } }
		else if(pos == 6 || pos == 7 || pos == 8)
		{ if(grille[6] == color && grille[7] == color && grille[8] == color) { return true; } }
		return false;
	}
	
	private static boolean verificationWinColonne(int pos)
	{
		int color = grille[pos];
		if(pos == 0 || pos == 3 || pos == 6)
		{ if(grille[0] == color && grille[3] == color && grille[6] == color) { return true; } }
		else if(pos == 1 || pos == 4 || pos == 7)
		{ if(grille[1] == color && grille[4] == color && grille[7] == color) { return true; } }
		else if(pos == 2 || pos == 5 || pos == 8)
		{ if(grille[2] == color && grille[5] == color && grille[8] == color) { return true; } }
		return false;
	}
	private static boolean verificationWinDiagonale(int pos)
	{
		int color = grille[pos];
		if(pos == 4)
		{ if((grille[0] == color && grille[4] == color && grille[8] == color) || (grille[2] == color && grille[4] == color && grille[6] == color)) { return true; } }
		else if(pos == 0 || pos == 8)
		{ if(grille[0] == color && grille[4] == color && grille[8] == color) { return true; } }
		else if(pos == 2 || pos == 6)
		{ if(grille[2] == color && grille[4] == color && grille[6] == color) { return true; } }
		return false;
	}
	
	public int[] getGrille() {
		return grille;
	}

//	public void setGrille(int[] grille) {
//		this.grille = grille;
//	}
	
	
}
