package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 1/27/2015.
 */
public class Map extends LESuperClass {
    public static final String url = "map";
    public static String CheckStarForIncomingProbe(String sessionID, String starID){
        return Request("check_star_for_incoming_probe", sessionID, starID);
        /*StartOfObject(1, "check_star_for_incoming_probe");
        String i = SessionAndBuildingIDRequests(sessionID, starID);
        return i;*/
    }
    public static String GetStar(String sessionID, String starID){
        return Request("get_star", sessionID, starID);
        /*
        StartOfObject(1, "get_star");
        String i = SessionAndBuildingIDRequests(sessionID, starID);
        return i; */
    }
    public static String GetStarByName(String sessionID, String name){
        return Request("get_star_by_name", sessionID, name);
        /*StartOfObject(1, "get_star_by_name");
        String i = SessionAndBuildingIDRequests(sessionID, name);
        return i;*/
    }
    public static String SearchStars(String sessionID, String name){
        return Request("search_stars", sessionID, name);
        /*StartOfObject(1, "search_stars");
        String i = SessionAndBuildingIDRequests(sessionID, name);
        return i;*/
    }
    public static String GetStars(String sessionID, String x1, String x2, String y1, String y2 ){
        return Request("get_stars", sessionID, x1, x2, y1, y2);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("get_stars");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(x1);
            writer.value(x2);
            writer.value(y1);
            writer.value(y2);
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
        return b; */
    }
    public static String GetStarsByXY(String sessionID, String x1, String y1){
        return Request("get_stars_by_xy", sessionID, x1, y1);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("get_stars_by_xy");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(x1);
            writer.value(y1);
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
        return b; */
    }
}
