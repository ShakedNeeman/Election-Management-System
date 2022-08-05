package view;
import javax.swing.JOptionPane;

import model.ElectionMain;


public class mainProgram{
	
	public static void main(String[] args) throws Exception{
	boolean endMain = false;
	int menuChoice; 
	while (!endMain) 
	{ 
		menuChoice=Integer.parseInt(JOptionPane.showInputDialog("1) Console view\n" 
		+ "2) UI view\n" 
		+ "3) EXIT\n")); 
		
		switch (menuChoice) {
		
		case 1: 
			JOptionPane.showMessageDialog(null, "Console!"); 
			ElectionMain.main(args); 
			break;
		case 2:
			JOptionPane.showMessageDialog(null, "UI!"); 
			ElectionMain.main(args);
			break;
		case 3: 
			JOptionPane.showMessageDialog(null, "Exit!"); 
			endMain=true;
			break;
			default: 
			JOptionPane.showMessageDialog(null, "wrong input! , enter value between 1-3");
			break;
		}
	}
	}
}