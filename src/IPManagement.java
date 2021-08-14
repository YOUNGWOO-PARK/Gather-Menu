public class IPManagement {
    String ip;
    int menuNumber;
    
    public IPManagement (String ip, int menuNumber) {
    	this.ip = ip;
    	this.menuNumber = menuNumber;
    }
    
    public void orderMenu(int menuNumber) {
    	this.menuNumber = menuNumber;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof IPManagement) {
    		IPManagement ipmanagement = (IPManagement) obj;
    		return this.ip.equals(ipmanagement.ip);
    	}
    	
    	return false;
    }
}
