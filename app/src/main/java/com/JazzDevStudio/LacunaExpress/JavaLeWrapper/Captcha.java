package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

import android.util.Log;

/**
 * Created by Alma on 1/27/2015.
 */
public class Captcha extends  LESuperClass{
    public static final String url = "captcha";
    public static String Fetch(String sessionID){
        return Request("fetch", sessionID);
        /*
        StartOfObject(1, "fetch");
        String i = "0";
        try{
            writer.value(SessionID);
            //writer.value(BodyID);
            writer.endArray();
            writer.endObject();
            writer.close();
            i = gson.toJson(writer);
            //writer.flush();
            i = CleanJsonObject(i);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return i;
        */
    }
    public static String Solve(String sessionID, String guid, String solution){
        Log.d("Solve", "creating string");
        return Request("solve", sessionID,"1", guid, solution);

        /*StartOfObject(1, "solve");
        String i = "0";
        try{
            writer.value(SessionID);
            writer.value(guid);
            writer.value(solution);
            writer.endArray();
            writer.endObject();
            writer.close();
            i = gson.toJson(writer);
            //writer.flush();
            i = CleanJsonObject(i);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        }
        return i; */
    }

}
