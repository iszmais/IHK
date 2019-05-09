import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Buffer {

	
	public static void main(String[] args) {
		System.out.println(Buffer.getInputString());
		System.out.println(Buffer.getInputInt());
		System.out.println(Buffer.getInputDouble());
	}
	
	public static String getInputString() {
		try {
			BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static int getInputInt() {
		try {
			BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
			return Integer.parseInt(reader.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static double getInputDouble() {
		try {
			BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
			return Double.parseDouble(reader.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
