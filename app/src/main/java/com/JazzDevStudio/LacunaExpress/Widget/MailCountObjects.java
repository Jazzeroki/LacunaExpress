package com.JazzDevStudio.LacunaExpress.Widget;

/**
 * Created by PatrickSSD2 on 1/9/2015.
 */
public class MailCountObjects {

	private int id;
	private String mail_count_string = "-1";
	private int mail_count_int = -1;

	//Constructor
	public MailCountObjects(){
	}

	//Setter
	public void setID(int aID){
		this.id = aID;
	}

	//Setter
	public void setMail_count_string(String str){
		this.mail_count_string = str;
	}

	//Setter
	public void setMail_count_int(int int1){
		this.mail_count_int = int1;
	}

	//Getter
	public int returnInt(){
		return mail_count_int;
	}

	//Getter
	public String returnString(){
		return mail_count_string;
	}

	public int returnID(){
		return id;
	}

}
