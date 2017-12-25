package ldh.akka.persit.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by xiongfei.lei on 2017/12/22.
 */
public class ServerMain {

    static ActorSystem actorSystem = ActorSystem.create("SwapperSystem");
    static LoggingAdapter logger = Logging.getLogger(actorSystem, ServerMain.class);

    public static void main(String[] args) {
        logger.info("Start myDestination");
        Props props1 = Props.create(MyDestination.class);
        final ActorRef myDestination = actorSystem.actorOf(props1, "myDestination");
        logger.info("Started myDestination");
    }
}
