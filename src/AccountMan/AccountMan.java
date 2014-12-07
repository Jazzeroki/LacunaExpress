package AccountMan;

import android.util.Log;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Notes on usage
 * DeleteAccount and ModifyAccount both assume that a check has been made for the existance of an account file
 * Load and AddAccount will both meet the requirements for this check.
 * Support for a default account still needs to be added.  There is meant to be only a single default account
 */
public class AccountMan {
    private static Gson gson = new Gson();
    public static AccountInfo GetDefaultAccount(){
        Accounts a = Load();
        for(AccountInfo i: a.accounts){
            if(i.defaultAccount)
                return i;
        }
        return null;
    }
    public static void AddAccount(AccountInfo acnt){
    	Log.d("AcountMan", "Starting Add Account");
        Accounts accounts = new Accounts();
        Log.d("AccountMan", "Checking for file");
        if(CheckForFile())
        	Log.d("AccountMan", "Loading Accounts");
            accounts = Load();
        //if an account is being set as default this will reset all other accounts and then a default account will be set later
        if(acnt.defaultAccount){
        	Log.d("AccountMan", "Default Account check");
            for(AccountInfo i: accounts.accounts){
                i.defaultAccount = false;
            }
        }
        //in case there is only 1 account in the file this ensures that one account will be default
        if(accounts.accounts.size() == 1)
        	Log.d("AccountMan", "Only 1 account setting as default");
            acnt.defaultAccount = true;
        //AccountInfo a = new AccountInfo(username, password, server, aPIKey, sessionID, sessionDate, defaultAccount);
        accounts.accounts.add(acnt);
        Log.d("AccountMan", "Calling Save Accounts");
        Save(accounts);
    }

    //This method assumes a check has already been made for the existance of the accounts file
    public static void DeleteAccount(String username, String server){
        Accounts accounts = Load();
        for(AccountInfo i: accounts.accounts){
            if(i.userName.equals(username)&& i.server.equals(server) ){
                accounts.accounts.remove(accounts.accounts.indexOf(i));
                break;
            }
        }
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
        accounts.accounts.add(a);
        Save(accounts);
    }
    private static void Save(Accounts accounts){
    	Log.d("AccountMan.Save", "Serializing File");

        String i = gson.toJson(accounts, Accounts.class);
        try {
        	Log.d("AccountMan.Save", "Writing to file");
            PrintWriter writer = new PrintWriter("acnt.jazz");
            //System.out.println("saving");
            writer.print(i);
            //System.out.println("closing writer");
            writer.close();
        } catch (FileNotFoundException e) {
        	Log.d("AccountMan.Save", "File Not Found Exception");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static ArrayList<AccountInfo> GetAccounts(){
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{
        BufferedReader br;
        String i = "";
        try{
            br = new BufferedReader(new FileReader("acnt.jazz"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
                i = sb.toString();
            }
            accounts = gson.fromJson(i, Accounts.class);
        }catch (FileNotFoundException e){
            // CreateAccount();
        }catch(IOException e){

        }
        return accounts.accounts;
    }
    private static Accounts Load(){
        Accounts accounts=new Accounts();
        //if(!new File("accounts.jazz").isFile()) //if an account file doesn't exist one is created
        //    CreateAccount();
        //else{
        BufferedReader br;
        String i = "";
        try{
            br = new BufferedReader(new FileReader("acnt.jazz"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
                i = sb.toString();
            }
            accounts = gson.fromJson(i, Accounts.class);
        }catch (FileNotFoundException e){
            // CreateAccount();
        }catch(IOException e){

        }
        return accounts;
    }
    public static boolean CheckForFile(){
    	Log.d("AccountMan.CheckForFile", "Checking if File Exists");
    	Boolean b = new File("acnt.jazz").isFile();
    	Log.d("AccountMan.CheckForFile", b.toString());
        //return new File("acnt.jazz").isFile();
    	return b;
    }

}

