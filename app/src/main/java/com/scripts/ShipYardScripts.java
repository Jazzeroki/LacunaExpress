package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class ShipYardScripts {
    /*
    //Shipyard methods
    static void MenuFillShipyardsWith(String planetID){
    	int i = 0;
    	int control = 0;
    	Scanner input = new Scanner(System.in);
    	do{
    		System.out.println("Fill shipyards with:");
        	System.out.println("1: Snark3");
        	System.out.println("2: Sweeper");
        	System.out.println("3: Fighter");
        	System.out.println("4: Scow");
        	System.out.println("5: Scow Fast");
        	System.out.println("6: Scow Large");
        	System.out.println("7: Scow Mega, Warning these take a long time to build");
        	System.out.println("8: Fissure Sealer");
        	System.out.println("9: Excavator");
        	System.out.println("10: Hulk Fast");
        	System.out.println("11: Hulk Huge");
        	System.out.println("12: detonator");
        	System.out.println("13: Smuggler Ship");
        	System.out.println("14: Placebo 6");
        	System.out.println("15: Bleeder");
        	System.out.println("16: Thud");
        	System.out.println("17: Sec. Min. Seeker");
        	System.out.println("18: Supply Pod 4");
        	System.out.println("19: Scanner");
        	System.out.println("0: Return to previous menu");

    		try{
    			control = input.nextInt();
    			switch(control){
    			case 1:
    				//System.out.println("Starting to build Snark 3's");
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "snark3");
    				break;
    			case 2:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "sweeper");
    				break;
    			case 3:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "fighter");
    				break;
    			case 4:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow");
    				break;
    			case 5:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_fast");
    				break;
    			case 6:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_large");
    				break;
    			case 7:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scow_mega");
    				break;
    			case 8:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "fissure_sealer");
    				break;
    			case 9:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "excavator");
    				break;
    			case 10:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "hulk_fast");
    				break;
    			case 11:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "hulk_huge");
    				break;
    			case 12:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "detonator");
    				break;
    			case 13:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "smuggler_ship");
    				break;
    			case 14:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "placebo6");
    				break;
    			case 15:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "bleeder");
    				break;
    			case 16:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "thud");
    				break;
    			case 17:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "security_ministry_seeker");
    				break;
    			case 18:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "supply_pod4");
    				break;
    			case 19:
    				FillAllShipyardsWithShipTypeOnePlanet(planetID, "scanner");
    				break;
    			case 0:
    				PlanetControlsMenu();
    				break;
    			default:
    				System.out.println("Invalid Selection");
    			}

    		}catch(InputMismatchException e){
    			System.out.println("Not a valid selection.");
    		}
    	}while(i==0);
    	//input.close();

    }
    static void FillAllShipyardsWithShipTypeOnePlanet(String planetID, String shipType){
    	ArrayList<Integer> shipyards = FindAllBuildingIDs("Shipyard", planets.get(planetID).buildings);
    	String buildingLevel, request, reply;
    	int shipCount = 0;
    		for(int bID: shipyards){
    			//System.out.println(bID);
    			Shipyard shipyard = new Shipyard();
    			buildingLevel = planets.get(planetID).buildings.get(bID).level;
    			request = shipyard.BuildShip(sessionID, String.valueOf(bID), shipType, Integer.valueOf(buildingLevel));
    			reply = server.ServerRequest(gameServer, shipyard.url, request);
    			shipCount += Integer.valueOf(buildingLevel);
    		}
    	System.out.println(shipCount +" "+ shipType +" now under construction on "+ planetList.get(planetID));
    }

     */
}
