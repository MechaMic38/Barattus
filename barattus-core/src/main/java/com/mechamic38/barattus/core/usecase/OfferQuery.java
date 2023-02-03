package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.offer.OfferStatus;
import com.mechamic38.barattus.core.user.User;

import java.util.LinkedList;
import java.util.List;

public class OfferQuery {

    private final User user;
    private final Category category;
    private final List<OfferStatus> statuses;

    private OfferQuery(User user, Category category, List<OfferStatus> statuses) {
        this.user = user;
        this.category = category;
        this.statuses = statuses;
    }

    public static OfferQueryBuilder builder() {
        return new OfferQueryBuilder();
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public List<OfferStatus> getStatuses() {
        return statuses;
    }


    /**
     * {@link OfferQuery} builder.
     */
    public static class OfferQueryBuilder {
        private final List<OfferStatus> statuses;
        private User user;
        private Category category;

        public OfferQueryBuilder() {
            this.statuses = new LinkedList<>();
        }

        public OfferQueryBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public OfferQueryBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public OfferQueryBuilder addStatus(OfferStatus status) {
            this.statuses.add(status);
            return this;
        }

        public OfferQuery build() {
            return new OfferQuery(user, category, statuses);
        }
    }
}
