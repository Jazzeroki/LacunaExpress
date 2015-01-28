package com.JazzDevStudio.LacunaExpress.AccountMan;
 
//import android.content.Context;
//import android.content.Context;

import android.os.Environment;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.MISCClasses.SessionRefresh;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;


//import java.io.FileOutputStream;
//import java.io.FileOutputStream.openFileOutput;
/**
 * Notes on usage
 * DeleteAccount and ModifyAccount both assume that a check has been made for the existence of an account file
 * Load and AddAccount will both meet the requirements for this check.
 * Support for a default account still needs to be added.  There is meant to be only a single default account
 */
public class AccountMan {
    private static Gson gson = new Gson();
    
    public static void DeleteFile(){
        //File dir = getFilesDir();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/", "accnt.jazz");
        boolean deleted = file.delete();
    }
    public static AccountInfo GetDefaultAccount(){
        Accounts a = Load();
        for(AccountInfo i: a.accounts){
            if(i.defaultAccount)
                return i;
        }
        return null;
    }
    public static AccountInfo GetAccount(String displayString){
        Accounts a = Load();
	    Log.d("AccountMan.Java-48", displayString);
        for(AccountInfo i: a.accounts){
            if(i.displayString.contains(displayString)) {
	            Log.d("AccountMan.Java-51", displayString);
	            return i;
            }
        }
        Log.d("AccountMan.Java", "Returning Null");
	    return null;
    }
    public static void AddAccount(AccountInfo acnt){
    	Log.d("AccountInfo username", acnt.userName);
    	Log.d("AccountInfo username", acnt.password);
    	Log.d("AccountInfo username", acnt.sessionID);
    	Log.d("AccountInfo username", acnt.defaultAccount.toString());
    	
    	Log.d("AcountMan", "Starting Add Account");
    	acnt.CreateDisplayString();
        Accounts accounts = new Accounts();
        Log.d("AccountMan", "Checking for file");
        if(CheckForFile()){
        	Log.d("AccountMan", "Loading Accounts");
            accounts = Load();
            
            //This block checks for and removes duplicates before adding the new account
            if(accounts.accounts.size() >1){
            	int indexToRemove = -1;
            	for(AccountInfo i: accounts.accounts)
                if(i.userName.equals(acnt.userName)&& i.server.equals(acnt.server) ){
                	indexToRemove = accounts.accounts.indexOf(i);
                    break;
                }
            	if(indexToRemove >= 0)
            		accounts.accounts.remove(indexToRemove);
            }
            
            Log.d("AccountMan", "if there's only 1 account");
            if(accounts.accounts.size() == 1){
            	Log.d("AccountMan", "Only 1 account setting as default");
                acnt.defaultAccount = true;
            	}
            }
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        Log.d("AccountMan", "Checking for Default Account");
        if(acnt.defaultAccount){
        	Log.d("AccountMan", "Default Account check");
            for(AccountInfo i: accounts.accounts){
                i.defaultAccount = false;
            }
        }
        //in case there is only 1 account in the file this ensures that one account will be default
 
        //AccountInfo a = new AccountInfo(username, password, server, aPIKey, sessionID, sessionDate, defaultAccount);
        Log.d("AccountMan", "Adding Account to Account Array");
        accounts.accounts.add(acnt);
        Log.d("AccountMan", "Calling Save Accounts");
        Save(accounts);
    }
    //troubleshooting method that removes all but the first account
    public static ArrayList<AccountInfo> PurgeDuplicateAccounts(ArrayList<AccountInfo> arrayToPurge){
    	if(arrayToPurge.size()>1){
    		AccountInfo account = arrayToPurge.get(0);
    		arrayToPurge.clear();
    		arrayToPurge.add(account);
    		Save(arrayToPurge);
    	}
    	return arrayToPurge;
    }
    
