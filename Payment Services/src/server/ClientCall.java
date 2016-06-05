package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import model.Client;

public class ClientCall<T> implements Callable<T>{
	private RequestResponse<T> lookup;
	OutputStream out = null;
	ObjectOutputStream stream = null;
	Socket sock = null;


	public ClientCall(RequestResponse<T> lookup, Socket soc) {
		this.lookup = lookup;
		sock = soc;
	}

	@Override
	public T call() throws IOException {
		T result = null;
		try {
			out = sock.getOutputStream();
			stream = new ObjectOutputStream(out);
			stream.writeObject(lookup); // trimite la server requestul

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