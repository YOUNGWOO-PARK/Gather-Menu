import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveText implements Runnable{
    BufferedWriter writer;
    Memory memory;
    boolean flag;
    
    public SaveText(Memory memory) {
    	this.memory = memory;
    }
    
    @Override
    public void run() {
    	while (!flag) {
    		try {
    			saving();
    			Thread.sleep(60000);
    		} catch (Exception e) {
    			break;
    		}
    	}
    }
    
    public void saving() throws Exception{
    	try {
    		this.writer = new BufferedWriter(new OutputStreamWriter(
    				new FileOutputStream("C:\\java\\Data\\result.txt")));
    	for (int i =0; i < memory.menu.size(); i++) {
			writer.write(memory.menu.get(i).name + memory.menu.get(i).number + "\t");
			if (i % (memory.menu.size() / 3) == 0 && i != 0) {
				writer.write("\r\n");
			}
    	}
    	writer.flush();
    	} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Save...");
			try {
	    		if (writer != null) {
	    			writer.close();
	    		}    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
		}
    	
    }
    
    public void turnOn() {
    	this.flag = true;
    }
}
