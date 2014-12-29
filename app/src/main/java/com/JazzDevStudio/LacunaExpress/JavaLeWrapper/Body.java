package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;


//import java.util.*;
//import java.util.regex.*;

public class Body extends LESuperClass {
    public static final String url = "body";
    public static String GetBuildings(int requestID, String sessionID, String bodyID){
        return BasicRequest("get_buildings", sessionID, bodyID );
    }

    //The below methods are not yet implemented
    void RepairList(int requestID, String SessionID, String BodyID, String ...buildings){}
    void RearrangeBuildings(int requestID, String SessionID, String BodyID, ArrangementForRearrangBuildings ...buildings){}
    void GetBuildable(int requestID, String SessionID, String BodyID, int x, int y){}
    void Rename(int requestID, String SessionID, String BodyID){}
    void Abandon(int requestID, String SessionID, String BodyID){}
    class ArrangementForRearrangBuildings{
        String id, x, y;
    }

}