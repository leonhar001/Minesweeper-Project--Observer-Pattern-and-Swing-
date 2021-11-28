package leonhar001.ms.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import leonhar001.ms.model.BField;
import leonhar001.ms.model.EventField;
import leonhar001.ms.model.ObserverField;

@SuppressWarnings("serial")
public class FieldButton extends JButton 
	implements ObserverField, MouseListener{
		
	private final Color BG_STANDARD = new Color(164, 170, 184); 
	private final Color BG_MARK = new Color(8, 179, 247);
	private final Color BG_EXPLOSION = new Color(189, 66, 68);
	private final Color TXT_GREEN = new Color(0, 100, 0);
	
	private BField field;
	
	public FieldButton(BField field) {
		this.field = field;
		setBackground(BG_STANDARD);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		field.addObservers(this);
	}

	@Override
	public void eventOcurred(BField field, EventField event) {
		switch(event) {
		case OPEN: 
			applyOpenStyle();
			break;
		case MARK: 
			applyMarkStyle();
			break;
		case EXPLOSION: 
			applyExplosionStyle();
			break;
		default:
			applyStandardStyle();
		}
		
	}

	private void applyStandardStyle() {
		setBackground(BG_STANDARD);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void applyExplosionStyle() {
		System.out.println("explodiu");
		setBackground(BG_EXPLOSION);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void applyMarkStyle() {
		System.out.println("marcou");
		setBackground(BG_MARK);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void applyOpenStyle() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		if(field.isMined()) {
			setBackground(BG_EXPLOSION);
			return;
		}
		System.out.println("abriu");
		setBackground(BG_STANDARD);
	
		switch (field.minesAroundNeighborhood()) {
		case 1: 
			setForeground(TXT_GREEN);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
			setForeground(Color.YELLOW);
			break;
		case 5:
			setForeground(Color.YELLOW);
			break;
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		String value = !field.safeNeighborhood() ?
				field.minesAroundNeighborhood() + "" : "";
		setText(value);
	}
	
	/*MOUSE EVENT CAPTURES*/
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			field.openField();
		}else {
			field.alternateFlag();
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
