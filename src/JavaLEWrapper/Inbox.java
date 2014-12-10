package JavaLEWrapper;

import java.io.IOException;
import java.util.ArrayList;

public class Inbox extends LESuperClass {
    public static final String url = "inbox";

    public static String ViewInbox(String sessionID, String tag ){
        return "{\"jsonrpc\":2,\"id\":1,\"method\":\"view_inbox\",\"params\":[\""+sessionID+"\",{\"tags\":[\""+tag+"\"],\"page_number\":1}]}";
    }
    public static String ViewInbox(String sessionID, String tag, int pageNumber ){
        return "{\"jsonrpc\":2,\"id\":1,\"method\":\"view_inbox\",\"params\":[\""+sessionID+"\",{\"tags\":[\""+tag+"\"],\"page_number\":"+pageNumber+"}]}";
    }
    String ReadMessage(int RequestID, String sessionID, String MessageID){
    	return Request("read_message", sessionID, String.valueOf(RequestID), MessageID);
        /*StartOfObject(RequestID, "read_message");
        String i = "nothing";
        try {
            writer.value(sessionID);
            writer.value(MessageID);
            writer.endArray();
            writer.endObject();
            writer.close();
            i = gson.toJson(writer);
            System.out.println(i);
            i = CleanJsonObject(i);

        } catch (IOException e) {
            System.out.println("Exception in read_message");
            i = "request build failed";
            e.printStackTrace();
        }
        return i; */
    }
    String SendMessage(int requestID, String sessionID, ArrayList<String> recipients, String subject, String body){
    	String r ="";
    	if(recipients.size()>1){
    		for(int z=0;z <recipients.size(); z++){
    			if(z < (recipients.size()-1))
    				r += recipients.get(z)+",";
    			else
    				r+=recipients.get(z);
    		}
    	}
    	else 
    		r = recipients.get(0);
    	String i = "{\"id\":"+requestID+",\"method\":\"send_message\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+r+"\",\""+subject+"\","+body+"\"\n\n Sent from Lacuna Express \n\nFind it in Google Playstore\",null]}";
        
    	/*StartOfObject(1, "send_message");
        String i = "0";
        try{
            writer.value(sessionID);
            writer.value(recipients);
            writer.value(subject);
            writer.value(body);
            writer.endArray();
            writer.endObject();
            writer.close();
            i = gson.toJson(writer);
            i = CleanJsonObject(i);
        }catch(IOException e){
            System.out.println("ioexception");
        }catch(NullPointerException e){
            System.out.println("null pointer exception");
        }finally{
        } */
        return i;
    }
    /*enum MessageTags{  //will be leaving this as an option to use in the future, but currently just passing a string directly to the methods instead
		Tutorial, Correspondence, Medal, Intelligence, Alert, Attack, Colonization, Complaint, Excavator, Mission, Parliament, Probe, Spies, Trade, Fissure;
	} */

}
/*
Inbox Methods
view_inbox ( session_id, [ options ] )
session_id
options
page_number
tags
view_archived ( session_id, [ options ])
view_trashed ( session_id, [ options ])
view_sent ( session_id, [ options ] )
read_message ( session_id, message_id )
session_id
message_id
archive_messages ( session_id, message_ids )
session_id
message_ids
trash_messages ( session_id, message_ids )
session_id
message_ids
send_message ( session_id, recipients, subject, body, [ options ] )
session_id
recipients
subject
body
options
in_reply_to
forward
*/