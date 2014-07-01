import bog.processing.rabbit.*;

RabbitListener listener;

void setup() {
  listener = new RabbitListener(this);
  listener.Listen();
}

void draw() {
  while (listener.HasMessages ()) {
    String message = listener.Pop();
    println(message);
  }
}

