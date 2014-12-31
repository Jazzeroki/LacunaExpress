package com.JazzDevStudio.LacunaExpress.JavaLeWrapper;

import java.util.ArrayList;


public class Inbox extends LESuperClass {
    public static final String url = "inbox";

    public static String ViewInbox(String sessionID, String tag ){
        return "{\"jsonrpc\":2,\"id\":1,\"method\":\"view_inbox\",\"params\":[\""+sessionID+"\",{\"tags\":[\""+tag+"\"],\"page_number\":1}]}";
    }

	public static String ViewInbox(String sessionID){
		return "{\"jsonrpc\":2,\"id\":1,\"method\":\"view_inbox\",\"params\":[\""+sessionID+"\",{\"page_number\":1}]}";
	}
    
    public static String ViewInbox(String sessionID, String tag, int pageNumber ){
        return "{\"jsonrpc\":2,\"id\":1,\"method\":\"view_inbox\",\"params\":[\""+sessionID+"\",{\"tags\":[\""+tag+"\"],\"page_number\":"+pageNumber+"}]}";
    }
    
    public static String ReadMessage(String sessionID, String MessageID){
    	return Request("read_message", sessionID, MessageID);
    }
    
    public static String SendMessage(int requestID, String sessionID, ArrayList<String> recipients, String subject, String body){
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
    	String i = "{\"id\":"+requestID+",\"method\":\"send_message\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+r+"\",\""+subject+"\","+body+"\",null]}";
        return i;
    }
    public static String SendMessage(int requestID, String sessionID, String recipients, String subject, String body){

        return "{\"id\":"+requestID+",\"method\":\"send_message\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",\""+recipients+"\",\""+subject+"\",\""+body+"\",null]}";
        //return i;
    }

    public String TrashMessages(int requestID, String sessionID, ArrayList<String> messageIds){
    	String r ="";
    	if(messageIds.size()>1){
    		for(int z=0;z <messageIds.size(); z++){
    			if(z < (messageIds.size()-1))
    				r += messageIds.get(z)+",";
    			else
    				r+=messageIds.get(z);
    		}
    	}
    	else 
    		r = messageIds.get(0);
        return "{\"id\":"+requestID+",\"method\":\"trash_messages\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",[\""+r+"\"]]}";

        //return Request("trash_messages", sessionID, String.valueOf(requestID), r);
    	
    }
    public static String TrashMessages(int requestID, String sessionID, String messageId){
        return "{\"id\":"+requestID+",\"method\":\"trash_messages\",\"jsonrpc\":\"2.0\",\"params\":[\""+sessionID+"\",[\""+messageId+"\"]]}";
        //return Request("trash_messages", sessionID, String.valueOf(requestID), messageId);
    }
    public String ArchiveMessages(int requestID, String sessionID, String messageId){
        return Request("archive_messages", sessionID, String.valueOf(requestID), messageId);
    }
    public static enum MessageTags{Tutorial, Correspondence, Medal, Intelligence, Alert, Attack, Colonization, Complaint, Excavator, Mission, Parliament, Probe, Spies, Trade, Fissure};
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
