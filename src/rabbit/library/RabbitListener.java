/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package bog.processing.rabbit;

import java.util.*;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import processing.core.*;

/**
 * @example HelloRabbit
 */

public class RabbitListener {
	public final static String VERSION = "##library.prettyVersion##";
	public final static String QUEUE_NAME = "bog processing default";
	public final static String ROUTING_KEY = "bog";
	PApplet myParent;

	Connection connection;
	Channel channel;

	LinkedList<String> messages = new LinkedList<String>();

	public RabbitListener(PApplet theParent) {
		myParent = theParent;
		welcome();
	}
	
	public void Listen() {
		Listen("","localhost",new HashMap<String, Object>(), null);
	}
	
	public void Listen(String host) {
		Listen("",host,new HashMap<String, Object>(), null);
	}
	
	public void Listen(String host, String exchange) {
		Listen(exchange,host,new HashMap<String, Object>(), null);
	}
	
	public void Listen(String host, String exchange,  Map<String, Object> params) {
		Listen(exchange,host,params, null);
	}

	public void Listen(String exchange, String host, Map<String, Object> params, String like) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, true, params);
			if (exchange != "") {
				channel.queueBind(QUEUE_NAME, exchange, ROUTING_KEY, params);
			}
			System.out.println(" [*] Waiting for messages...");
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			(new Thread(new ListenerThread(consumer, messages, like))).start();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean HasMessages() {
		return messages.isEmpty() == false;
	}

	public String Pop() {
		try {
			return messages.remove();
		} catch (Exception e) {
			System.out.println(e);
			messages.clear();
			return e.toString();
		}
	}

	public static String version() {
		return VERSION;
	}

	private void welcome() {
		System.out
				.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	public void dispose() {

	}
}
