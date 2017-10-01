import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Engine implements ActionListener, Words, KeyListener {
	private Screen screen;
	private ArrayList<String> substr = new ArrayList<>();
	private int i;
	private int start_pos;
	private int end_pos;
	private String outStr;
	private String fractional_part;
	private int kopeks;
	
	public Engine(Screen screen) {
		this.screen = screen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(screen.goBtn)){
			
			substr.clear();								// очистить "тройки"
			kopeks = 0;
			outStr = digits[4];							// очистить выходную строку (в исходное)
			String str="";
			if (screen.input_fld.getText().equals(""))	// если поле пустое - то равно 0
				str="0";
			else
				str = screen.input_fld.getText();	// вытащить введенную цифру
			str = str.replace(" ", "");				// убрать пробелы
			str = str.replace(".", ",");	
			
		if (No_Double_Divider(str)){
			if (str.contains(",") | str.contains(".")){								// если есть разделитель ->			
					kopeks = Do_Fractional_part(str);
					if (kopeks>99)
						kopeks = 99;
					str = str.substring(0, str.length() - fractional_part.length());	// -> отрезать от строки дробную часть
			}
		
			int str_s = str.length();		// длина введенной строки
			
			if (str_s <= 12){
				// делим цифру на части по три знака
				i = 0;
				do {
					i = i+3;
					start_pos = (str_s-i < 0)? 0:str_s-i;	// если левая граница за число - в начало числа.
					end_pos = str_s-i+3;			
						if (end_pos == 0) break;			// если правая граница в ноль - то выход из цикла
					substr.add(str.substring(start_pos, end_pos));
				} while (i <= str_s);
				
				int parts = substr.size();
				int part_length;
				String char_to_translate;
				String str_to_add="";
				Check_ruble(); // проверка окончания "рубля" от последнего символа правой "тройки"
				
				// перевод "троек" от разделителя и влево
					for (int i = 0; i < parts; i++) {
	
						Check_digits(i);
						
						part_length = substr.get(i).length();
						// вычленение отдельных цифр из "тройки" справа налево
						for (int j = part_length-1; j >= 0; j--) {
							char_to_translate = Character.toString(substr.get(i).charAt(j));
							
							// разряд тысяч? w. Нет? m. Определение "пола" цифры.
							// "один рубль" или "одна тысяча"
							char s = (i==1)? 'w':'m';	
							
							// расшифровка "тройки" по цифрам, от единиц к сотням
							if (j==part_length-1){
								if (part_length >= 2 &&
									substr.get(i).charAt(substr.get(i).length()-2) == '1'){	// если разряд десяток равен "1" 
									char_to_translate = "1"+char_to_translate;
									str_to_add = Words.Units(Integer.valueOf(char_to_translate), s);
									Concat_str_with_new_digit(i, j, part_length, str_to_add);
									j=j-1;
									continue;
								}								
								str_to_add = Words.Units(Integer.valueOf(char_to_translate), s);
							}
							if (j==part_length-2)
								str_to_add = Words.Tens(Integer.valueOf(char_to_translate));
							if (j==part_length-3)
								str_to_add = Words.Hundreds(Integer.valueOf(char_to_translate));
							
							Concat_str_with_new_digit(i, j, part_length, str_to_add);
						}
					}
				
				String kopeks_str = (kopeks==0)? "00":Integer.toString(kopeks);
				outStr = outStr + " " + kopeks_str + " коп.";	// добавить копейки
				
				String start_digits="";
				for (int i = 0; i < parts; i++) {
					start_digits = substr.get(i)+" "+start_digits;		// цифры в начале
				}
				outStr = start_digits+"("+outStr; 						// добаить цифры в начале и оформить скобки
				
				screen.output_lbl.setText(outStr);
	//			3 677 014,36 
	//			3 677 014 (три миллиона шестьсот семьдесят семь тысяч четырнадцать) рублей 36 коп.
				
				screen.input_fld.requestFocus();
				Copy_to_Clipboard(outStr);
			}else{
				JOptionPane.showMessageDialog(screen.mp, "Введите число от 0 до 999 999 999 999");
			}
		}else{
			JOptionPane.showMessageDialog(screen.mp, "Введённое число содержит два разделителя");
		}
		
	}
	}

	private boolean No_Double_Divider(String str) {
		int k=0;
		for (char c : str.toCharArray())
			if (c==',')
				k++;
		if (k>1)
			return false;
		else
			return true;
	}

	private void Copy_to_Clipboard(String outStr) {
		StringSelection stringSelection = new StringSelection(outStr);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	private void Concat_str_with_new_digit(int i, int j, int part_length, String str_to_add) {
		if (i==0 & j==part_length-1 || str_to_add.equals("") || outStr.charAt(0)==')')
			outStr = str_to_add + "" + outStr;
		else outStr = str_to_add + " " + outStr;
	}

	/**
	 * Определяет копейки во входной строке. 
	 * @param str - входная строка
	 * @return
	 */
	private int Do_Fractional_part(String str) {
		String two_digits, after_two_digits;
		fractional_part = str.substring(str.lastIndexOf(","), str.length());	// выделить дробную часть
		// 111,4566 -> 45 + 66 -> 45.66 -> float 45.66 -> round = 46 -> 111 (str) + 46 (fractional_part)
		if (fractional_part.length()<=2){
			two_digits = fractional_part.substring(1, fractional_part.length())+"0";
			after_two_digits = "0";
		}else{
			two_digits = fractional_part.substring(1, 3);
			after_two_digits = fractional_part.substring(3, fractional_part.length());
		}
		String fractional_part_str_to_float = two_digits +"."+ after_two_digits;
		float fractional_part_float = Float.valueOf(fractional_part_str_to_float); 
		int fractional_part_int = Math.round(fractional_part_float);
		return fractional_part_int;
	}

	private void Check_digits(int i) {
		char c = substr.get(i).charAt(substr.get(i).length()-1);
		         
		if (substr.get(i).length() >= 2 &&
				substr.get(i).charAt(substr.get(i).length()-2) == '1')
				c = '5';
		if (i == 1)
			switch (c) {
			case '1': outStr = digits[i] + "а " + outStr; break;
			case '2': case '3': case '4': outStr = digits[i] + "и " + outStr;
				break;
			default: outStr = digits[i] + " "+ outStr;	break;
			}
		if (i >= 2)
			switch (c) {
			case '1': outStr = digits[i] + " " + outStr; break;
			case '2': case '3': case '4': outStr = digits[i] + "а " + outStr;
				break;
			default: outStr = digits[i] + "ов "+ outStr;	break;
			}
	}

	private void Check_ruble() {
		char c = substr.get(0).charAt(substr.get(0).length()-1);
		if (substr.get(0).length() >= 2 &&
				substr.get(0).charAt(substr.get(0).length()-2) == '1')
				c = '5';
		switch (c) {
		case '1': outStr = outStr+"ь"; break;
		case '2': case '3': case '4': outStr = outStr+"я"; break;
		default: outStr = outStr+"ей";
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (c==27)
			System.exit(0);
		
		if (c=='\n')
			screen.goBtn.doClick();
		
	    if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) 
	    		&& (c != '.') && (c != ',')) {
	         e.consume();  // ignore event
	    }
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
