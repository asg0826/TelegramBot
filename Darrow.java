import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.sql.Timestamp;

import javax.crypto.interfaces.DHPublicKey;

public class Darrow extends TelegramLongPollingBot {
	
	//instances
	long refreshTime;
	TimeUpdate timeUpdate;
	TimeCalibrate timeCalibrate;
	long refreshTimeMemory = 60000;
	boolean calibration=false;
    int cal=0;
    public int correction_cond=0;
    public int correction_pH=0;
    
	
	//Constructor
	public Darrow() {
		
		setTime(60000);

	}
	
	
	public void startMainTask() {
		
		TimeUpdate timeUpdate= new TimeUpdate((long) 0 ,setTime());
		this.timeUpdate=timeUpdate;
		
	}
	
	public void startCalibrateTask() {
		
		TimeCalibrate timeCalibrate= new TimeCalibrate((long) 0 ,setTime());
		this.timeCalibrate=timeCalibrate;
		
	}
	
    


	public long setTime() {
		
		return this.refreshTime;
		
	}
	

	
	public void setTime(long refreshTime) {
		
		this.refreshTime=refreshTime;
		
		
	}
	
	


	//Methods	
	public void update() throws IOException {
		
		try {
			
			Socket s=new Socket("localhost",23456);
		        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		        s.setSoTimeout(2000);
		        
		        try {
		        	
		        	String str=in.readLine(); //Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
		        	String[] value = str.split(",");
		        	int conductividad= Integer.parseInt(value[2]);
		        	
		        	
		        	
		    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    		String time= timestamp.toString() ;	
		    		String trama=time+ ",hotel," + str;
		    		if(this.cal == 1) {
		    			
		    			sendMsg(str);	
			    		s.close();
		    		 	
		    		}else if(this.cal == 0) {
		    			
		    			sendMsg(trama);
			    		s.close();
		    			
		    		}
		    		
		    		
		    		
				} catch (SocketTimeoutException a) {
					
					s.close();
					Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
					
				}
			
		} catch (ConnectException e) {

			Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
		}
        
		}
	
	public void updateCalibrate() throws IOException {
		
		try {
			
			Socket s=new Socket("localhost",23456);
		        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		        s.setSoTimeout(2000);
		        
		        try {
		        	
		        	String str=in.readLine(); //Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
		    		sendMsg(str);	
			    	s.close();
		    		 	
		    		
		    		
		    		
				} catch (SocketTimeoutException a) {
					
					s.close();
					Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
					
				}
			
		} catch (ConnectException e) {

			Runtime.getRuntime().exec("sudo sh /home/pi/task.sh");
		}
        
		}	
	

	
	
	
	public void sendMsg(String msg) throws IOException {
		
		//long channel=-1001574111334L ;
		    long channel=-1001758498434L ;
       		SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
     	    message.setChatId(Long.toString(channel));
     	   	message.setText(msg);
    
        try {
		
        	execute(message); 
		
        } catch (TelegramApiException e) {
		
        	e.printStackTrace();
		
        }
    
	}

	
	
	@Override
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
							+ " /startCalibration \n"
							+ " /stopCalibration \n"
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
	       
	        if("/startCalibration".equals(post)) {
	        	
	        	
	        try {
	        	    
	        		
	        		
	        	    refreshTimeMemory = setTime();
					timeUpdate.Stop();
					sendMsg("Calibration started");
					setTime(3000);
					startCalibrateTask();
					//startMainTask();
												
				}
					
			 catch (IOException e) {

				e.printStackTrace();
				
			}
	        		
	        }
	        
	        
	        
	        
	        if("/stopCalibration".equals(post)) {
	        	
	        	
	        	try {	

					//timeUpdate.Stop();
	        		timeCalibrate.Stop();
					sendMsg("Calibration finished");
					setTime(refreshTimeMemory);
					sendMsg("Uptade rate set to " + refreshTimeMemory/60000 + " min");
					startMainTask();
												
				}
					
			 catch (IOException e) {

				e.printStackTrace();
				
			}
	        		
	        }
	        
	    }
	}
	

	@Override
	public String getBotUsername() {
 
        	return "Darrow";
		
    	}

    @Override
	
    public String getBotToken() {
    
       	 return "2110806264:AAGpaoaocSAetvSle6m5STQcKaD1CmSHXI8";
    }
}
