package leonhar001.ms.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import leonhar001.ms.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

	public BoardPanel (Board board) {
		
		setLayout(new GridLayout(
				board.getRows(),board.getColumns()));
		board.forEachField(f -> add(new FieldButton(f)));
		board.addObserver(o -> {
		SwingUtilities.invokeLater(() ->{
			
				if(o.booleanValue()) {
					JOptionPane.showMessageDialog(this, "GANHOU!");
				}else {
					JOptionPane.showMessageDialog(this, "PERDEU!");
				}
				
				board.restart();
			});
		});
	
	}
	
}
