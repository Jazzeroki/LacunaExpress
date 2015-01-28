package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 12/23/2014.
 */
public class Buildings extends LESuperClass{
    //String for URL will be the same as the building name
    public String url;
    Buildings(String buildingName){
        url = buildingName.toLowerCase();
        System.out.println(url);
        url = url.replace(" ", "");
        if (url.equals("archaeologyministry"))
            url = "archaeology";
        if (url.contains("beach"))
            url = "beach";
        if (url.contains("herder"))
            url = "beeldeban";
        if (url.contains("bean"))
            url = "bean";
        if (url.contains("nest"))
            url = "beeldebannest";
        if (url.contains("beach"))
            url = "beach";
        if (url.contains("bread"))
            url = "bread";
        if (url.contains("burger"))
            url = "burger";
        if (url.contains("capitol"))
            url = "capitol";
        if (url.contains("cheese"))
            url = "cheese";
        if (url.contains("chip"))
            url = "chip";
        if (url.contains("cider"))
            url = "cider";
        if (url.contains("cornplantation"))
            url = "corn";
        if (url.contains("cornmeal"))
            url = "meal";
        if (url.contains("dairy"))
            url = "dairy";
        if (url.contains("dentonroot"))
            url = "denton";
        if (url.contains("development"))
            url = "development";
        if (url.contains("espionageministry"))
            url = "espionage";
        if (url.contains("fission"))
            url = "fission";
        if (url.contains("fusion"))
            url = "fusion";
        if (url.contains("geoenergy"))
            url = "geo";
        if (url.contains("grove"))
            url = "grove";
        if (url.contains("hydrocarbon"))
            url = "hydrocarbon";
        if (url.contains("lapisorchard"))
            url = "lapis";
        if (url.contains("lostcityoftyleona"))
            url = "lcota";
        if (url.contains("lostcityoftyleonb"))
            url = "lcotb";
        if (url.contains("lostcityoftyleonc"))
            url = "lcotc";
        if (url.contains("lostcityoftyleond"))
            url = "lcotd";
        if (url.contains("lostcityoftyleone"))
            url = "lcote";
        if (url.contains("lostcityoftyleonf"))
            url = "lcotf";
        if (url.contains("lostcityoftyleong"))
            url = "lcotg";
        if (url.contains("lostcityoftyleonh"))
            url = "lcoth";
        if (url.contains("lostcityoftyleoni"))
            url = "lcoti";
        if (url.contains("malcudfungus"))
            url = "malcud";
        if (url.contains("oversight"))
            url = "oversight";
        if (url.contains("pancake"))
            url = "pancake";
        if (url.contains("pie"))
            url = "pie";
        if (url.contains("pilottraining"))
            url = "pilottraining";
        if (url.contains("planetarycommand"))
            url = "planetarycommand";
        if (url.contains("potatoe"))
            url = "potatoe";
        if (url.contains("propulsion"))
            url = "propulsion";
        if (url.contains("shieldagainst"))
            url = "saw";
        if (url.contains("security"))
            url = "security";
        if (url.contains("shake"))
            url = "shake";
        if (url.contains("shipyard"))
            url = "shipyard";
        if (url.contains("singularity"))
            url = "singularity";
        if (url.contains("syrup"))
            url = "syrup";
        if (url.contains("trade"))
            url = "trade";
        if (url.contains("transporter"))
            url = "transporter";
        if (url.contains("wasteenergy"))
            url = "wasteenergy";
        if (url.equals("entertainmentdistrict"))
            url = "entertainment";
        if (url.contains("wasterecycling"))
            url = "wasterecycling";
        if (url.contains("wastesequestration"))
            url = "wastesequestration";
        if (url.equals("intelligenceministry"))
            url="intelligence";
        if (url.equals("wastetreatment"))
            url="wastetreatment";
        if (url.equals("waterproduction"))
            url="waterproduction";
        if (url.equals("waterpurification"))
            url="waterpurification";
        if (url.equals("waterreclamation"))
            url="waterreclamation";
        if (url.equals("waterstorage"))
            url="waterstorage";
        if (url.equals("wheat"))
            url="wheat";
        if (url.equals("spacestationlaba"))
            url="ssla";
        if (url.equals("spacestationlabb"))
            url="sslb";
        if (url.equals("spacestationlabc"))
            url="sslc";
        if (url.equals("spacestationlabd"))
            url="ssld";

    }
    public static String Build(String sessionID, String bodyID, String x, String y){
        return Request("build",sessionID, bodyID, x, y);
    }
    public static String View(String sessionID, String buildingID){
        StartOfObject(1, "view");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
    }
    public static String Upgrade(String sessionID, String buildingID){
        StartOfObject(1, "upgrade");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
    }
    public static String Demolish(String sessionID, String buildingID){
        String i = null;
        StartOfObject(1, "demolish");
        i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
    }
    public static String Downgrade(String sessionID, String buildingID){
        String i = null;
        StartOfObject(1, "downgrade");
        i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
    }
    public static String Repair(String sessionID, String buildingID){
        return Request("repair", sessionID, buildingID);
        //String i = BasicRequest("repair", sessionID, buildingID);
        //return i;
    }
	 /* Building Methods


get_stats_for_level ( session_id, building_id, level )
session_id
building_id
level

	 *
	 */
}
