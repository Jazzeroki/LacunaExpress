package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class Glyphinator {
    /*
     static void SendOutMaxExcavators(String planetID){
    	int archID = FindBuildingID("Archaeology Ministry", planets.get(planetID).buildings);
    	if(archID != 0){
    		Response.Building archMin = planets.get(planetID).buildings.get(archID);
    		if(Integer.parseInt(archMin.level) > 10){
    			Archaeology arch = new Archaeology("archaeology");
    			String request = arch.ViewExcavators(sessionID, String.valueOf(archID));
    			String reply = server.ServerRequest(gameServer, arch.url, request);
    			Response r = gson.fromJson(reply, Response.class);
    			int toSend = Integer.parseInt(archMin.level) - 10 - r.result.excavators.size();
    			if(toSend > 0){
    				Spaceport space = new Spaceport("spaceport");
    				int spID = FindBuildingID("Space Port", planets.get(planetID).buildings);
    				request = space.ViewAllShips(sessionID, String.valueOf(spID), "excavator");
    				reply = server.ServerRequest(gameServer, space.url, request);
    				r = gson.fromJson(reply, Response.class);
    				ArrayList<Response.Ship> ships = r.result.ships;
    				if(r.result.ships.size()!=0){
    					ArrayList<Stars> stars = GetAllBodiesInRange30(Integer.parseInt(planets.get(planetID).status.body.x), Integer.parseInt(planets.get(planetID).status.body.y));
    					ArrayList<String> possibleTargets = new ArrayList<String>();
    					for(Stars s: stars){
    						if(!CheckSystemHostile(s.bodies)){
    							for(Response.Bodies h: s.bodies)
    								if(h.empire == null &&(h.type.contentEquals("habitable planet")||h.type.contentEquals("asteroid"))){
    									System.out.println("adding to target list");
    									possibleTargets.add(h.id);
    								}
    						}
    					}
    					if(possibleTargets.size() !=0 && ships.size() > toSend){
    						int counter = 0;
    						for(String t: possibleTargets){
    							Spaceport.Target target = new Spaceport.Target();
    							target.bodyID = t;
    							space = new Spaceport("spaceport");
    							request = space.SendShip(sessionID, ships.get(counter).id, target);
    							reply = server.ServerRequest(gameServer, space.url, request);
    							//System.out.println(reply);
    							if(!reply.contentEquals("error"))
    								counter++;
    							if(counter == toSend)
    								break;
    						}

    					}
    					else
    						System.out.println("No possible targets in rang of 30 found, or insufficient excavators");
    					//request = map.
    				}
    				else
    					System.out.println("No excavators available, recomend building some first");
    			}
    			else
    				System.out.println("Maximum number of Excavators reached");
    		}
    		else
    			System.out.println("Archaeology Minstry must be level 11 or greater to use excavators");
    	}
    	else
    		System.out.println("No Archaeology Minstry found");
    }

     */
}
