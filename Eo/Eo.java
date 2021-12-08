import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Eo extends TelegramLongPollingBot {
	
	//Constructor
	public Eo() {
		
		}
	
	
	//Methods
	public void sendMsg(String msg) throws IOException {
		
				long channel=-1001574111334L ;
		       	SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
		        message.setChatId(Long.toString(channel));
		        message.setText(msg);
	        
	        
	        try {
	            execute(message); // Call method to send the message
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	        
	}
	
	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasChannelPost()) {
			
	        String post= update.getChannelPost().getText();
	        
	        
	       
	     
	        //String[] parts = post.split(",");
	        //System.out.println(parts[0]);
	       /* 
	        File file = new File("C:\\Users\\aleja\\Desktop\\2021_11_16.txt");
	        System.out.println(file.exists());
	        
	        if(!file.exists()) {
	        	
	        	try {
	        		
					FileWriter flwriter = new FileWriter("C:\\Users\\aleja\\Desktop\\2021_11_16.txt");
					BufferedWriter bfwriter = new BufferedWriter(flwriter);
					bfwriter.write(post + "\n");
					bfwriter.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	        	
	        	
	        }else {
				FileWriter flwriter;
				try {
					
					flwriter = new FileWriter("C:\\Users\\aleja\\Desktop\\2021_11_16.txt",true);
					BufferedWriter bfwriter = new BufferedWriter(flwriter);
					bfwriter.write(post + "\n");
					bfwriter.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}

	        	*/
	        	

	        
	        if("/helloEo".equals(post)) {
	        	
	        	try {
	        		
					sendMsg("Hello, I am EO 😌");
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
	        	
	        		
	        }else if("/informationEo".equals(post)) {
	        	
	        	try {
	        		
					sendMsg("I am the bot who listens to the information that Darrow is sending. I am located in Manizales and sending the information to the GEODATA 😌");
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
	        	
	        }else {
	        	
	        	 try {
	 				
	 	        	Socket s=new Socket("localhost",23456);
	 	        	DataOutputStream m = new DataOutputStream(s.getOutputStream());
	 	        	m.writeUTF(post);
	 	        	s.close();

	 	        	
	 			} catch (ConnectException e1) {
	 				
	 				
	 				System.out.println("Connection error");
	 				try {
		        		
						sendMsg("I can't connect to server");
						
					} catch (IOException e) {
						
						e.printStackTrace();
	 				
	 			}
	 	        
	        	
	        } catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	    
	}
	}
	

	@Override
	public String getBotUsername() {
        // TODO
        return "Eo";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "2126583823:AAH_BCLM0THa-pcXr5Pe4hcT-yaE9n8Yas4";
    }
}
