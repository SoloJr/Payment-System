package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientCall<T> implements Callable<T>{
	private RequestResponse<T> lookup;
	OutputStream out = null;
	ObjectOutputStream stream = null;


	public ClientCall(RequestResponse<T> lookup) {
		this.lookup = lookup;
	}

	@Override
	public T call() throws IOException {
		T result = null;
		try {
			Socket sock = new Socket(lookup.host, lookup.port);
			out = sock.getOutputStream();
			stream = new ObjectOutputStream(out);
			stream.writeObject(lookup);

			InputStream in = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			RequestResponse<T> look = (RequestResponse<T>) ois.readObject();
			result = look.response ;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
