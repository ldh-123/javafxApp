package ldh.akka.persit;

import java.io.Serializable;

public interface Persistence {

    public static class Msg implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;
        public final String s;

        public Msg(long deliveryId, String s) {
            this.deliveryId = deliveryId;
            this.s = s;
        }
    }

    public static class Confirm implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;

        public Confirm(long deliveryId) {
            this.deliveryId = deliveryId;
        }
    }

    public static class MsgSent implements Serializable {
        private static final long serialVersionUID = 1L;
        public final String s;

        public MsgSent(String s) {
            this.s = s;
        }
    }

    public static class MsgConfirmed implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;

        public MsgConfirmed(long deliveryId) {
            this.deliveryId = deliveryId;
        }
    }

}
