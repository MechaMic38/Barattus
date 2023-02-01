package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.Trade;

/**
 * Holder class for trade data.
 */
public class TradeData {

    private final Trade trade;
    private final OfferData initiatorOfferData;
    private final OfferData proposedOfferData;

    private TradeData(Trade trade, OfferData initiatorOfferData, OfferData proposedOfferData) {
        this.trade = trade;
        this.initiatorOfferData = initiatorOfferData;
        this.proposedOfferData = proposedOfferData;
    }

    public static TradeDataBuilder builder(Trade trade) {
        return new TradeDataBuilder(trade);
    }

    public Trade getTrade() {
        return trade;
    }

    public OfferData getInitiatorOfferData() {
        return initiatorOfferData;
    }

    public OfferData getProposedOfferData() {
        return proposedOfferData;
    }

    /**
     * {@link TradeData} builder.
     */
    static class TradeDataBuilder {
        private Trade trade;
        private OfferData initiatorOfferData;
        private OfferData proposedOfferData;

        public TradeDataBuilder(Trade trade) {
            this.trade = trade;
        }

        public TradeDataBuilder setTrade(Trade trade) {
            this.trade = trade;
            return this;
        }

        public TradeDataBuilder setInitiatorOfferData(OfferData initiatorOfferData) {
            this.initiatorOfferData = initiatorOfferData;
            return this;
        }

        public TradeDataBuilder setProposedOfferData(OfferData proposedOfferData) {
            this.proposedOfferData = proposedOfferData;
            return this;
        }

        public TradeData build() {
            return new TradeData(
                    trade,
                    initiatorOfferData,
                    proposedOfferData
            );
        }
    }
}
