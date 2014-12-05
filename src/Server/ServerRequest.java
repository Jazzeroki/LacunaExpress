package Server;

public class ServerRequest{
	public final String server, methodURL, json;
	public ServerRequest(String server, String methodURL, String json){
		this.server = server;
		this.methodURL = methodURL;
		this.json = json;
	}
}
