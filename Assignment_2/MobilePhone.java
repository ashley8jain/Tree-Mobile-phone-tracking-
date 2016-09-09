
public class MobilePhone {
	int no;
	boolean status;
	private Exchange station;
	MobilePhone(int number){
		no=number;
		station=null;
		
	}
	public int number()
	{
		return no;
	}
	public boolean status(){
		return status;
	}
	public void switchOn(){
		status=true;
	}
	public void switchOff(){
		status=false;
	}
	public void setStation(Exchange B)
	{
		station=B;
	}
	public Exchange location(){
		return station;
	}

}
