package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.user.User;

import java.util.LinkedList;
import java.util.List;

public class TradeQuery {

    private final User user;
    private final Offer offer;
    private final List<TradeStatus> statuses;

    private TradeQuery(User user, Offer offer, List<TradeStatus> statuses) {
        this.user = user;
        this.offer = offer;
        this.statuses = statuses;
    }

    public static TradeQueryBuilder builder() {
        return new TradeQueryBuilder();
    }

    public User getUser() {
        return user;
    }

    public Offer getOffer() {
        return offer;
    }

    public List<TradeStatus> getStatuses() {
        return statuses;
    }

    /**
     * {@link TradeQuery} builder.
     */
    public static class TradeQueryBuilder {
        private User user;
        private Offer offer;
        private List<TradeStatus> statuses;

        public TradeQueryBuilder() {
            this.statuses = new LinkedList<>();
        }

        public TradeQueryBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TradeQueryBuilder setOffer(Offer offer) {
            this.offer = offer;
            return this;
        }

        public TradeQueryBuilder addStatus(TradeStatus status) {
            this.statuses.add(status);
            return this;
        }

        public TradeQuery build() {
            return new TradeQuery(user, offer, statuses);
        }
    }
}
