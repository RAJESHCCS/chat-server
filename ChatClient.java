import java.util.*;
import java.io.*;
import java.net.*;
public class ChatClient 
{
  private Socket soc;
  public ChatClient()
  {
    try
    {
	soc=new Socket("localhost",2009);
	new ReadThread().start();
	Scanner sc=new Scanner(System.in);
	System.out.print("Enter your name:");
	String name=sc.nextLine();
	DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
	while(true)
	{
		String message=sc.nextLine();
		dos.writeUTF(name+" said:"+message);
	}
    }
    catch(Exception ex)
    {
	System.out.println(ex);
    }
  }
  class ReadThread extends Thread
  {
    	public void run()
	{
	  try
	  {
	    while(true)
	    {
	      DataInputStream dis=new DataInputStream(soc.getInputStream());
	      String msg=dis.readUTF();
	      System.out.println(msg);
	    }
	  }
	  catch(Exception ex)
	  {
	   	System.out.println(ex);
	  }
        }
    }
    public static void main(String[] args) 
    {
	new ChatClient();
    }
}