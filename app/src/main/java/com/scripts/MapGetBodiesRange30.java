package com.scripts;

/**
 * Created by Alma on 1/30/2015.
 */
public class MapGetBodiesRange30 {
    /*
    static ArrayList<Stars> GetAllBodiesInRange30(int x, int y){
    	int x1, x2, y1, y2;
    	x1 = x-15;
    	x2 = x+15;
    	y1 = y-15;
    	y2 = y+15;
    	Map map = new Map();
    	String request = map.GetStars(sessionID, Integer.toString(x1), Integer.toString(y1), Integer.toString(x2), Integer.toString(y2));
    	String reply = server.ServerRequest(gameServer, map.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.stars;
    }

      static ArrayList<Stars> GetAllBodiesInRange(int centerx, int centery, int range){
    	//method to support getting stars by a much larger range still in development

    	int x1, x2, y1, y2;
    	x1 = centerx-15;
    	x2 = centerx+15;
    	y1 = centery-15;
    	y2 = centery+15;
    	Map map = new Map();
    	String request = map.GetStars(sessionID, Integer.toString(x1), Integer.toString(y1), Integer.toString(x2), Integer.toString(y2));
    	String reply = server.ServerRequest(gameServer, map.url, request);
    	Response r = gson.fromJson(reply, Response.class);
    	return r.result.stars;
    }
    static boolean CheckSystemHostile(ArrayList<Response.Bodies> bodies){

    	for(Response.Bodies b: bodies){
    		System.out.println("checking if null");
    		if(b.empire != null){
    			System.out.println("checking if hostile");
    			if(b.empire.alignment.contentEquals("hostile"))
    				return true;
    		}
    	}
    	return false;
    }
     */
}
