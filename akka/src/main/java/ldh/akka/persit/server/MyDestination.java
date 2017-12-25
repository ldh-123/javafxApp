package ldh.akka.persit.server;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ldh.akka.persit.Persistence;


public class MyDestination extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Persistence.Msg.class, s -> {
                    Persistence.Msg msg = (Persistence.Msg) s;
                    log.info("receive msg : " + msg.s + ", deliveryId : " + msg.deliveryId);
                    getSender().tell(new Persistence.Confirm(msg.deliveryId), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}