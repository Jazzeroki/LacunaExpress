package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 1/27/2015.
 */
public class Intelligence extends Buildings {
    public static final String url = "intelligence";
    Intelligence(String buildingName) {
        super(buildingName); //may need to input intelligence
        // TODO Auto-generated constructor stub
    }
    //String url = "intelligence";
    public static String View(String sessionID, String buildingID){
        return Request("view", sessionID, buildingID);
        /*StartOfObject(1, "view");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;*/
    }
    public static String SubsidizeTraining(String sessionID, String buildingID){
        return Request("subsidize_training", sessionID, buildingID);
        /*StartOfObject(1, "subsidize_training");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;*/
    }
    public static String ViewAllSpies(String sessionID, String buildingID){
        return Request("view_all_spies", sessionID, buildingID);
        /*StartOfObject(1, "view_all_spies");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;*/
    }
    public static String AssignSpy(String sessionID, String buildingID, String spyID, String assignment ){
        return Request("assign_spy", sessionID, buildingID, assignment);
        /*
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("assign_spy");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(spyID);
            writer.value(assignment);
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
    public static String NameSpy(String sessionID, String buildingID, String spyID, String name){
        return Request("name_spy", sessionID, buildingID, spyID, name);
        /*
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("name_spy");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(spyID);
            writer.value(name);
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
        return b;
        */
    }
    public static String BurnSpy(String sessionID, String buildingID, String spyID){
        return Request("burn_spy", sessionID, buildingID, spyID);
        /*
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("burn_spy");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(spyID);
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
    public static String TrainSpy(String sessionID, String buildingID, String quantity){
        return Request("train_spy", sessionID, buildingID, quantity);
        /*
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("train_spy");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(quantity);
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
	/*Intelligence Methods
view ( session_id, building_id )
train_spy ( session_id, building_id, [ quantity ])
session_id
building_id
quantity
view_spies ( session_id, building_id, [ page_number ] )
session_id
building_id
page_number
burn_spy ( session_id, building_id, spy_id )
session_id
building_id
spy_id
name_spy ( session_id, building_id, spy_id, name )
session_id
building_id
spy_id
name
assign_spy ( session_id, building_id, spy_id, assignment )
session_id
building_id
spy_id
assignment
	 *
	 *
	 * assignment

assignment

A string containing the new assignment name. These are the possible assignments:

Idle
Don't do anything.

Bugout
Only visible on non-home planets. Immediately has agent go to their home base via spypod.

Counter Espionage.
Passively defend against all attackers.

Security Sweep
Round up attackers.

Intel Training
Train in Intelligence skill

Mayhem Training
Train in Mayhem skill

Politics Training
Train in Politics skill

Theft Training
Train in Theft skill

Political Propaganda
Give happiness generation a boost. Especially effective on unhappy colonies, but hastens an agent toward retirement. Only usuable on owned planets.

Gather Resource Intelligence
Find out what's up for trade, what ships are available, what ships are being built, where ships are travelling to, etc.

Gather Empire Intelligence
Find out what is built on this planet, the resources of the planet, what other colonies this Empire has, etc.

Gather Operative Intelligence
Find out what spies are on this planet, where they are from, what they are doing, etc.

Hack Network 19
Attempts to besmirch the good name of the empire controlling this planet, and deprive them of a small amount of happiness.

Sabotage Probes
Destroy probes controlled by this empire.

Rescue Comrades
Break spies out of prison.

Sabotage Resources
Destroy ships being built, docked, en route to mining platforms, etc.

Appropriate Resources
Steal empty ships, ships full of resources, ships full of trade goods, etc.

Assassinate Operatives
Kill spies.

Sabotage Infrastructure
Destroy buildings.

Sabotage Defenses
Destroy buildings that are used in defense.

Sabotage BHG
Prevent enemy planet from using Black Hole Generator.

Incite Mutiny
Turn spies. If successful they come work for you.

Abduct Operatives
Kidnap a spy and bring him back home.

Appropriate Technology
Steal plans for buildings that this empire has built, or has in inventory.

Incite Rebellion
Obliterate the happiness of a planet. If done long enough, it can shut down a planet.

Incite Insurrection
Steal a planet.

NOTE: You can do bad things to allies using these assignments.


	 */
}
