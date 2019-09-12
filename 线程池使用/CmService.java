
import java.util.concurrent.*;

@Service
public class CmService {
    private static final Logger logger = LoggerFactory.getLogger(CmService.class);


    @Async
    public void getAndSendMessage() {
        

    
    }

    public boolean testTelnet() {

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 3000);
        } catch (IOException e) {
            logger.error("network still in trouble, " + e.getMessage());
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("network still in trouble, " + e.getMessage());
            }
        }
        return true;
    }
}
