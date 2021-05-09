package cubeWealth;

import java.time.Duration;
import java.time.LocalDateTime;

public class Person {
	
	public String personName;
	public int noOfDeliveries;
	public int totalTime;
	public LocalDateTime pickupTime;
	public LocalDateTime deliveryTime;
	public int timeTaken;
//	private Delivery latestDelivery;
	
	

	public Person(String personName,int totalTime,LocalDateTime pickupTime, LocalDateTime deliveryTime ) {
		this.personName = personName;
		this.noOfDeliveries = 0;
		this.totalTime = 0;
		this.pickupTime = pickupTime;
		this.deliveryTime = deliveryTime;
		differenceTime();
	}
	
	public void differenceTime() {
		Duration timeDiff =Duration.between(pickupTime, deliveryTime);
		timeTaken = (int) timeDiff.toMinutes();
	}
	public int timeTaken() {
		return timeTaken;
	}

	void ShowPerson() {
		System.out.println("Person Name : "+personName+"No. of Deliveries : "+noOfDeliveries+"Total Time : "+totalTime);
	}
	

}
