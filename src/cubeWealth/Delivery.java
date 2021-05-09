package cubeWealth;
import java.time.LocalDateTime;
import java.time.Duration;
public class Delivery {
	
	public int sno;
	public String deliveryPerson;
	public String product;
	public LocalDateTime pickupTime;
	public LocalDateTime deliveryTime;
	public int timeTaken;

	public Delivery(int sno, String deliveryPerson,String product, LocalDateTime pickupTime, LocalDateTime deliveryTime){
		this.sno = sno;
		this.deliveryPerson = deliveryPerson ;
		this.product=product;
		this.pickupTime=pickupTime;
		this.deliveryTime=deliveryTime;
		differenceTime();
	}
	
	public void differenceTime() {
		Duration timeDiff =Duration.between(pickupTime, deliveryTime);
		timeTaken = (int) timeDiff.toMinutes();
	}
	
	public int timeTaken() {
		return timeTaken;
	}
	
	public void show() {
		System.out.println("S.No : "+sno+" **Delivery Person : "+deliveryPerson+" **Product :"+product+" **Pickup Time : "+pickupTime+" **Delivery Time : "+deliveryTime+" **Duration : "+timeTaken);
	}

	public void addDelivery(Delivery d) {
		this.sno = d.sno;
		this.deliveryPerson = d.deliveryPerson;
		this.product = d.product;
		this.pickupTime = d.pickupTime;
		this.deliveryTime = d.deliveryTime;
		this.timeTaken = d.timeTaken;
	}

}
