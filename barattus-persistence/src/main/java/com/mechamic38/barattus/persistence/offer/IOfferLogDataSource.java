package com.mechamic38.barattus.persistence.offer;

import com.mechamic38.barattus.persistence.common.IDataSource;

import java.util.List;

/**
 * Offer log data source base interface.
 */
public interface IOfferLogDataSource extends IDataSource<OfferLogDTO> {
    List<OfferLogDTO> getAll();
}
