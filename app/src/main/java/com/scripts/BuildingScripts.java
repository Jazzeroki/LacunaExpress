package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class BuildingScripts {
    /*
     static void SpaceportSpammer(String planetID){
    	//String bodyID = "432750";
    	int x = -5;
    	int y = 5;
    	do{
    		if(x != 0 && y != 0){
    			Spaceport spaceport = new Spaceport("spaceport");
    			String request = spaceport.Build(sessionID, planetID, String.valueOf(x), String.valueOf(y));
    			System.out.println(request);
    			String reply = server.ServerRequest(gameServer, spaceport.url, request);
    			System.out.println(reply);
    		}
    		x++;
    		if(x==6){
    			x=-5;
    			y--;
    		}
    	}while(y!=-6);
    }



    static void PrintAllBuildingsOnPlanet(String planetIDNumber){
    	HashMap<Integer, Response.Building> buildings = planets.get(planetIDNumber).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	//String bnumber;
    	//int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		//bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		System.out.println(b.name);
    	}
    }

    static void RepairBuilding(String buildingID, String buildingName){
    	buildingName.toLowerCase();
    	buildingName.replace(" ", "");
    	//String y = buildingName.toLowerCase();
    	//y = y.replace(" ", "");
    	//System.out.println(buildingName);
		Buildings b = new Buildings(buildingName);
		String request = b.Repair(sessionID, buildingID);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, b.url, request);
		System.out.println(reply);
		//Response r = gson.fromJson(reply, Response.class);
    }
    static void UpgradeBuilding(String buildingID, String buildingName){
    	buildingName.toLowerCase();
    	buildingName.replace(" ", "");
    	//String y = buildingName.toLowerCase();
    	//y = y.replace(" ", "");
    	//System.out.println(buildingName);
		Buildings b = new Buildings(buildingName);
		String request = b.Upgrade(sessionID, buildingID);
		System.out.println(request);
		String reply = server.ServerRequest(gameServer, b.url, request);
		System.out.println(reply);
		//Response r = gson.fromJson(reply, Response.class);
    }
    static void RepairAllPlanetBuildings(String planetIDNumber){
    	//dumbly iterates through all buildings on a planet attempting repairs if efficiency is less than 0
    	//Will save lost city for last as it's repairs are the most expensive
    	HashMap<Integer, Response.Building> buildings = planets.get(planetIDNumber).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.efficiency)<100){
    			System.out.println("Repairing Building: "+b.name);
    			RepairBuilding(bnumber,b.name );
    			buildingsRepaired++;
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
    //static void RepairAllBuildings(){}
    static void RepairGlyphBuildings(String planetID){
    	HashMap<Integer, Response.Building> buildings = planets.get(planetID).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.efficiency)<100){
    			if(b.name.contentEquals("Interdimensional Rift")
    					||b.name.contentEquals("Pyramid Junk Sculpture")
    					||b.name.contentEquals("Black Hole Generator")
    					||b.name.contentEquals("Citadel of Knope")
    					||b.name.contentEquals("Denton Brambles")
    					||b.name.contentEquals("Ravine")
    					||b.name.contentEquals("Oracle of Anid")
    					||b.name.contentEquals("Gratch's Gauntlet")
    					||b.name.contentEquals("Crashed Ship Site")
    					||b.name.contentEquals("Natural Spring")
    					||b.name.contentEquals("Geo Thermal Vent")
    					||b.name.contentEquals("Algea Pond")
    					||b.name.contentEquals("Volcano")
    					||b.name.contentEquals("Beeldeban Nest")
    					||b.name.contentEquals("Junk Henge Sculpture")
    					||b.name.contentEquals("Great Ball of Junk")
    					||b.name.contentEquals("Metal Junk Arches")
    					||b.name.contentEquals("Kalavian Ruins")
    					||b.name.contentEquals("Malcud Field")
    					||b.name.contentEquals("Temple of the Drajilites")
    					||b.name.contentEquals("Space Junk Park")
    					||b.name.contentEquals("Pantheon of Hagness")
    					){
    				System.out.println("Repairing Building: "+b.name);
    				RepairBuilding(bnumber,b.name );
    				buildingsRepaired++;
    			}
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
    static void UpgradeGlyphBuildings(String planetID){
    	//Copied code from repair since it'll be similar
    	HashMap<Integer, Response.Building> buildings = planets.get(planetID).buildings;
    	Set<Integer> buildingkeys = buildings.keySet();
    	Response.Building b;
    	String bnumber;
    	int buildingsRepaired = 0;
    	for(Integer j: buildingkeys){
    		bnumber = String.valueOf(j);
    		b = buildings.get(j);
    		if(Integer.parseInt(b.level)<30){
    			if(b.name.contentEquals("Interdimensional Rift")
    					//||b.name.contentEquals("Pyramid Junk Sculpture")
    					||b.name.contentEquals("Black Hole Generator")
    					||b.name.contentEquals("Citadel of Knope")
    					||b.name.contentEquals("Denton Brambles")
    					||b.name.contentEquals("Ravine")
    					||b.name.contentEquals("Oracle of Anid")
    					||b.name.contentEquals("Gratch's Gauntlet")
    					||b.name.contentEquals("Crashed Ship Site")
    					||b.name.contentEquals("Natural Spring")
    					||b.name.contentEquals("Geo Thermal Vent")
    					||b.name.contentEquals("Algea Pond")
    					||b.name.contentEquals("Volcano")
    					||b.name.contentEquals("Beeldeban Nest")
    					//||b.name.contentEquals("Junk Henge Sculpture")
    					//||b.name.contentEquals("Great Ball of Junk")
    					//||b.name.contentEquals("Metal Junk Arches")
    					||b.name.contentEquals("Kalavian Ruins")
    					||b.name.contentEquals("Malcud Field")
    					||b.name.contentEquals("Temple of the Drajilites")
    					//||b.name.contentEquals("Space Junk Park")
    					||b.name.contentEquals("Pantheon of Hagness")
    					){
    				System.out.println("Repairing Building: "+b.name);
    				UpgradeBuilding(bnumber,b.name );
    				buildingsRepaired++;
    			}
    		}
    	}
    	System.out.println(buildingsRepaired + " buildings repaired");
    }
     */
}
