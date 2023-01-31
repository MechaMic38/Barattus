package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.core.tradeparams.TradeParams;
import com.mechamic38.barattus.util.Result;

public interface ITradeParamsService {

    Result<TradeParams> loadParamsFromFile(String path);
}
