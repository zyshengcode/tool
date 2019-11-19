

public class MessageProducer {
	private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

	@Resource
	private AmqpTemplate amqpTemplate;

	public void sendMessage(Object message) {
		logger.info("to send message:{}", message);
		// 发送对象，默认序列化。如果序列化，必须convertAnd发送
		amqpTemplate.convertAndSend(message);
	}

	public void sendMessage(String key, Object message) {
		logger.info("to send message:{}", key + " : " + message);
		// 发送对象，默认序列化。如果序列化，必须convertAnd发送
		amqpTemplate.convertAndSend(key, message);
	}
}
