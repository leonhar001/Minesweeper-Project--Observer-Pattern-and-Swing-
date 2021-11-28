package leonhar001.ms.view;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import leonhar001.ms.model.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame {
	
	public MainScreen() {
		
		Board board = new Board(16, 30, 55);
		
		add(new BoardPanel(board));
		
		ImageIcon imagemTituloJanela = new ImageIcon("images/logo.png"); 
		this.setIconImage(imagemTituloJanela.getImage());
		setTitle("MINESWEEPER");
		setSize(690,348);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		new MainScreen();
		
	}
}
