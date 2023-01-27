package com.mechamic38.barattus.persistence.tradeparams;

import com.mechamic38.barattus.persistence.common.IDataSource;
import com.mechamic38.barattus.persistence.common.InvalidFileException;

import java.util.List;

/**
 * Trade params data source base interface.
 */
public interface ITradeParamDataSource extends IDataSource<TradeParamDTO> {

    List<TradeParamDTO> getAll(String filePath) throws InvalidFileException;
}
