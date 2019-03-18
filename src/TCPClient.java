import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

/**
 * 
 * @author Stuart Spiegel Date: 2/17/2019
 * 
 *         Program implements the HTTP GET method to retrieve valid web pages
 *         from a web server
 */
public class TCPClient {

	public static void main(String[] args) throws Exception {
		System.out.println(getHTMLV2("http://users.dickinson.edu/~braught/")); // get specified web page

	}

	public static String getHTML(String URL) throws Exception {

		// build URL from String
		StringBuilder result = new StringBuilder();
		URL url = new URL(URL);

		// establish TCP connection with the web server
		System.out.println("Enter the URL you wish to GET FROM: (example:  wwww.google.com\n)");
		String hostName = new String();
		Scanner webScanner = new Scanner(System.in);
		hostName = webScanner.next();
		Socket webSocket = new Socket(hostName, 80);

		// get reference to server output stream
		OutputStream outputStream = webSocket.getOutputStream();

		// send request
		String command = "GET \" + resource + \" HTTP/1.1\\n\"+ \"Host: \" + host + \"\\n\\n";
		System.out.print(command); // output GET command
		outputStream.write(command.getBytes("US-ASCII"));

		// make Buffered Reader for the in-stream
		BufferedReader inStream = new BufferedReader(new InputStreamReader(webSocket.getInputStream()));
		String line;

		// keep reading while there is data
		while ((line = inStream.readLine()) != null) {
			result.append(line);
		}

		// close the connection to the web server and return result data
		inStream.close();
		System.out.print(result.toString());
		return result.toString();

	}

	public static String getHTMLV2(String URL) throws IOException {

		// build URL from String
		StringBuilder result = new StringBuilder();
		URL url = new URL(URL);
		
		//create HTTP request and set mode 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		//read input stream from web server 
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		reader.close();
		return result.toString();

	}

}
