package bog.processing.rabbit;

import com.rabbitmq.client.QueueingConsumer;

import java.util.LinkedList;

public class ListenerThread implements Runnable {
	private LinkedList<String> messages;
	private QueueingConsumer consumer;
	private String like;

	public ListenerThread(QueueingConsumer consumer, LinkedList<String> messages, String like) {
		this.messages = messages;
		this.consumer = consumer;
		this.like = like;
	}

	public void run() {
		try {
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				if (like == null || message.contains(like)) {
					messages.add(message);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}