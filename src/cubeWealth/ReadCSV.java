package cubeWealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.ArrayList;
//import java.util.HashSet;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Scanner;
import java.time.Instant;
//import java.util.Iterator;

public class ReadCSV{
	
	public void addPersonList(ArrayList<Person> personList) { 
		
	}

	public static void main(String[] args) throws Exception {

		int deliveryCount=0;
		ArrayList<Delivery> deliveryList = new ArrayList<Delivery>();
		ArrayList<Person> personList = new ArrayList<Person>();
		long[] timeDurations = new long[8];
		int tD, maxTD = 0;
		String maxPerson = "";
		
		try {
			//reading the file
			File file = new File("src/data.csv");
			Scanner sc = new Scanner(file);
			sc.nextLine();//skipping 1st line
			while(sc.hasNextLine()) { //looping if there exists a line
				
				String s = sc.nextLine();
				String[] data = s.split(",");
				
				int sno = Integer.parseInt(data[0]);
		        Instant pickupTimeInstant = Instant.parse(data[3]);
		        LocalDateTime pickupTime = LocalDateTime.ofInstant(pickupTimeInstant, ZoneId.of(ZoneOffset.UTC.getId()));
		        Instant deliveryTimeInstant = Instant.parse(data[4]);
		        LocalDateTime deliveryTime = LocalDateTime.ofInstant(deliveryTimeInstant, ZoneId.of(ZoneOffset.UTC.getId()));
		        
				Delivery D = new Delivery(sno, data[1], data[2], pickupTime, deliveryTime);
				deliveryList.add(D);
				tD = D.timeTaken();
				
//				Person P = new Person(data[1],tD);
				Person P = new Person(data[1],tD,pickupTime, deliveryTime);

				int deli=0, peri=0;
				boolean flag = false;
				
				while(deli <= personList.size()) {
					
					if(peri==0 && deli==0) {
						personList.add(P);
					} 
					if(data[1].equals(personList.get(peri).personName)) {
						personList.get(peri).noOfDeliveries++;
						if(personList.get(peri).deliveryTime.isAfter(pickupTime)) {
							personList.get(peri).deliveryTime = pickupTime;
							personList.get(peri).differenceTime();
							tD = personList.get(peri).timeTaken();
						}
						personList.get(peri).totalTime = personList.get(peri).totalTime + tD;
						if(maxTD < personList.get(peri).totalTime) {
							maxTD = personList.get(peri).totalTime;
							maxPerson = personList.get(peri).personName;
						}
						System.out.println(personList.get(peri).personName+" : "+personList.get(peri).totalTime+" : "+personList.get(peri).noOfDeliveries);
						flag = false;
						deli++;
						break;
					} 
					if(deli == personList.size()) flag = true;
					peri++;
				}
				if(flag) {
					personList.add(P);
					deli++;
					peri++;
				}
				timeDurations[deliveryCount] = tD;
				deliveryCount++;
			}

			Arrays.sort(timeDurations);
//			System.out.println(timeDurations[timeDurations.length-1]+"min. is the max time taken for a delivery");
			System.out.println(maxPerson +" has taken largest time, i.e.," +maxTD+"min. for his deliveries");
			sc.close();
		} catch(FileNotFoundException e) {
			System.out.println("File you are looking for is not available");
		} catch(DateTimeException e) {
			System.out.println("Date/Time cannot be parsed");
		} catch(NullPointerException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
