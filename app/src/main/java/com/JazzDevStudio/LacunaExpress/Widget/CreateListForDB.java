package com.JazzDevStudio.LacunaExpress.Widget;

import java.util.ArrayList;
import java.util.List;

//This class has a method that returns a list of the items to go into the database either using the add or update methods
public class CreateListForDB {

	//Params are matching the columns in the database
	public static List<String> CreateList ( String awid, //0
	                                        String sync_frequency, //1
                                            String chosen_account_string, //2
                                            String message_count_int, //3
                                            String message_count_string, //4-18
                                            String tag_chosen, //19
                                            String color_background_choice, //20
                                            String font_color_choice, //21
                                            String sessionID, //22
                                            String homePlanetID, //23
                                            String notifications_boolean, //24
	                                        String message_count_received //25
										){

		List<String> returned_list = new ArrayList<>();

		returned_list.add(awid); //0
		returned_list.add(sync_frequency); //1
		returned_list.add(chosen_account_string); //2
		returned_list.add(message_count_int); //3
		/*
		I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
		will not matter as it is not checking the other columns. Once I add an update to allow for editing
		widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
		update code to allow for specific passing of the tag chosen and using that to write.
		 */
		returned_list.add(message_count_string); //4
		returned_list.add(message_count_string); //5
		returned_list.add(message_count_string); //6
		returned_list.add(message_count_string); //7
		returned_list.add(message_count_string); //8
		returned_list.add(message_count_string); //9
		returned_list.add(message_count_string); //10
		returned_list.add(message_count_string); //11
		returned_list.add(message_count_string); //12
		returned_list.add(message_count_string); //13
		returned_list.add(message_count_string); //14
		returned_list.add(message_count_string); //15
		returned_list.add(message_count_string); //16
		returned_list.add(message_count_string); //17
		returned_list.add(message_count_string); //18
		returned_list.add(tag_chosen); //19
		returned_list.add(color_background_choice); //20
		returned_list.add(font_color_choice); //21
		returned_list.add(sessionID); //22
		returned_list.add(homePlanetID); //23
		//AS OF RIGHT NOW, this line above is passing in the homePlanetID instead of the empire ID.
		returned_list.add(notifications_boolean); //24
		returned_list.add(message_count_received); //25

		return returned_list;
	}
}
