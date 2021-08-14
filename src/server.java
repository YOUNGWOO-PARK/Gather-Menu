import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Server {
	public final static int PORT = 12345;
    ServerSocket server;
    Socket client;
    List<Food> menu;
	int kolength = 0;
	int time;
	BufferedReader ipReader;
	String instruction = "★ 메뉴 주문시 아래와 같이 주문해주세요★"
			+ "		<br />ipaddress:" + PORT + "/주문번호"
					+ "<br />※취소는 주문번호에 음수를 작성하면 됩니다.<br />";
	
    public Server() {
    	menu = new Vector<Food>();
    }
    
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	Server server = new Server();
    	System.out.println("음식점 이름을 입력하시오 : ");
    	String htmlDoc = server.setRestaurant(scan.nextLine());
    	System.out.println("몇분 동안 주문 받을지 입력하시오.");
    	server.time = scan.nextInt();
    	server.time += LocalTime.now().getMinute();
    	Thread user = null;
    	Thread saveText = null;
    	Memory memory = new Memory(server.menu);
    	SaveText serverSave = new SaveText(memory);
    	
    	try {
    		saveText = new Thread(serverSave,"SAVE");
    		saveText.start();
    		server.server = new ServerSocket(PORT);
    		System.out.println("Server is created");
    		System.out.println(
    				"Server address is [" + server.getIP() + ":" + PORT + " ]");
    		htmlDoc = htmlDoc.replaceAll("ipaddress", server.getIP());
    		
    		while (LocalTime.now().getMinute() != server.time) {
    			server.client = server.server.accept();
    			user = new Thread(new User(server.client,
    					memory,htmlDoc,server.kolength),"User");
    			user.start();
    		}
    		
    	} catch (Exception e) {
    		System.out.println("Server Closed");
    	} finally {
    		try {
    			if (server.ipReader != null) {
    				server.ipReader.close();
    			}
    			
    			if (server.server != null) {
    				server.server.close();
    			}
    			
    			if (server.client != null) {
    				server.server.close();
    			}
    			serverSave.saving();
    			Thread.sleep(1000);
    		} catch (Exception e) {
    		}
    		serverSave.turnOn();
    	}
    }
    
    public String setRestaurant(String restaurant) {
    	BufferedReader menureader = null;
    	BufferedReader reader = null;
    	StringBuilder change = new StringBuilder();
    	StringBuilder htmlDoc = new StringBuilder();

    	try {
    		menureader = new BufferedReader(new InputStreamReader(
    				new FileInputStream(
    						"C:\\java\\Data\\" + restaurant + ".txt")));
    		
    		reader = new BufferedReader(new InputStreamReader(
    				new FileInputStream(
    						"C:\\java\\Data\\menu.html")));
    		
    		String message;
    		int index = 1;
    		while ((message = menureader.readLine()) != null) {
    			Food food = new Food(message.split("[ ]")[0]);
    			menu.add(food);
    			change.append("<" + index + "> " + message + "<br/>");
				kolength += (message.split("[ ]")[0]).length();
				index++;
    		}
    		
    		while ((message = reader.readLine()) != null) {
    			htmlDoc.append(message);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (menureader != null) {
    				menureader.close();
    			}
    			
    			if (reader != null) {
    				reader.close();
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	String result = htmlDoc.toString().replaceAll(
    			"%restaurant%", "<<" + restaurant + ">>");
    	result = result.replaceAll("%content%", change.toString());
    	result = result.replaceAll("%instruction%", instruction);
    	return result;
    }
    
    public String getIP() throws Exception {
    	String command = "ipconfig";
		Process process = Runtime.getRuntime().exec(command);
		ipReader = new BufferedReader(
				new InputStreamReader(process.getInputStream(),"MS949"));
		
		for (int i = 0; i < 18; i++) {
			ipReader.readLine();
		}
		
    	return ipReader.readLine().split(":")[1];
    }
}
