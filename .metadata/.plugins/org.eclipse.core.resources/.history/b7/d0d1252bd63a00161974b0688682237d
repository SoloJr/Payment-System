package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {
	private Socket clientSocket;

	public ServerThread(Socket socket) {
		clientSocket = socket;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void accept() {
		OutputStream out = null;
		ObjectOutputStream stream = null;
		InputStream in = null;
		ObjectInputStream objStream = null;
		try {
			in = clientSocket.getInputStream();
			objStream = new ObjectInputStream(in);
			RequestResponse lookup = (RequestResponse) objStream.readObject();
			if (lookup != null) {
				out = clientSocket.getOutputStream();
				stream = new ObjectOutputStream(out);
				lookup.response = ServerUtils.getValue(lookup);
				stream.writeObject(lookup);
			}
		} catch (Exception ex) {
			System.out.println("");
		} finally {
			try {
				if (out != null)
					out.close();
				if (stream != null)
					stream.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				System.out.println("");
			}
		}
	}

	@Override
	public void run() {
		accept();
	}

}
