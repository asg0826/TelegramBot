import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.Timestamp;

public class Darrow extends TelegramLongPollingBot {
	
	//instances
	long refreshTime=60000;
	TimeUpdate timeUpdate;
	
	//Constructor
	public Darrow() {
		setTime(60000);

		
	}
	
	public void startMainTask() {
		
		TimeUpdate timeUpdate= new TimeUpdate((long) 0 ,setTime());
		this.timeUpdate=timeUpdate;
		
	}
	
	
	//Methods	
	public void update() throws IOException {
		
		try {
			
			 Socket s=new Socket("localhost",23456);
		        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		        s.setSoTimeout(2000);
		        
		        try {
		        	
		        	String str=in.readLine(); //Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
		    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    		String time= timestamp.toString() ;	
		    		String trama=time+ ",hotel," + str;
		    		sendMsg(trama);	
		    		s.close();
		    		
				} catch (SocketTimeoutException a) {
					
					s.close();
				//	System.out.println("ERROR timeout");
					Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
					
				}
			
		} catch (ConnectException e) {
			//System.out.println("ERROR conection");// TODO: handle exception
			Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
		}

       
		   
		        
		}
	
	public long setTime() {
		
		
		return this.refreshTime;
		
	}
	
	
	
	public void setTime(long refreshTime) {
		
		this.refreshTime=refreshTime;
		
		
	}
	
	
	
	
	public void sendMsg(String msg) throws IOException {
		
		long channel=############# ;
       	SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(Long.toString(channel));
        message.setText(msg);
    
        try {
        	execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
    
	}

	
	
	public void onUpdateReceived(Update update) {

		if (update.hasChannelPost()) {
			
	        String post= update.getChannelPost().getText();
	        String[] parts = post.split(" ");
	        
	        if("/helloDarrow".equals(post)) {
	        	try {
	        		
					sendMsg("Hey there, I am Darrow 😄 .\n\n"
							+ "Here are my commands:\n"
							+ " /helloDarrow \n"
							+ " /locationDarrow \n"
							+ " /informationDarrow \n"
							+ " /stopDarrow \n"
							+ " /restartDarrow \n"
							+ " /setTime (min) \n"
							+ " /update");
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        		
	        }
	        
	        
	        if("/update".equals(post)) {
	        	try {
	        		
					update();
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        		
	        }
	        
	        
	        if("/locationDarrow".equals(post)) {
	        	try {
	        		
					sendMsg("I am located at Hotel Termales del Ruiz 🤩 .");
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        		
	        }
	        
	        
	        if("/informationDarrow".equals(post)) {
	        	try {
	        		
					sendMsg("I am located at Hotel Termales del Ruiz measuring conductivity, pH and enjoying the environment 🙈 .");
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        		
	        }
	        
	        
	        if("/stopDarrow".equals(post)) {

	        	timeUpdate.Stop();
	        		
	        }
	        
	        
	        if("/restartDarrow".equals(post)) {

	        	timeUpdate.Stop();
	        	
	        	try {
	        		
	        		Long sT= setTime()/60000;
					sendMsg("Uptade rate set to " + sT + " min");
					startMainTask();
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        	
	        		
	        }
	        
	        
	        if("/setTime".equals(parts[0])) {
	        	try {
	        		
					long userTime = (Long.parseLong(parts[1]))*60000;
					
					if(userTime >= 30000) {
						
						timeUpdate.Stop();
						setTime(userTime);
						sendMsg("Uptade rate set to " + parts[1] + " min");
						startMainTask();
						
						
					}
					
				} catch (IOException e) {

					e.printStackTrace();
				}
	        		
	        }
	        
	    }
	}
	

	public String getBotUsername() {
 
        return "Darrow";
    }

    @Override
    public String getBotToken() {
    
        return "##############################################";
    }
}
