package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

/**
 * Created by Alma on 1/27/2015.
 */
public class Archaeology extends Buildings {
    public static final String url = "archaeology";
    Archaeology(String buildingName) {
        super("archaeology");
    }
    public static String SubsidizeSearch(String sessionID, String buildingID){
        return Request("subsidize_search", sessionID, buildingID);
        /*StartOfObject(1, "subsidize_search");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
        */
    }
    public static String GetGlyphs(String sessionID, String buildingID){
        return Request("get_glyphs", sessionID, buildingID);
        /*StartOfObject(1, "get_glyphs");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
        */
    }
    public static String GetGlyphSummary(String sessionID, String buildingID){
        return Request("get_glyph_summary", sessionID, buildingID);
        /*
        StartOfObject(1, "get_glyph_summary");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
        */
    }
    public static String GetOresAvailableForProcessing(String sessionID, String buildingID){
        return Request("get_ores_available_for_processing", sessionID, buildingID);
        /*StartOfObject(1, "get_ores_available_for_processing");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
        */
    }
    public static String ViewExcavators(String sessionID, String buildingID){
        return Request("view_excavators", sessionID, buildingID);
        /*
        StartOfObject(1, "view_excavators");
        String i = SessionAndBuildingIDRequests(sessionID, buildingID);
        return i;
        */
    }
    public static String SearchForGlyph(String sessionID, String buildingID, String oreType){
        return Request("search_for_glyph", sessionID, buildingID, oreType);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("search_for_glyph");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(oreType);
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
    public static String AbandonExcavator(String sessionID, String buildingID, String siteID){
        return Request("abandon_excavator", sessionID, buildingID, siteID);
        /*String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("abandon_excavator");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            writer.value(siteID);
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
    public static String AssembleGlyphs(String sessionID, String buildingID, int quantity, String... glyphs){
        return null;
        /*
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value("assemble_glyphs");
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(buildingID);
            for(String i: glyphs)
                writer.value(i);
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
        return b;
        */
    }

}
