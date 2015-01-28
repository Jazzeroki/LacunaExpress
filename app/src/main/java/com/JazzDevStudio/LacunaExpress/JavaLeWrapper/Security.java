package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 1/27/2015.
 */
public class Security extends Buildings {
    public static final String url = "security";
    Security(String buildingName) {
        super("security");
        // TODO Auto-generated constructor stub
    }
    public static String ViewPrisoners(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_prisoners", sessionID, buildingID, pageNumber);
        return i;
    }
    public static String ExecutePrisoner(String sessionID, String buildingID, String prisonerID){
        String i = Request("execute_prisoner", sessionID, buildingID, prisonerID);
        return i;
    }
    public static String ReleasePrisoner(String sessionID, String buildingID, String prisonerID){
        String i = Request("release_prisoner", sessionID, buildingID, prisonerID);
        return i;
    }
    public static String ViewForeignSpies(String sessionID, String buildingID, String pageNumber){
        String i = Request("view_foreign_spies", sessionID, buildingID, pageNumber);
        return i;
    }
}
