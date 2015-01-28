package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 1/27/2015.
 */
public class Shipyard extends Buildings{
    public static final String url = "shipyard";
    Shipyard() {
        super("shipyard");
        // TODO Auto-generated constructor stub
    }
    public static String BuildShip(String sessionID, String buildingID, String type){
        return Request("build_ship", buildingID, type);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("build_ship");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(type);
            writer.endArray();
            writer.endObject();
            writer.close();
            b = gson.toJson(writer);
            //writer.flush();
            b = CleanJsonObject(b);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return b;*/
    }
    public static String BuildShip(String sessionID, String buildingID, String type, String numberToBuild){
        return Request("build_ship", buildingID, type, numberToBuild);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("build_ship");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(type);
            writer.value(numberToBuild);
            writer.endArray();
            writer.endObject();
            writer.close();
            b = gson.toJson(writer);
            //writer.flush();
            b = CleanJsonObject(b);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return b;*/
    }
}
/*
view_build_queue ( session_id, building_id, [page_number])

subsidize_build_queue ( session_id, building_id )

subsidize_ship ( parameter_hash )
session_id (required)
building_id (required)
ship_id (required)
RESPONSE
get_buildable ( session_id, building_id, [ tag ] )

build_ship ( session_id, building_id, type, [ quantity ] )
 */