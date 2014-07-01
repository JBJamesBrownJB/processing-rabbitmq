## RabbitMQ - Processing

A simple library for listening to RabbitMQ messages and using them in your processing sketches.

### Simple Use

#### Listen on localhost, default exchange.
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


### Extra Options
#### 1. Listen to different host
    listener.Listen("HOST");

#### 2. Listen to different Exchange
    listener.Listen("HOST", "EXCHANGE_NAME");

#### 3. Add Params
      HashMap<String, Object> params = new HashMap<String,Object>();
      params.put("PARAM_NAME", "PARAM_VALUE");
      listener.Listen("HOST", "EXCHANGE_NAME", params);
#### 4. Where message contains...
      HashMap<String, Object> params = new HashMap<String,Object>();
      params.put("PARAM_NAME", "PARAM_VALUE");
      listener.Listen("HOST", "EXCHANGE_NAME", params, "LIKE");

[Processing is great!](http://processing.org/)