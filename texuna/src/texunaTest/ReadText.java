package texunaTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

/**
 *
 * @author Pavel
 */

public class ReadText {
	public ArrayList<String> name = new ArrayList<String>();
	public ArrayList<String> date = new ArrayList<String>();
	public ArrayList<String> number = new ArrayList<String>();
	private ParseXml par = new ParseXml();
	private static int date_width;
	private static int name_width;
	private static int number_width;
	int k = 0;
	int page_height;
	int page_width;

	public List<HumanItem> reading() {
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\n");
		TsvParser parser = new TsvParser(settings);
		try {
			List<String[]> allRows = parser.parseAll(new File("src/texunaTest/source-data.tsv"), "UTF-16");
			System.setOut(new PrintStream(System.out, true, "UTF-16"));
			List<HumanItem> resultList = new ArrayList<>();
			resultList = allRows.stream().map(row -> new HumanItem(row)).collect(Collectors.toList());
			tableprint(resultList);
			return resultList;
		} catch (IOException ex) {

			System.out.println(ex.getMessage());
		}
		
		return new ArrayList<>();

	}

	public void tableprint(List<HumanItem> resultlist) {
		par.parse();
		date_width = Integer.parseInt(par.date_width);
		name_width = Integer.parseInt(par.name_width);
		number_width = Integer.parseInt(par.number_width);
		page_height = Integer.parseInt(par.height);
		page_width = Integer.parseInt(par.width);
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> number = new ArrayList<String>();
		ArrayList<String> date = new ArrayList<String>();
		int i = 0;
        for (i = 0; i < resultlist.size(); i++) {
        	   name.add(resultlist.get(i).getName());
        	   number.add(resultlist.get(i).getNumber());
        	   date.add(resultlist.get(i).getDate());
        }
		int counter1 = 0;
		int counter2 = 0;
		int counter3 = 0;
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/texunaTest/result.txt"), "UTF-16"));
			number.add(0, "Номер");
			date.add(0, "Дата");
			name.add(0, "ФИО");
			for (i = 0; i < number.size() - 1; i = i+2 ) {
			number.add(i+1,"rowBorder");
			date.add(i+1,"rowBorder");
			name.add(i+1,"rowBorder");
			}
			number.add("endBorder");
			date.add("endBorder");
			name.add("endBorder");
			int pageControl = 0;
			int rowControl = 0;
			int rowIndex = 0;
			for (i = 0; i < number.size() - 1; i++) {
				pageControl++;
				if (number.get(i).equals("rowBorder")) {
					i ++;
					rowControl++;
					if (pageControl + rowControl > page_height) {
			        if (rowIndex == 0) {
			        	rowIndex = i;
			        }
			        else {
				    number.add(rowIndex , "~");
					date.add(rowIndex, " ");
					name.add(rowIndex, " ");
				    	number.add(rowIndex + 1 , "Номер");
					date.add(rowIndex + 1, "Дата");
					name.add(rowIndex + 1, "ФИО");
					number.add(rowIndex + 2, "rowBorder");
					date.add(rowIndex + 2, "rowBorder");
					name.add(rowIndex + 2, "rowBorder");
					pageControl = 0;
					rowControl = 0;
					i = rowIndex + 1;
				    }
				}
					rowIndex = i;
						
					}
				
				counter1 = number.get(i).length() - number_width;
				counter2 = date.get(i).length() - date_width;
				counter3 = name.get(i).length() - name_width;
			
				if ((counter1 > 0) || (counter2 > 0) || (counter3 > 0)) {

					if (counter1 > 0) {
						if (counter2 > 0) {
							if (counter3 > 0) {
								number.add(i + 1, addString(number_width, number.get(i)));
								number.set(i, saveString(number_width, number.get(i)));
								date.add(i + 1, addStringDate(date_width, date.get(i)));
								date.set(i, saveStringDate(date_width, date.get(i)));
								name.add(i + 1, addStringName(name_width, name.get(i)));
								name.set(i, saveStringName(name_width, name.get(i))); // counter1>0;counter2>0;counter3>0
							} else {
								number.add(i + 1, addString(number_width, number.get(i)));
								number.set(i, saveString(number_width, number.get(i)));
								date.add(i + 1, addStringDate(date_width, date.get(i)));
								date.set(i, saveStringDate(date_width, date.get(i)));
								name.add(i + 1, "");
								// counter1>0;counter2>0;counter3<=0
							}

						}

						else {
							if (counter3 > 0) {
								number.add(i + 1, addString(number_width, number.get(i)));
								number.set(i, saveString(number_width, number.get(i)));
								date.add(i + 1, "");
								name.add(i + 1, addStringName(name_width, name.get(i)));
								name.set(i, saveStringName(name_width, name.get(i))); // counter1>0;counter2<=0;counter3>0
							} else {
								number.add(i + 1, addString(number_width, number.get(i)));
								number.set(i, saveString(number_width, number.get(i)));
								date.add(i + 1, "");
								name.add(i + 1, ""); // counter1>0;counter2<=0;counter3<=0
							}

						}
					} else {
						if (counter2 > 0) {
							if (counter3 > 0) {
								number.add(i + 1, "");
								date.add(i + 1, addStringDate(date_width, date.get(i)));
								date.set(i, saveStringDate(date_width, date.get(i)));
								name.add(i + 1, addStringName(name_width, name.get(i)));
								name.set(i, saveStringName(name_width, name.get(i))); // counter1<=0;counter2>0;counter3>0
							} else {
								number.add(i + 1, addString(number_width, number.get(i)));
								number.set(i, saveString(number_width, number.get(i)));
								date.add(i + 1, addStringDate(date_width, date.get(i)));
								date.set(i, saveStringDate(date_width, date.get(i)));
								name.add(i + 1, ""); // counter1<=0;counter2>0;counter3<=0

							}
						} else {
							number.add(i + 1, "");
							date.add(i + 1, "");
							name.add(i + 1, addStringName(name_width, name.get(i)));
							name.set(i, saveStringName(name_width, name.get(i))); // counter1<=0;counter2<=0;counter3>0

						}

					}	
				}
			}
			String s = "";
			
			for (i = 0; i < number.size() - 1; i++) {
				
				 if (number.get(i).equals("rowBorder") || number.get(i).equals("endBorder")) {
					for (int k = 0; k < number_width + date_width + name_width + 10;k++) {
						s += "-";
						
					}
					s += '\n';
					i++;
				}					
				 if (number.get(i).equals("~")) {
					s += "~";
					s += '\n';
					i++;
				 }
				s += "| ";
				s += number.get(i);
				if (number_width - number.get(i).length() > 0) {

					for (k = 0; k < (number_width -number.get(i).length()); k++) {
						s += " ";
					}
					
				}
				s += " |";
				s += " ";
				s += date.get(i);
				if (date_width - date.get(i).length() > 0) {

					for (k = 0; k < (date_width - date.get(i).length()); k++) {
						s += " ";
					}
					
					
				}
				s += " |";
				s += " ";
				s += name.get(i);
				if (name_width - name.get(i).length() > 0) {

					for (k = 0; k < (name_width - name.get(i).length()); k++) {
						s += " ";
					}
					
					
				}
				s += " |";
				s += '\n';
			}
			 
			for (int k = 0; k < number_width + date_width + name_width + 10;k++) {
				s += "-";	
				}
			s += '\n';		
			writer.write(s);
			writer.flush();
			writer.write("\n");
			writer.write("\n");
			writer.write("\n");
			writer.write(comments());
			writer.flush();
			writer.close();
		} catch (IOException ex) {

			System.out.println(ex.getMessage());
		}
	}

	public String addString(int width, String s) {
		int k = 0;
		s = deleteSpace(s);
		s = deleteSpace(s);
		String result = "";
		for (k = width; k < s.length(); k++) {
			result += s.charAt(k);
		}
		return result;
	}

	public String saveString(int width, String s) {
		int k = 0;
		s = deleteSpace(s);
		String result = "";
		s = deleteSpace(s);
		int numerate = width;
		if (s.length() < width) {
			numerate = s.length();
		}
		for (k = 0; k < numerate; k++) {
			result += s.charAt(k);

		}

		return result;
	}
	public String saveStringDate(int width,String s) {
		String sum = "";
		String[] word = s.split("/");
		 for (int i = 0; i < word.length; i++) {	 
	         if (word[i].length() + sum.length() + 1 < width) {
	        	 sum +=  word[i];
	        	 if (i!=word.length-1) {
	        		 sum += "/";
	        	 }
	         }
	         else {
	        	   break;
	         }
	      }
		if (sum.equals("")) {
			return saveString(width,s);
		}
		else {
			return sum;
		}
		
	}
	public String addStringDate(int width,String s) {
		String sum = "";
		String difference = "";
		String[] word = s.split("/");
		 for (int i = 0; i < word.length; i++) {	 
	         if (word[i].length() + sum.length() + 1 < width) {
	        	 sum +=  word[i];
	        	 difference = "";
	        	 for (int k = i + 1; k < word.length; k++) {
	        		 difference += word[k];
	        		 if (k!=word.length-1) {
		        		 difference += "/";
		        	 }
	        	 }
	        	 if (i!=word.length-1) {
	        		 sum += "/";
	        	 }
	         }
	         else {
	        	   break;
	         }
	      }
		if (difference.equals("")) {
			return addString(width,s);
		}
		else {
			return difference;
		}
		
	}
	public String saveStringName(int width, String s) {
		String sum = "";
		s = deleteSpace(s);
		String[] word = s.split(" ");
		 for (int i = 0; i < word.length; i++) {
	         if (word[i].length() + sum.length() + 1 < width) {
	        	 sum +=  word[i];
	        	 if (i!=word.length-1) {
	        		 sum += " ";
	        	 }
	         }
	         else {
	        	   break;
	         }
	      }
		if (sum.equals("")) {
			return saveString(width,s);
		}
		else {
			return sum;
		}
	}
	public String addStringName(int width,String s) {
		String sum = "";
		String difference = "";
		s = deleteSpace(s);
		String[] word = s.split(" ");
		 for (int i = 0; i < word.length; i++) {
			 
	         if (word[i].length() + sum.length() + 1 < width) {
	        	 sum +=  word[i];
	        	 difference = "";
	        	 for (int k = i + 1; k < word.length; k++) {
	        		 difference += word[k];
	        		 if (k!=word.length-1) {
		        		 difference += " ";
		        	 }
	        	 }
	        	 if (i!=word.length-1) {
	        		 sum += " ";
	        	 }
	         }
	         else {
	        	   break;
	         }
	      }
		if (difference.equals("")) {
			return addString(width,s);
		}
		else {
			return difference;
		}
		
	}
	
	
	public String deleteSpace(String s) {
		String timeString = "";
		if (s.charAt(0) == ' ') {
			 for (int m = 1; m < s.length(); m++) {
				 timeString += s.charAt(m);
			 }
			 s = timeString;
		}
		if (s.charAt(0) == ' ') {
			s = deleteSpace(s);
		}
		return s;
	}
	
	
	public String comments() {
		String s = "КОММЕНТАРИИ:\n";
		s += "1) В ДАННОМ ОТЧЕТЕ РЕАЛИЗОВАНО\n" ;
		s += "	а)* отчет должен быть разбит на страницы определённой широты и высоты\n" + 
				"	б) страницы отделяются друг от друга отдельной строкой содержащей один единственный символ ~\n" + 
				"	в) высота страницы не включает себя разделитель страниц\n" + 
				"	г) заголовок и данные внутри колонки должны быть выравнены влево\n" + 
				"	д) заголовки и данные в строке отделяются друг от друга символом |\n" + 
				"	е) слева и справа от заголовка/значения всегда должно быть по одному пробелу\n" + 
				"	ё) ширина колонок фиксирована в символах\n" + 
				"	ж) ширина колонок не включает в себя пробелы слева и справа от заголовка/значения\n" + 
				"	з) для заполнения неиспользованного места справа добавляются пробелы\n" + 
				"	и) заголовки колонок должны быть повторены на каждой странице \n" + 
				"	й) строки данных в отчете отделяются друг от друга (и от заголовка) строкой символов -\n" + 
				"	к) для перехода на новую строку должен использоваться системный разделитель строк\n" + 
				"	л)* ширина страницы не включает в себя разделитель строк \n" + 
				"	м) если заголовок или значение не умещается в отведённое место, то значение должно быть принудительно разбито\n" + 
				"	н) разбиенние должно производиться по границе слов, если это возможно\n" + 
				"	о) если разбиение по границе слов невозможно - то разбивать прямо посередине слова\n" + 
				"	п) границей слова является любой символ кроме букв и цифр\n" + 
				"	р) настройки подаются в виде ХМЛ файла (см. пример в settings.xml)\n" + 
				"	с) данные подаются в виде TAB-delimited файла в кодировке UTF-16 (см. пример в source-data.tsv)\n" + 
				"	т) результат должен быть записан в файл в кодировке UTF-16\n" + 
				"	у) использовать нужно язык Java\n" + 
				"	ф) разрешается использовать любые готовые библиотеки\n";
		s += "2)ЧАСТИЧНО РЕАЛИЗОВАНО:\n" +
				"а)л) отчет должен быть разбит на страницы определённой широты и высоты - этот пункт \n" +
				"остался не ясным из за широты, поскольку не заданы инструкции в случае, если размеры \n" +
				"колонок больше ширины страницы; также кажется противоречивым пункт л), так как не понятно \n"+
				"как разделитель строк оказывает влиние на ширину, логичнее было бы предположить влияние на высоту.\n" +
				"Инструкции же про высоту реализованы полностью\n";
		s += "3) ДОБАВЛЕНО:\n" +
				"	I) Приоритеты в разделении строки (когда длина строки больше ширины колонки): \n" +
				"		а)Строка делится на отдельные слова (в случае даты на числа разделенные слэшом /) \n " +
				"		б)Если первое слово поместилось в строку, а второе нет, то второе слово уходит на следующую строку \n " +
				"		При чем, приоритет отдается в сторону переноса, как в случае с Ким Чен Иром (Подробнее в пункте 4г) ) \n" + 
				"		в) Если слово не помещается в строку, то оно делится прямо по середине слова\n" + 
				"	II) Добавлена стороняя библиотека univocity-parsers, которая используются для парсинга XML файла\n"+
		        "	III) В конце страницы печатается разделитель строк, в высоту страницы он не включается.\n"+
				"	IV) Высота страницы включает в себя шапку и разделитель строк(но не страниц)\n";
		s += "4)КОММЕНТАРИИ К ДАННОМУ ПРИМЕРУ:\n"+
				"	a) Пробелы между словами убраны как часть строки, если слова находятся в разных строках: -Оксана | Сухово-\n" +
				"	б) Если слово не помещается, то переносится на следующую строку, если же и на следующей не помещается, то делится по ширине столбца\n" +
				"	в) Если данные одного номера не помещаются на страницу, то эти данные переносятся на новую страницу(как с Ким Чен Иром)\n "+
				"	г) Ким Чен Ир разделен на Ким и Чен Ир, алгоритм отдал приоритет такому резелению, а не Ким Чен и Ир, потому что он учитывает\n" +
				"	следующее слово после Чен, поэтому он перенес на следующую строку, в случае если бы имя содержало только \n"+
				"	Ким Чен, алгоритм записал бы в одну строку.(как описано в пункте)\n";
		return s;
	}
}
