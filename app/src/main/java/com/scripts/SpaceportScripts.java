package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class SpaceportScripts {
    /*
    //Spaceport methods
    static void ViewShips(String planetID){
    	int bID = FindBuildingID("Space Port", planets.get(planetID).buildings);
    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.View(sessionID, String.valueOf(bID));//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	System.out.println(request);
    	System.out.println(spaceport.url);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	System.out.println(reply);
    }
    static ArrayList<Response.Available>  GetShipsForTargetFromPlanet(String planetID){
    	Spaceport spaceport = new Spaceport("spaceport");
    	Spaceport.Target target = GetTarget();
    	//Spaceport.Target target = new Spaceport.Target();
    	//target.bodyName = GetSingleInputFromUser("Enter the target planet name");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.available;
    }
    static ArrayList<Response.Available>  GetShipsForTargetFromPlanet(String planetID, Spaceport.Target target){
    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);//spaceport.ViewAllShips(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.available;
    }

    static Response.Available FindSpaceshipOfType(ArrayList<Response.Available> ships, String typeToFind){
    	for(Response.Available a: ships){
			System.out.println(a.type_human);
			System.out.println(a.combat);
			System.out.println(a.stealth);

			if(a.type_human.equals(typeToFind)){
				return a;
			}
		}
		return null;
    }

    static void LaunchFleet(String planetID){
    	//int bID = FindBuildingID("spaceport", planets.get(planetID).buildings);
    	Spaceport spaceport = new Spaceport("spaceport");
    	Spaceport.Target target  = new Spaceport.Target();
    	target.bodyID = GetSingleInputFromUser("Input target id");
    	String request = spaceport.GetShipsFor(sessionID, planetID, target);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	Response response = gson.fromJson(reply, Response.class);
    	ArrayList<String> ships = new ArrayList<String>();
    	Captcha();
    	if (response.result.available.size() != 0){
    		String shipType = GetSingleInputFromUser("Enter ship type");
    		for(Response.Available a: response.result.available){
    			System.out.println(a.type);
    			if(a.type.contains(shipType))
    				ships.add(a.id);

    		}
    	}
    	ArrayList<String> temp = new ArrayList<String>();
    	int z = 0;
    	if(ships.size()!=0){
    		//GetSingleInputFromUser("hi");
    		System.out.println("Launching fleet "+z);
    		int count = 0;
    		for(String s: ships){

    			temp.add(s);
    			count++;
    			System.out.println(count);
    			if(count == 20){
    				Spaceport space = new Spaceport("spaceport");
    				String r = space.SendFleet(sessionID, temp, target);
    				SaveToLog(r);
    				r =server.ServerRequest(gameServer, spaceport.url, r);
    				SaveToLog(r);
    				temp.clear();
    				count = 0;
    				//break;
    			}
    		}
    	}

    	//System.out.println(reply);
    }



    static void LargeFleetSenderTest(String planetID){
    	Captcha();
    	//Target selection
    	String choice;
    	Spaceport.Target target = new Spaceport.Target();
    	System.out.println("Enter Target Info");
    	System.out.println("1: Enter Target by Name");
		System.out.println("2: Enter Target by ID");
		System.out.println("3: Enter Target by x,y");
		choice = GetSingleInputFromUser("Enter a Selection");
		int selection = Integer.parseInt(choice);
		switch(selection){
		case 1:
			target.bodyName = GetSingleInputFromUser("Enter Target Name");
			break;
		case 2:
			target.bodyID = GetSingleInputFromUser("Enter Target ID");
			break;
		case 3:
			target.x = GetSingleInputFromUser("Enter x coordinate");
			target.y = GetSingleInputFromUser("Enter y coordinate");
			break;
		}

		//Ship Type Selection
		ArrayList<Response.Available> availableShips = GetShipsForTargetFromPlanet(planetID, target);
		HashSet<String> availableType = new HashSet<String>();
		if(availableShips.size() !=0){
			for(Response.Available a: availableShips){
				availableType.add(a.type);
			}
		}
		//int count = 0;
		if(availableType.size()!=0){ //this is to print out a selection menu
			for(String a: availableType){
				System.out.println(a);
			}
		}
		choice = GetSingleInputFromUser("Type in the ship name");
		Spaceport.Type type = new Spaceport.Type();
		for(Response.Available a: availableShips){
			if(a.type.contentEquals(choice)){
				type.combat = a.combat;
				type.speed = a.speed;
				type.stealth = a.stealth;
				type.type = a.type;
				type.quantity = GetSingleInputFromUser("Enter a number to send");
				break;
			}
		}
		Set<Spaceport.Type> t = new HashSet<Spaceport.Type>(); //adding type to a hashset that the spaceport method request, multiple different ship types can be sent
		t.add(type);

		//Arrival Time Selection
		System.out.println("Time selection is based on the server time and date not a projection of how far in the future, \n Day, Hour, Minute, Second");
		System.out.println("Be sure your arrival time is valid, otherwise the request will not go through");
		Spaceport.Arrival arrival = new Spaceport.Arrival();
		arrival.day = GetSingleInputFromUser("Enter the day of the month for the ships to arrive");
		arrival.hour = GetSingleInputFromUser("Enter the hour in 24 hour time");
		arrival.minute = GetSingleInputFromUser("Enter the minute");
		arrival.second = GetSingleInputFromUser("Enter the second");


		Spaceport spaceport = new Spaceport("spaceport");
		String request = spaceport.SendShipTypes(sessionID, planetID, target, type, arrival);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, spaceport.url, request);
		System.out.println(reply);

	/*
		//Spaceport.Arrival arrival = new Spaceport.Arrival();
		Spaceport.Type type = new Spaceport.Type();
		int d = 0;
		count = 0;
		for(Response.Available a: availableShips){
			System.out.println("Type Human "+a.type_human);
			System.out.println("Combat "+a.combat);
			System.out.println("Stealth "+a.stealth);
			if(a.type_human.equals("Scanner")){
				//System.out.println("found Scanner");
				type.combat = a.combat;
				type.speed = a.speed;
				type.stealth = a.stealth;
				type.type = a.type;
				//System.out.println
				d = 1;
			}
			if(a.type.equals("scanner"))
					count++;
		}
		type.quantity = "1";
		arrival.day = "28";
		arrival.hour = "10";
		arrival.minute = "5";
		arrival.second = "0";
		target.bodyName = "Omicron";
		Set<Spaceport.Type> t = new HashSet<Spaceport.Type>();
		t.add(type);
		String request = spaceport.SendShipTypes(sessionID, planetID, target, type, arrival);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, spaceport.url, request);
		System.out.println(reply);

}
*/
}
