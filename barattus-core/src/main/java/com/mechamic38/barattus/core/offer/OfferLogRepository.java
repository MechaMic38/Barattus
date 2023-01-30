package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.persistence.offer.IOfferLogDataSource;
import com.mechamic38.barattus.persistence.offer.OfferLogDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Offer logs repository.
 */
public class OfferLogRepository implements IOfferLogRepository {

    private final List<OfferLog> offerLogs;
    private final IOfferLogDataSource dataSource;
    private final OfferLogMapper mapper;


    public OfferLogRepository(IOfferLogDataSource dataSource, OfferLogMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
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
            dataSource.insert(mapper.toDto(log));
        } else {
            dataSource.update(mapper.toDto(log));
        }
    }

    @Override
    public void loadFromDataSource() {
        for (OfferLogDTO log : dataSource.getAll()) {
            offerLogs.add(mapper.fromDto(log));
        }
    }
}
