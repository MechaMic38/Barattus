package com.mechamic38.barattus.persistence.tradeparams;

import com.mechamic38.barattus.persistence.common.IDataSource;
import com.mechamic38.barattus.persistence.common.InvalidFileException;

/**
 * Trade params data source base interface.
 */
public interface ITradeParamDataSource extends IDataSource<TradeParamDTO> {

    TradeParamDTO get();

    TradeParamDTO get(String filePath) throws InvalidFileException;
}
