package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
/**
 * Created by Alma on 1/27/2015.
 */
public class Spaceport extends Buildings {
    public static final String url = "spaceport";

    Spaceport(String buildingName) {
        super("spaceport");
        // TODO Auto-generated constructor stub
    }
    public static String ViewAllShips(String sessionID, String buildingID){
        String i = Request("view_all_ships", sessionID, buildingID);
        return i;
    }
    public static String ViewAllShips(String sessionID, String buildingID, String shipType){
        String i = "{\"id\":11,\"method\":\"view_all_ships\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+buildingID+"\",{\"no_paging\":1},{\"type\":\""+shipType+"\"},null]}";
        //String i = "{\"id\":11,\"method\":\"view_all_ships\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+buildingID+"\",{\"page_number\":1},null,null]}";
        return i;
    }
    public static String RecallAll(String sessionID, String buildingID){
        String i = Request("recall_all", sessionID, buildingID);
        return i;
    }
    public static String ViewForeignShips(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_foreign_ships", sessionID, buildingID, pageNumber);
        return i;
    }
    public static String GetFleetFor(String sessionID, String bodyID, String target){
        String i = Request("get_fleet_for", sessionID, bodyID, target);
        return i;
    }
    public static String GetShipsFor(String sessionID, String bodyID, Target target){
        String t = "";
        if(!target.bodyID.isEmpty()){
            t = "body_id\":\""+target.bodyID;
        }
        else if(!target.bodyName.isEmpty()){
            t = "body_name\":\""+target.bodyName;
        }
        else if(!target.starID.isEmpty()){
            t = "star_id\":\""+target.starID;
        }
        else if(!target.starName.isEmpty()){
            t = "star_name\":\""+target.starName;
        }
        else{
            t = "x\":\""+target.x+"\",\"y\":\""+target.y;
        }
        String b = "{\"id\":8,\"method\":\"get_ships_for\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+bodyID+"\",{\""+t+"\"}]}";
        return b;
    }

