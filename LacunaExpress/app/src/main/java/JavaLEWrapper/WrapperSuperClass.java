package JavaLEWrapper;

    import com.google.gson.Gson;
    import com.google.gson.stream.JsonWriter;
    import java.io.IOException;
    import java.io.StringWriter;

    class LESuperClass {
    //A few objects that are needed by all LE Wrapper classes for creating json
    protected Gson gson = new Gson();
    protected StringWriter w = new StringWriter();
    protected JsonWriter writer = new JsonWriter(w);

    protected String Request(String method, String sessionID, String id){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
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
    }
    protected String Request(String method, String sessionID, String id, String one){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
            writer.value(one);
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
    }
    protected String Request(String method, String sessionID, String id, String one, String two){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
            writer.value(one);
            writer.value(two);
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
    }

    protected String BasicRequest(String method, String sessionID, String id){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
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
    }
    protected String ThreePartRequest(String method, String sessionID, String id, String one){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
            writer.value(one);
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
    }
    protected String FourPartRequest(String method, String sessionID, String id, String one, String two){
        String b = "0";
        try{
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(1);
            writer.name("method").value(method);
            writer.name("params").beginArray();
            writer.value(sessionID);
            writer.value(id);
            writer.value(one);
            writer.value(two);
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
    }
    //A method to be used by all other LE Wrapper classes to create the first portion of requests
    protected void StartOfObject(int requestID, String Method){
        //System.out.println("starting writer");
        //JsonWriter writer = new JsonWriter();
        try{
            //writer = new JsonWriter(null);
            writer.beginObject();
            writer.name("jsonrpc").value(2);
            writer.name("id").value(requestID);
            writer.name("method").value(Method);
            writer.name("params").beginArray();
            //String u = gson.toJson(writer);
            //System.out.println(u);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IO Exception in start object");
        }

    }
    // This method is used for building the common methods that require only a session ID, and a building ID in params[]
    protected String SessionAndBuildingIDRequests(String sessionID, String buildingID){
        String b = "nothing";
        try {
            writer.value(sessionID);
            writer.value(buildingID);
            writer.endArray();
            writer.endObject();
            writer.close();
            b = gson.toJson(writer);
            b = CleanJsonObject(b);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
    //This method is for cleaning out the json string before sending to the server.  Gson leaves behind a lot of garbage after serializing
    protected String CleanJsonObject(String i){
        int start = i.indexOf("{\\");
        int end = i.indexOf("]}");
        i = i.substring(start, end+2);
        return i = i.replace("\\", "");
    }

}
