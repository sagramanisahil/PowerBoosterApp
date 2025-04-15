import javax.swing.*;
import java.awt.BorderLayout;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class SolarPower{
	JFrame MainFrame = new JFrame();
	JTextArea Azimuth_Angle = new JTextArea();
	JTextArea Tilt_Angle = new JTextArea();

	public void DataReciever()
	{
		try{
			Socket Client_Socket = new Socket("127.0.0.1",8080);
			BufferedReader Server_Reader = new BufferedReader(new InputStreamReader(Client_Socket.getInputStream()));

			String TiltAngle = "Current Tilt Angle : "+Server_Reader.readLine();
			String AzimuthAngle = "Current Azimuth Angle : "+Server_Reader.readLine(); //recieves values from the server

			Tilt_Angle.setText(TiltAngle);
			Azimuth_Angle.setText(AzimuthAngle);  	// Sets the value of labels
			Server_Reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Error connecting");
			e.printStackTrace();
		}
	}
	public void CreateGUI()
	{
		MainFrame.setLayout(new BorderLayout());
		MainFrame.add(Tilt_Angle, BorderLayout.NORTH);
		MainFrame.add(Azimuth_Angle, BorderLayout.CENTER);

		MainFrame.setTitle("Robotic Arm");

		MainFrame.setSize(400,500);
		MainFrame.setVisible(true);
		MainFrame.setDefaultCloseOperation(3);
	}
	public static void main(String [] args)
	{
		SolarPower sc = new SolarPower();
		sc.CreateGUI();
		sc.DataReciever();
	}
}