    //This method assumes a check has already been made for the existance of the accounts file
    public static void DeleteAccount(String userName, String server){
        Accounts accounts = Load();
        if(accounts.accounts.size() >1){
        	int indexToRemove = -1;
        	for(AccountInfo i: accounts.accounts)
            if(i.userName.equals(userName)&& i.server.equals(server) ){
            	indexToRemove = accounts.accounts.indexOf(i);
                break;
            }
        	if(indexToRemove >= 0)
        		accounts.accounts.remove(indexToRemove);
        }
        Save(accounts);
    }
    public static void DeleteAccount(String displayString){
        Accounts accounts = Load();
        if(accounts.accounts.size() >1){
        	int indexToRemove = -1;
        	for(AccountInfo i: accounts.accounts)
            if(i.displayString.equals(displayString) ){
            	indexToRemove = accounts.accounts.indexOf(i);
                break;
            }
        	if(indexToRemove >= 0)
        		accounts.accounts.remove(indexToRemove);
        }
        Save(accounts);
    }
    //This method assumes you've already checked for the existance of an account file
    public static void ModifyAccount(String username, String password, String server, String aPIKey, String sessionID, String sessionDate, Boolean defaultAccount){
        AccountInfo a = new AccountInfo(username, password, server, aPIKey, sessionID, sessionDate, defaultAccount);
        Accounts accounts = Load();
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        if(defaultAccount){
            for(AccountInfo i: accounts.accounts){
                i.defaultAccount = false;
            }
        }
        for(AccountInfo i: accounts.accounts){
            if(i.userName.equals(username)&& i.server.equals(server) ){
                accounts.accounts.remove(accounts.accounts.indexOf(i));
                break;
            }
        }
        a.CreateDisplayString();
        accounts.accounts.add(a);
        Save(accounts);
    }
    //for use internally by accountman to save accounts
    private static void Save(Accounts accounts){

	    //TESTING



	    Log.d("AccountMan.Save", "Serializing File");
        String i = gson.toJson(accounts, Accounts.class);
        Log.d("AccountMan.Save", i);
        //File file = new File("/data/data/folder/files/", "accnt.jazz");
	    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/", "accnt.jazz");

			    try {
		    file.createNewFile();
		    Log.d("File Was", "Successfully Created");
	    } catch (Exception e){
		    e.printStackTrace();
		    Log.d("File Was", "NOT Created");
	    }

        file.setReadable(true);
        file.setWritable(true);
        try {
        	Log.d("AccountMan.Save", "Writing to file");
            //PrintWriter writer = new PrintWriter("accnt.jazz");
            PrintWriter writer = new PrintWriter(file);
            //System.out.println("saving");
            writer.write(i);
	        Log.d("Info Being Written", i);
            //System.out.println("closing writer");
            writer.close();
        } catch (FileNotFoundException e) {
        	Log.d("AccountMan.Save", "File Not Found Exception");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
	        e.printStackTrace();
        }


    }
    //An overloaded save method to make saving accounts easier
    public static void Save(ArrayList<AccountInfo> accounts){
    	Accounts a = new Accounts();
    	for(AccountInfo i: accounts){
    		accounts.get(accounts.indexOf(i)).CreateDisplayString();
    	}
    		
    	a.accounts = accounts;
    	Save(a);
    }
    public static ArrayList<AccountInfo> GetAccounts(){
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{
        BufferedReader br;
        String i = "";
        try{
	        br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"accnt.jazz"));
	        //br = new BufferedReader(new FileReader("/data/data/com.JazzDevStudio.LacunaExpress/files/accnt.jazz"));
	        Log.d("PATH", Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"accnt.jazz");
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
 
            while (line != null) {
                sb.append(line);
                line = br.readLine();
                i = sb.toString();
            }
            br.close();
            Log.d("AccountMan.GetAccounts", "what was in file"+i);
            accounts = gson.fromJson(i, Accounts.class);
        }catch (FileNotFoundException e){
            // CreateAccount();
	        Log.d("PATH", Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"accnt.jazz");
	        Log.d("ERROR", "LINE 234");
            e.printStackTrace();
            return null;
        }catch(IOException e){
	        Log.d("PATH", Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"accnt.jazz");
	        Log.d("ERROR", "LINE 237");
            e.printStackTrace();
            return null;
        }

        //having issue with accounts.accounts being null so deleting file because file may be corrupted
        if(accounts.accounts.size() == 0){
            //DeleteFile();
            return null;
        }
        return accounts.accounts;
    }
    
    private static Accounts Load(){
    	
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{

        Boolean sessionsWereRefreshed = false;
        BufferedReader br;
        String i = "";
        try{

            br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"accnt.jazz"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
                i = sb.toString();
            }
            br.close();
            Log.d("AccountMan.Load", i);
            accounts = gson.fromJson(i, Accounts.class);
            if(accounts.accounts.size() !=0){
                for(AccountInfo a: accounts.accounts){
                    Log.d("Accountman.Load", "sessionExpires ="+a.sessionExpires);
                    Log.d("Accountman.Load", "Calendar instance"+Calendar.getInstance());
                    Log.d("Accountman.Load", "SessionExpires instance"+a.sessionExpires);
                    Log.d("Accountman.Load", "SessionExpires after"+a.sessionExpires.after(Calendar.getInstance()));
                    if(a.sessionExpires.equals(null)||a.sessionExpires.before(Calendar.getInstance())) {
                        //if (a.sessionExpires.after(Calendar.getInstance())) {
                            Log.d("AccountMan.Load", "Sessions have expired. Refreshing");
                            SessionRefresh r = new SessionRefresh();
                            r.execute("i");
                            try {
                                Thread.sleep(1000 * (accounts.accounts.size()));
                            } catch (InterruptedException e) {
                                Log.e("AccountMan.Load", "Thread interrupted during sleep");
                                e.printStackTrace();
                            }
                            sessionsWereRefreshed = true;
                        //}
                    }

                }
                Log.d("AccounMan.Load", "Successfully Loaded accounts");
            }
            else
	        Log.e("AccounMan.Load", "No Accounts found");
        }catch (FileNotFoundException e){
	        Log.d("AccounMan.Load", "IOException");
        }catch(IOException e){
	        Log.d("AccounMan.Load", "IOException");
        }
        Log.d("Accountman.Load", "Sessions were refreshed ="+sessionsWereRefreshed);
        if(sessionsWereRefreshed){
            return accounts = Load();
        }
        return accounts;
    }

	//Checks if there is a file (If they have never added an account, no file will exist)
    public static boolean CheckForFile(){
    	Log.d("AccountMan.CheckForFile", "Checking if File Exists");
    	Boolean b = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/", "accnt.jazz").isFile();
    	Log.d("AccountMan.CheckForFile ", b.toString());
        //return new File("accnt.jazz").isFile();
    	return b;
    }


}