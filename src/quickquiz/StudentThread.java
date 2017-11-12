package quickquiz;
//Source:
//  Creating a simple Chat Client/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html


import java.net.*;
import java.io.*;

public class StudentThread extends Thread
{

    private Socket socket = null;
    private QuickQuizStudent student = null;
    private DataInputStream streamIn = null;

    public StudentThread(QuickQuizStudent _student, Socket _socket)
    {
        student = _student;
        socket = _socket;
        open();
        start();
    }

    public void open()
    {
        try
        {
            streamIn = new DataInputStream(socket.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Error getting input stream: " + ioe);
            //client2.stop();
            student.close();
        }
    }

    public void close()
    {
        try
        {
            if (streamIn != null)
            {
                streamIn.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                student.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client2.stop();
                student.close();
            }
        }
    }
}
