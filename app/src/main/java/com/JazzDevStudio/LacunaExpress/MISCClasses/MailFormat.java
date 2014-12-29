package com.JazzDevStudio.LacunaExpress.MISCClasses;

import android.util.Log;
import java.util.ArrayList;

import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;

/**
 * Created by PatrickSSD2 on 12/15/2014.
 */
//This class is for formatting mail content when passed in as a Messages Object into easier to read data
public class MailFormat {

	//ArrayList<Integer> return_int = new ArrayList<Integer>();
	//ArrayList<String> return_string = new ArrayList<String>();
	//ArrayList<String[]> arrayOfArrays = new ArrayList<String[]>();

	//Formats the From of a message
	public static ArrayList<String> fFrom(ArrayList <Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).from;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the To of a message
	public static ArrayList<String> fTo(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).to;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the Subject of a message
	public static ArrayList<String> fSubject(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).subject;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the Date of a message
	public static ArrayList<String> fDate(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).date;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the ReplyTo of a message
	public static ArrayList<String> fIn_Reply_To(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).in_reply_to;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the body_preview of a message
	public static ArrayList<String> fBody_Preview(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).body_preview;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the body of a message
	public static ArrayList<String> fBody(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		for(int i = 0; i < message_array_list.size(); i++){
			String str = message_array_list.get(i).body;
			return_string.add(str);
		}
		return return_string;
	}

	//Formats the has_replied of a message
	public static ArrayList<Integer> fHas_Replied(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).has_replied;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the has_archived of a message
	public static ArrayList<Integer> fHas_Archived(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).has_archived;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the has_trashed of a message
	public static ArrayList<Integer> fHas_Trashed(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).has_trashed;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the id of a message
	public static ArrayList<Integer> fID(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).id;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the from_id of a message
	public static ArrayList<Integer> fFrom_ID(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).from_id;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the to_id of a message
	public static ArrayList<Integer> fTo_ID(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).to_id;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the has_read of a message
	public static ArrayList<Integer> fHas_Read(ArrayList<Response.Messages> message_array_list){
		ArrayList<Integer> return_int = new ArrayList<Integer>();
		for(int i = 0; i < message_array_list.size(); i++){
			int intx = message_array_list.get(i).has_read;
			return_int.add(intx);
		}
		return return_int;
	}

	//Formats the tags of a message. Returns an arraylist of arrays
	public static ArrayList<String[]> fTags(ArrayList <Response.Messages> message_array_list){
		String[] strArray = new String[message_array_list.size()];
		ArrayList<String[]> arrayOfArrays = new ArrayList<String[]>();
		for(int i = 0; i < message_array_list.size(); i++){
			strArray = message_array_list.get(i).tags;
			arrayOfArrays.add(strArray);
		}
		return arrayOfArrays;
	}

	//Formats the tags of a message. Returns an arraylist of arrays
	public static ArrayList<String[]> fRecipients(ArrayList <Response.Messages> message_array_list){
		String[] strArray = new String[message_array_list.size()];
		ArrayList<String[]> arrayOfArrays = new ArrayList<String[]>();
		for(int i = 0; i < message_array_list.size(); i++){
			strArray = message_array_list.get(i).recipients;
			arrayOfArrays.add(strArray);
		}
		return arrayOfArrays;
	}

	//Formats and returns Strings from an ArrayList of string arrays
	public static ArrayList<String> fUnNestArrayList(ArrayList <String[]> string_array_list){
		ArrayList<String> return_string = new ArrayList<String>();
		//Nested for loop to loop through the Array list of string arrays
		for(int i = 0; i < string_array_list.size(); i++){
			String[] strArray = string_array_list.get(i);
			for(int x = 0; x < strArray.length; x++){
				String str = strArray[x];
				return_string.add(str);
			}
		}
		return return_string;
	}

}
