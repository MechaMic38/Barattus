package com.mechamic38.barattus.persistence.trade;

import com.mechamic38.barattus.persistence.common.IDataSource;

import java.util.List;

/**
 * Trade data source base interface.
 */
public interface ITradeDataSource extends IDataSource<TradeDTO> {
    List<TradeDTO> getAll();
}
