import java.util.List;

public class Memory {
	List<Food> menu;
    
    public Memory(List<Food> menu) {
    	this.menu = menu;
    }
    
    public synchronized void order(int number) {
    	menu.get(number).countUp();
    }
    
    public int sum() {
    	int result = 0;
    	for (int i = 0; i < menu.size(); i++) {
    		result += menu.get(i).number;
    	}
    	return result;
    }
}
