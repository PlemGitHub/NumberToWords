import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen implements Words {
	Engine engine = new Engine(this);
	
	JTextField input_fld = new JTextField();
	JPanel mp = new JPanel();
	JLabel output_lbl = new JLabel("Результат");
	JButton goBtn = new JButton("Перевести");
	
	
	public Screen() {
		mp.setBackground(Color.white);
		mp.setLayout(null);
		JFrame fr = new JFrame("Перевод суммы в пропись");
		fr.setContentPane(mp);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		fr.setBounds(0, 200, screenWidth, 300);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mp.add(input_fld);
		input_fld.setBounds(20, 20, 200, 30);
		input_fld.setBackground(Color.white);
			mp.add(output_lbl);
			output_lbl.setBounds(input_fld.getX(), input_fld.getY()+input_fld.getHeight()+20, fr.getWidth()-20, 30);
			output_lbl.setBackground(Color.white);
				mp.add(goBtn);
				goBtn.setBounds(input_fld.getX()+input_fld.getWidth()+20, input_fld.getY(), 250, 30);
				goBtn.setBackground(Color.white);
				goBtn.setFocusable(false);
			
		goBtn.addActionListener(engine);
		input_fld.addKeyListener(engine);
		input_fld.requestFocus();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Screen sc = new Screen();
	}

}
