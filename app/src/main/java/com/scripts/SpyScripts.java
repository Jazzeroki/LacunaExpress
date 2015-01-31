package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class SpyScripts {
    /*
    //Spy Methods
    static ArrayList<Spies> GetSpies(String planetID){
    	int bID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	Intelligence intel = new Intelligence("intelligence");
    	String request = intel.ViewAllSpies(sessionID, String.valueOf(bID));
    	String reply = server.ServerRequest(gameServer, intel.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.spies;
    }
    static String SpyAssignmentSelectionMenu(){

    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		System.out.println("Select spy assignment");
    		System.out.println("1: Idle");
    		System.out.println("2: Counter Espionage");
    		System.out.println("3: Security Sweep");
    		System.out.println("4: Intel Training");
    		System.out.println("5: Mayhem Training");
    		System.out.println("6: Politics Training");
    		System.out.println("7: Theft Training");
    		System.out.println("8: Political Propaganda");
    		System.out.println("9: Gather Resource Intelligence");
    		System.out.println("10: Gather Empire Intelligence");
    		System.out.println("11: Gather Operative Intelligence");
    		System.out.println("12: Gather Operative Intelligence");
    		System.out.println("13: Sabotage Probes");
    		System.out.println("14: Rescue Comrades");
    		System.out.println("15: Sabotage Resources");
    		System.out.println("16: Appropriate Resources");
    		System.out.println("17: Assassinate Operatives");
    		System.out.println("18: Sabotage Infrastructure");
    		System.out.println("19: Sabotage BHG");
    		System.out.println("20: Incite Mutiny");
    		System.out.println("21: Abduct Operatives");
    		System.out.println("22: Appropriate Technology");
    		System.out.println("23: Incite Rebellion");
    		System.out.println("24: Incite Insurrection");
    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				return "idle";
				case 2:
					return "Counter Espionage";
				case 3:
					return "Security Sweep";
				case 4:
					return "Intel Training";
				case 5:
					return "Mayhem Training";
				case 6:
					return "Politics Training";
				case 7:
					return "Theft Training";
				case 8:
					return "Political Propaganda";
				case 9:
					return "Gather Resource Intelligence";
				case 10:
					return "Gather Empire Intelligence";
				case 11:
					return "Gather Operative Intelligence";
				case 12:
					return "Hack Network 19";
				case 13:
					return "Sabotage Probes";
				case 14:
					return "Rescue Comradese";
				case 15:
					return "Sabotage Resources";
				case 16:
					return "Appropriate Resources";
				case 17:
					return "Assassinate Operatives";
				case 18:
					return "Sabotage Infrastructure";
				case 19:
					return "Sabotage BHG";
				case 20:
					return "Incite Mutiny";
				case 21:
					return "Abduct Operatives";
				case 22:
					return "Appropriate Technology";
				case 23:
					return "Incite Rebellion";
    			case 24:
    				return "Incite Insurrection";
    			default:
    				System.out.println("Invalid Selection");
    			}


    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}

    	}while(i==0);
    	//input.close();
		return null;
    }
    static void AssignAllSpies(ArrayList <Spies> spies, String planetID, String assignment){
    	if(captchaValid==false)
    		Captcha();
    	int bID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	String request, reply;

    	for(Spies s: spies){//only assigns spies that are available
    		if(s.is_available.contentEquals("1")){
    			Intelligence intel = new Intelligence("intelligence");
    			request = intel.AssignSpy(sessionID, String.valueOf(bID), s.id, assignment);
    			reply = server.ServerRequest(gameServer, intel.url, request);
    		}

    	}
    }
    static void AssignSpiesToTrain(String planetID){
    	Captcha();
    	System.out.println("This assigns an equal number of spies to each type of training \nand all remaining and max level spies on the planet to Counter Espionage");
    	//this method is still under development and has lots of test code.  It doesn't do anything really yet.
    	String numb = GetSingleInputFromUser("Enter the number of spies to have training in each assignment");
    	int numbToTrain = Integer.parseInt(numb);
    	//ArrayList<String> intelTraining, politicalTraining, theftTraining, mayhemTraining, counter;
    	ArrayList<Spies> spyList = GetSpies(planetID);
    	int buildingID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	int intelCounter = 0;
    	int politicalCounter = 0;
    	int theftCounter = 0;
    	int mayhemCounter = 0;
    	int counterEsp = 0;
    	for(Spies s: spyList){
    		if(s.assigned_to.body_id.contentEquals(planetID) && s.is_available.contentEquals("1")){
    		if(Integer.parseInt(s.level) >= 78 && !s.assignment.contentEquals("Counter Espionage")){
    			//if spy is already max level and not already on counter espionage
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Counter Espionage");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			counterEsp++;
    			System.out.println("Assigning "+s.name+" to Counter Espionage");
    		}
    		else if(Integer.parseInt(s.mayhem) < 2600 && mayhemCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Mayhem Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			mayhemCounter++;
    			System.out.println("Assigning "+s.name+" to Mayhem Training");
    		}
    		else if(Integer.parseInt(s.intel) < 2600 && intelCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Intel Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			intelCounter++;
    			System.out.println("Assigning "+s.name+" to Intel Training");
    		}
    		else if(Integer.parseInt(s.politics) < 2600 && politicalCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Political Propaganda");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			politicalCounter++;
    		}
    		else if(Integer.parseInt(s.theft) < 2600 && theftCounter < numbToTrain){
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Theft Training");
    			//System.out.println(request);
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			//System.out.println(reply);
    			theftCounter++;
    			System.out.println("Assigning "+s.name+" to Theft Training");
    		}
    		else{
    			Intelligence intel = new Intelligence("intelligence");
    			String request = intel.AssignSpy(sessionID, String.valueOf(buildingID), s.id, "Counter Espionage");
    			String reply = server.ServerRequest(gameServer, intel.url, request);
    			counterEsp++;
    			System.out.println("Assigning "+s.name+" to Counter Espionage");
    		}
    		}

    	}
    	System.out.println(2600/(30/numbToTrain)+" is the approximate number of hours until max training is reached \nif training building is level 30");
    }
    static void SendSpies(String planetID){
    	Captcha();
    	String toBodyID = GetSingleInputFromUser("Enter target body ID");

    	Spaceport spaceport = new Spaceport("spaceport");
    	String request = spaceport.PrepareSendSpies(sessionID, planetID, toBodyID);
    	System.out.println(request);
    	String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	System.out.println(reply);
    	Response r = gson.fromJson(reply, Response.class);
    	System.out.println("Max number of spies available to send "+r.result.spies.size());

    	String maxToSend = GetSingleInputFromUser("Enter the maximum number of spies to send");
    	String minimumLevel = GetSingleInputFromUser("Enter the minimum Level of the spies to send/nMax level is 78");
    	ArrayList<String> spiesToSend = new ArrayList<String>();
    	if(r.result.spies.size() != 0){
    		int send = Integer.parseInt(maxToSend);
    		int minLevel = Integer.parseInt(minimumLevel);
    		int counter = 0;
    		for(Spies s: r.result.spies){
    			if(counter == send)
    				break;
    			else{
    				if(Integer.parseInt(s.level) >= minLevel){
    					spiesToSend.add(s.id);
    				}
    			}

    		}
    		if(spiesToSend.size() != 0){
    			String shipID;
    			for(Response.Ship s: r.result.ships){
    				if(s.type.contentEquals("smugglership")){
    					shipID = s.id;
    					spaceport = new Spaceport("spaceport");
    			    	request = spaceport.SendSpies(sessionID, planetID, toBodyID, shipID, spiesToSend);
    			    	System.out.println(request);
    			    	reply = server.ServerRequest(gameServer, spaceport.url, request);
    			    	System.out.println(reply);
    			    	System.out.println(spiesToSend.size()+" Spies sent");
    			    	break;
    				}
    			}
    		}
    		else
    			System.out.println("No spies meet the requirements to send");
    	}

    }
    static void SpyRun(){

    }
    static void FetchSpies(String planetID){
    	//Spaceport spaceport = new Spaceport("spaceport");
    	//String request = spaceport.PrepareFetchSpies(sessionID, planetID, fromBodyID);
    	//System.out.println(request);
    	//String reply = server.ServerRequest(gameServer, spaceport.url, request);
    	//System.out.println(reply);
    }
    static void TrainNewSpies(String planetID){
    	Intelligence intelligence = new Intelligence("intelligence");
    	int buildingID = FindBuildingID("Intelligence Ministry", planets.get(planetID).buildings);
    	//String request = intelligence.ViewAllSpies(sessionID, buildingID);
    }
    static void SetSpiesMinistryTraining(){

    }

    static void ExecuteAllPrisoners(String planetID){
    	int bID = FindBuildingID("Security Ministry", planets.get(planetID).buildings);
		boolean i = true;
		do{
			Security security = new Security("security");
	    	String request = security.ViewPrisoners(sessionID, String.valueOf(bID), "1");
			String reply = server.ServerRequest(gameServer, security.url, request);
			Response r = gson.fromJson(reply, Response.class);
			if(r.result.captured_count.contentEquals("0"))
				i=false;
			else{
				for(Response.Prisoner p: r.result.prisoners){
					Security s = new Security("security");
					request = s.ExecutePrisoner(sessionID, String.valueOf(bID), p.id);
					server.ServerRequest(gameServer, security.url, request);
					System.out.println("Prisoner: "+p.name+" Executed for "+p.task);
				}
			}
		}while(i == true);
		//if()
		//Nothing useful is returned in the reply of execution

    	//result.captured_count shows how many prisoners are on the planet
    }
     */
    static void ExecuteAllPrisoners(String displayName, String planetName){

    }
}
