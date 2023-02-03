package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.persistence.common.InvalidFileException;
import com.mechamic38.barattus.persistence.tradeparams.ITradeParamDataSource;
import com.mechamic38.barattus.persistence.tradeparams.TradeParamDTO;
import com.mechamic38.barattus.util.Result;

public class TradeParamsService implements ITradeParamsService {

    private final ITradeParamRepository repository;
    private final ITradeParamDataSource dataSource;
    private final TradeParamsMapper mapper;

    public TradeParamsService(ITradeParamRepository repository, ITradeParamDataSource dataSource, TradeParamsMapper mapper) {
        this.repository = repository;
        this.dataSource = dataSource;
        this.mapper = mapper;
    }


    @Override
    public Result<TradeParams> loadParamsFromFile(String path) {
        try {
            TradeParamDTO dto = dataSource.get(path);
            if (dto != null) {
                TradeParams tradeParams = mapper.fromDto(dto);
                repository.importData(tradeParams);
                return Result.success(tradeParams);
            } else {
                return Result.error("trade.params.error.load");
            }
        } catch (InvalidFileException e) {
            return Result.error("trade.params.error.load");
        }
    }
}