    public static String SendShip(String sessionID, String bodyID, Target target){
        String t = "";
        if(!target.bodyID.isEmpty()){
            t = "body_id\":\""+target.bodyID;
        }
        else if(!target.bodyName.isEmpty()){
            t = "body_name\":\""+target.bodyName;
        }
        else if(!target.starID.isEmpty()){
            t = "star_id\":\""+target.starID;
        }
        else if(!target.starName.isEmpty()){
            t = "star_name\":\""+target.starName;
        }
        else{
            t = "x\":\""+target.x+"\",\"y\":\""+target.y;
        }
        String b = "{\"id\":8,\"method\":\"send_ship\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+bodyID+"\",{\""+t+"\"}]}";
        return b;
    }
    public static String RecallShip(String sessionID, String buildingID, String shipID){
        String i = Request("recall_ship", sessionID, buildingID, shipID);
        return i;
    }
    public static String PrepareSendSpies(String sessionID, String onBodyID, String toBodyID){
        String i = Request("prepare_send_spies", sessionID, onBodyID, toBodyID);
        return i;
    }
    public static String PrepareFetchSpies(String sessionID, String onBodyID, String toBodyID){
        String i = Request("prepare_fetch_spies", sessionID, onBodyID, toBodyID);
        return i;
    }
    public static String ViewBattleLogs(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_battle_logs", sessionID, buildingID, pageNumber);
        return i;
    }
    public static String NameShip(String sessionID, String buildingID, String shipID, String name){
        String i = Request("name_ship", sessionID, buildingID, shipID, name);
        return i;
    }
    public static String ScuttleShip(String sessionID, String buildingID, String shipID, String name){
        String i = Request("scuttle_ship", sessionID, buildingID, shipID, name);
        return i;
    }
    public static String ViewShipsTraveling(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_ships_travelling", sessionID, buildingID, pageNumber);
        return i;
    }
    public static String ViewShipsOrbiting(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_ships_orbiting", sessionID, buildingID, pageNumber);
        return i;
    }
    public static String SendSpies(String sessionID, String onBodyID, String toBodyID, String shipID, ArrayList<String> spyIDs ){
        String b = "0";
        try{
            StringWriter w = new StringWriter();
            JsonWriter writer = new JsonWriter(w);
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("send_spies");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(onBodyID);
            writer.value(toBodyID);
            writer.value(shipID);
            writer.beginArray();
            for(String j: spyIDs)
                writer.value(j);
            writer.endArray();
            writer.endArray();
            writer.endObject();
            writer.close();
            b = gson.toJson(writer);
            b = CleanJsonObject(b);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return b;
    }
    public static String FetchSpies(String sessionID, String onBodyID, String fromBodyID, String shipID, ArrayList<String> spyIDs ){
        String b = "0";
        try{
            StringWriter w = new StringWriter();
            JsonWriter writer = new JsonWriter(w);
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("fetch_spies");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(onBodyID);
            writer.value(fromBodyID);
            writer.value(shipID);
            writer.beginArray();
            for(String j: spyIDs)
                writer.value(j);
            writer.endArray();
            writer.endArray();
            writer.endObject();
            writer.close();
            b = gson.toJson(writer);
            b = CleanJsonObject(b);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return b;
    }
    //String SendShipTypes(String sessionID, String fromBodyID, Target target, Set<Type> types, Arrival arrival){
    public static String SendShipTypes(String sessionID, String fromBodyID, Target target, Type types, Arrival arrival){
        String t = "";
        if(!target.bodyID.isEmpty()){
            t = "body_id\":\""+target.bodyID;
        }
        else if(!target.bodyName.isEmpty()){
            t = "body_name\":\""+target.bodyName;
        }
        else if(!target.starID.isEmpty()){
            t = "star_id\":\""+target.starID;
        }
        else if(!target.starName.isEmpty()){
            t = "star_name\":\""+target.starName;
        }
        else
            t = "x\":\""+target.x+"\",\"y\":\""+target.y;
        String a = "{\"day\":\""+arrival.day+"\",\"hour\":\""+arrival.hour+"\",\"minute\":\""+arrival.minute+"\",\"second\":\""+arrival.second+"\"}";
        String type = "";
        //type += "{\"type\":\"sweeper\",\"quantity\":\"1\"}";
        type  += "{\"type\":\""+types.type+"\",\"speed\":\""+types.speed+"\",\"stealth\":\""+types.stealth+"\",\"combat\":\""+types.combat+"\",\"quantity\":\""+types.quantity+"\"}";

        String b = "{\"id\":8,\"method\":\"send_ship_types\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+fromBodyID+"\",{\""+t+"\"},["+type+"],"+a+" ]}";
        return b;
    }
    public static String SendFleet(String sessionID, ArrayList<String> ships, Target target){
        String t = ""; //Sets the target
        if(!target.bodyID.isEmpty()){
            t = "body_id\":\""+target.bodyID;
        }
        else if(!target.bodyName.isEmpty()){
            t = "body_name\":\""+target.bodyName;
        }
        else if(!target.starID.isEmpty()){
            t = "star_id\":\""+target.starID;
        }
        else if(!target.starName.isEmpty()){
            t = "star_name\":\""+target.starName;
        }
        else{
            t = "x\":\""+target.x+"\",\"y\":\""+target.y;
        }

        String s =""; //Creates the ships list to send
        int count = ships.size();
        int counter = 0;
        for(String j: ships){
            s+=j;
            counter++;
            if(counter == count)
                s+= "\"";
            else
                s+= "\",\"";
        }
        String i = "{\"id\":15,\"method\":\"send_fleet\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",[\""+s+"],{\""+t+"\"},0]}";
        return i;
    }
    public static class Target{
        Target(){
            bodyName = "";
            bodyID = "";
            starName = "";
            starID = "";
            x = "";
            y = "" ;
        }
        String bodyName, bodyID, starName, starID, x , y;
    }
    public static class Type{
        Type(){
            type = "";
            speed = "";
            stealth = "";
            combat = "";
            quantity = "";
        }
        String type, speed, stealth, combat, quantity;
    }
    public static class Arrival{
        Arrival(){
            day = "";
            hour = "";
            minute = "";
            second = "";
        }
        String day, hour, minute, second;
    }
}
