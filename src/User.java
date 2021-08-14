import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Hashtable;

public class User implements Runnable{
	static Hashtable<String,Integer> ipManagement = new Hashtable<String,Integer>();
	Socket client;
	Memory memory;
	BufferedReader reader;
	BufferedWriter writer;
	String htmlDoc;
	int length;
	String ip;
	
	public User(Socket client,
			Memory memory, String htmlDoc, int length) {
		this.client = client;
		this.memory = memory;
		this.htmlDoc = htmlDoc;
		this.length = length;
	}
	
    @Override
    public void run () {
    	htmlDoc = htmlDoc.replaceAll(
    			"%RealTime%",this.memory.menu.toString()
    			+ "<br />전체합계 : " + memory.sum());
    	this.length += this.memory.menu.toString().length();
    	ip = getClientIp();
    	if (!ipManagement.containsKey(ip)) {
    		ipManagement.put(ip, -1); 
    		System.out.println("create ip");
    	}
    	
    	try {
    		print(this.htmlDoc, this.length);
    		order();
    	}catch (NumberFormatException e) {
		} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (writer != null) {
    				writer.close();
    			}
    		}  catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public void print (String send, int Additional) throws Exception{
    	StringBuffer msg = new StringBuffer();
    	msg.append("HTTP/1.1 200 OK").append("\r\n");
		msg.append("Host: localhost").append("\r\n");
		msg.append("Content-Length: ").append(send.length() + (Additional * 1.8)).append("\r\n");
		msg.append("Content-Type: text/html;charset=UTF-8").append("\r\n");
		msg.append("\r\n");
		msg.append(send);
		
		writer = new BufferedWriter(new OutputStreamWriter(
				client.getOutputStream()));
		writer.write(msg.toString());
    }
    
    public void order () throws Exception {
    	reader = new BufferedReader(new InputStreamReader(
    			client.getInputStream()));
    	String choice = reader.readLine().split("[ ]")[1];
        int foodNumber =Integer.valueOf(choice.substring(1)) - 1;
        String status = "";
        
        if (foodNumber <= -1 && ipManagement.get(ip) != -1) {
        	memory.menu.get(ipManagement.get(ip)).countDown();
    		ipManagement.put(ip, -1);
    		status = "주문취소";
        } else if (foodNumber <= -1 && ipManagement.get(ip) == -1) {
        	ipManagement.put(ip, -1);
        } else {
        	if (ipManagement.get(ip) == -1) {
        		ipManagement.put(ip, foodNumber);
        		memory.menu.get(foodNumber).countUp();
        		System.out.println(memory.menu.get(foodNumber).name);
        		System.out.println("new order");
        		status = "주문완료";
        	}else {
        		memory.menu.get(ipManagement.get(ip)).countDown();
        		memory.menu.get(foodNumber).countUp();
        		System.out.println(memory.menu.get(ipManagement.get(ip)).name + "취소");
        		System.out.println(memory.menu.get(foodNumber).name + "변경");
        		ipManagement.put(ip, foodNumber);
        		status = "주문변경";
        	}
        }
        print(htmlDoc + status, length + 4);
    }
    
    public String getClientIp() {
    	String originalIp = client.toString(); 
    	
    	return originalIp.substring(
    			originalIp.indexOf("/"),originalIp.indexOf(","));
    }

}
