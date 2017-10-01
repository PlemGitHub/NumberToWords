
public interface Words {
	
	public static String Units(int a, char s){
		String str = "";
		switch (a) {
			case 0: str = ""; break;
			case 1: str = (s=='m')? "один" : "одна"; break;
			case 2: str = (s=='m')? "два": "две"; break;
			case 3: str = "три"; break;
			case 4: str = "четыре"; break;
			case 5: str = "пять"; break;
			case 6: str = "шесть"; break;
			case 7: str = "семь"; break;
			case 8: str = "восемь"; break;
			case 9: str = "девять"; break;
			case 10: str = "десять"; break;
			case 11: str = "одиннадцать"; break;
			case 12: str = "двенадцать"; break;
			case 13: str = "тринадцать"; break;
			case 14: str = "четырнадцать"; break;
			case 15: str = "пятнадцать"; break;
			case 16: str = "шестнадцать"; break;
			case 17: str = "семнадцать"; break;
			case 18: str = "восемнадцать"; break;
			case 19: str = "девятнадцать"; break;
		}
		return str;
	}
	
	public static String Tens(int a){
		String str = "";
		switch (a) {
			case 0: str = ""; break;
			case 1: str = "десять"; break;
			case 2: str = "двадцать"; break;
			case 3: str = "тридцать"; break;
			case 4: str = "сорок"; break;
			case 5: str = "пятьдесят"; break;
			case 6: str = "шестьдесят"; break;
			case 7: str = "семьдесят"; break;
			case 8: str = "восемьдесят"; break;
			case 9: str = "девяносто"; break;
		}
		return str;
	}
	
	public static String Hundreds(int a){
		String str = "";
		switch (a) {
			case 0: str = ""; break;
			case 1: str = "сто"; break;
			case 2: str = "двести"; break;
			case 3: str = "триста"; break;
			case 4: str = "четыреста"; break;
			case 5: str = "пятьсот"; break;
			case 6: str = "шестьсот"; break;
			case 7: str = "семьсот"; break;
			case 8: str = "восемьсот"; break;
			case 9: str = "девятьсот"; break;
		}
		return str;
	}	
	public String[] digits = {"","тысяч", "миллион", "миллиард", ") рубл"};
	
}
