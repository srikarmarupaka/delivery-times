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
				
				//splitting the line into strings (split with ",")
				String s = sc.nextLine();
				String[] data = s.split(",");
				
				//parsing Time & Date
				int sno = Integer.parseInt(data[0]);
		        Instant pickupTimeInstant = Instant.parse(data[3]);
		        LocalDateTime pickupTime = LocalDateTime.ofInstant(pickupTimeInstant, ZoneId.of(ZoneOffset.UTC.getId()));
		        Instant deliveryTimeInstant = Instant.parse(data[4]);
		        LocalDateTime deliveryTime = LocalDateTime.ofInstant(deliveryTimeInstant, ZoneId.of(ZoneOffset.UTC.getId()));
		        
		        //Creating a delivery object
				Delivery D = new Delivery(sno, data[1], data[2], pickupTime, deliveryTime);
				deliveryList.add(D);
				tD = D.timeTaken();
				//Creating a person object
				Person P = new Person(data[1],tD,pickupTime, deliveryTime);
				
				//iterator initializations
				int deli=0, peri=0;
				boolean flag = false;
				
				//Looping until, deliveries complete
				while(deli <= personList.size()) {
					//adding 1st delivery
					if(peri==0 && deli==0) {
						personList.add(P);
					}
					
					//checking if the name already exists
					if(data[1].equals(personList.get(peri).personName)) {
						//adding delivery, if name already exists.
						personList.get(peri).noOfDeliveries++;
						
						//overlapping times conditions
						if(personList.get(peri).deliveryTime.isAfter(pickupTime)) {
							personList.get(peri).deliveryTime = pickupTime;
							personList.get(peri).differenceTime();
							tD = personList.get(peri).timeTaken();
						}
						
						//calculating Total time-taken; considering overlapping times
						personList.get(peri).totalTime = personList.get(peri).totalTime + tD;
						if(maxTD < personList.get(peri).totalTime) {
							maxTD = personList.get(peri).totalTime;
							maxPerson = personList.get(peri).personName;
						}

						flag = false;
						deli++;
						break;
					} 
					if(deli == personList.size()) flag = true;
					peri++;
				}
				//adding person if not exists
				if(flag) {
					personList.add(P);
					deli++;
					peri++;
				}
				timeDurations[deliveryCount] = tD;
				deliveryCount++;
			}

			Arrays.sort(timeDurations);
			
			//Final output
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
