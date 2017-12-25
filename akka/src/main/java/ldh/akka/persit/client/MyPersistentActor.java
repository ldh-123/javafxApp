package ldh.akka.persit.client;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.Procedure;
import akka.persistence.AbstractPersistentActorWithAtLeastOnceDelivery;
import akka.persistence.AtLeastOnceDelivery;
import ldh.akka.persit.Persistence;

import java.util.List;

public class MyPersistentActor extends AbstractPersistentActorWithAtLeastOnceDelivery {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorSelection destination;

    @Override
    public String persistenceId() {
        return "persistence-id";
    }

    public MyPersistentActor(ActorSelection destination) {
        this.destination = destination;
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(Persistence.MsgSent.class, s -> {
                    final Persistence.MsgSent evt = (Persistence.MsgSent) s;
                    deliver(destination, new Function<Long, Object>() {
                        public Object apply(Long deliveryId) {
                            return new Persistence.Msg(deliveryId, evt.s);
                        }
                    });
                })
                .match(Persistence.MsgConfirmed.class, s -> {
                    final Persistence.MsgConfirmed evt = (Persistence.MsgConfirmed) s;
                    confirmDelivery(evt.deliveryId);
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    log.info("receive msg : " + s);
                    persist(new Persistence.MsgSent(s), new Procedure<Persistence.MsgSent>() {
                        public void apply(Persistence.MsgSent evt) {
                            updateState(evt);
                        }
                    });
                })
                .match(Persistence.Confirm.class, message -> {
                    Persistence.Confirm confirm = (Persistence.Confirm) message;
                    log.info("receive confirm with deliveryId : " + confirm.deliveryId);
                    persist(new Persistence.MsgConfirmed(confirm.deliveryId), new Procedure<Persistence.MsgConfirmed>() {
                        public void apply(Persistence.MsgConfirmed evt) {
                            updateState(evt);
                        }
                    });
                })
                .match(AtLeastOnceDelivery.UnconfirmedWarning.class, message -> {
                    log.info("receive unconfirmed warning : " + message);
                    // After a number of delivery attempts a AtLeastOnceDelivery.UnconfirmedWarning message will be sent to self. The re-sending will still continue, but you can choose to call confirmDelivery to cancel the re-sending.
                    List<AtLeastOnceDelivery.UnconfirmedDelivery> list = ((AtLeastOnceDelivery.UnconfirmedWarning) message).getUnconfirmedDeliveries();
                    for (AtLeastOnceDelivery.UnconfirmedDelivery unconfirmedDelivery : list) {
                        Persistence.Msg msg = (Persistence.Msg) unconfirmedDelivery.getMessage();
                        confirmDelivery(msg.deliveryId);
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    public void updateState(Object obj) {
        if (obj instanceof Persistence.MsgSent) {
            final Persistence.MsgSent evt = (Persistence.MsgSent) obj;
            deliver(destination, new Function<Long, Object>() {
                public Object apply(Long deliveryId) {
                    return new Persistence.Msg(deliveryId, evt.s);
                }
            });
        } else if (obj instanceof Persistence.MsgConfirmed) {
            final Persistence.MsgConfirmed evt = (Persistence.MsgConfirmed) obj;
            confirmDelivery(evt.deliveryId);
        }
    }
}
