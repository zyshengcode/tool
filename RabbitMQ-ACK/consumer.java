

@Component("reportDelFileListener")
public class ReportDelFileListener implements ChannelAwareMessageListener {

	private final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

	@Autowired
	public ReportFileServiceImpl reportFileServiceImpl;

	private final static Jackson2JsonMessageConverter convert = new Jackson2JsonMessageConverter();

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			LOGGER.info("接受MQ队列为：REPORT_DEL_FILE_SID");
			String synJsonStr = (String) convert.fromMessage(message);
			LOGGER.info("接受MQ数据为：" + synJsonStr);

			reportFileServiceImpl.delFtpFile(Long.valueOf(synJsonStr));
		} catch (Exception e) {
			ExceptionUtils.saveStackTrace(e);
		} finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
}
