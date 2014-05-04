import java.io.*; // TODO
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import java.util.*; // DEBUGG

public class TestLobby extends Thread {
// #1	private ArrayList<Socket> socketList;
// #2	private ArrayList<ObjectInputStream> inputList;
	private ArrayList<ObjectOutputStream> outputList;
	private Queue queue;

	public TestLobby() {
// #1	socketList = new ArrayList<Socket>();
// #2	inputList = new ArrayList<ObjectInputStream>();
		outputList = new ArrayList<ObjectOutputStream>();
		queue = new LinkedList<Object>();
	}

	public void addClient(final Socket socket) {
// #1	socketList.add(socket);
// #2	inputList.add(new ObjectInputStream(socket.getInputStream()));
		try {
			outputList.add(new ObjectOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					while (true) {
						queue.add(inputStream.readObject());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void run() {
		while(true) {
			System.out.print(""); // BUGG-CODE
// #2		for (int i = 0 ; i < inputList.size() ; i++) {
// #1		for (int i = 0 ; i < socketList.size() ; i++) {
// #1			Socket socket = socketList.get(i);
// #1			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
// #1+#2		System.out.println(inputList.get(i).readObject());
// #1+#2	}
			while (queue.peek() != null) {
				Object poll = queue.poll();
				System.out.println(poll);

				try {
					for (int i = 0 ; i < outputList.size() ; i++)
						outputList.get(i).writeObject(poll);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}