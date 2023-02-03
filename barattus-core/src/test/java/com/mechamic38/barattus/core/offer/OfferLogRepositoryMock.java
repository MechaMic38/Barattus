package com.mechamic38.barattus.core.offer;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class OfferLogRepositoryMock implements IOfferLogRepository {

    private final List<OfferLog> offerLogs;

    public OfferLogRepositoryMock() {
        this.offerLogs = new LinkedList<>();
    }

    @Override
    public OfferLog getById(UUID id) {
        return offerLogs.stream()
                .filter(log -> log.checkID(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<OfferLog> getAll() {
        return offerLogs;
    }

    @Override
    public void save(OfferLog log) {
        if (!this.offerLogs.contains(log)) {
            this.offerLogs.add(log);
        }
    }

    @Override
    public void loadFromDataSource() {

    }
}
