import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer 
{
	private ServerSocket server;
	private ArrayList<Socket> list=new ArrayList<Socket>();
	public ChatServer()
	{
		try
		{
			server=new ServerSocket(2009);
			System.out.println("Server started...");
			while(true)
			{
				Socket soc=server.accept();
				System.out.println("Client connected...");
				list.add(soc);
				new ChatThread(soc,list).start();
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public static void main(String[] args) 
	{
		new ChatServer();
	}
}
class ChatThread extends Thread
{
	Socket soc;
	ArrayList<Socket> list;
	public ChatThread(Socket soc,ArrayList<Socket> list)
	{
		this.soc=soc;
		this.list=list;
	}
	public void run()
	{
		try
		{
			while(true)
			{
				DataInputStream dis=new DataInputStream(soc.getInputStream());
				String msg=dis.readUTF();
				String name=msg.substring(0,msg.indexOf(" "));
				System.out.println("Thread is brodcasting "+name+" messages");
				for(Socket sc:list)
				{
					DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
					if(soc==sc)
					{
						String m=msg.substring(msg.indexOf(':')+1);
						m="You said:"+m;
						dos.writeUTF(m);
					}
					else
						dos.writeUTF(msg);
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}

