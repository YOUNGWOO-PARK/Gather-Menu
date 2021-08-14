public class Food {
    String name;
    int number;
    
    public Food(String name) {
    	this.name = name;
    }
    
    public void countUp() {
    	number++;
    }
    
    public void countDown() {
    	number--;
    }
    
    @Override
    public String toString() {
    	return name + " " +number;
    }
}
