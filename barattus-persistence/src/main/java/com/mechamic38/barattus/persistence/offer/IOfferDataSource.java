package com.mechamic38.barattus.persistence.offer;

import com.mechamic38.barattus.persistence.common.IDataSource;

import java.util.List;

/**
 * Offer data source base interface.
 */
public interface IOfferDataSource extends IDataSource<OfferDTO> {
    List<OfferDTO> getAll();
}
