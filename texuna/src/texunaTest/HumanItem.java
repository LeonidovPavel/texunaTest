package texunaTest;
/**
*
* @author Pavel
*/

public class HumanItem {
	private String number = new String();
	private String date = new String();
	private String name = new String();
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HumanItem(String[] args) {
		String number = args[0];
		String date = args[1];
		String name = args[2];
		this.number = number;
		this.date = date;
		this.name = name;
	}

}
