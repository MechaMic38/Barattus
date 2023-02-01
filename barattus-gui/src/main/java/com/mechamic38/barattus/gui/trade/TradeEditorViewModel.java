package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeDetails;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import com.mechamic38.barattus.core.usecase.IGetTradeDataUseCase;
import com.mechamic38.barattus.core.usecase.TradeData;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.ListUtils;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TradeEditorViewModel implements ITradeEditorViewModel {

    private final ITradeService tradeService;
    private final ITradeParamRepository tradeParamRepository;
    private final IGetTradeDataUseCase getTradeDataUseCase;

    private final StringProperty error = new SimpleStringProperty("");
    private final BooleanProperty editTurn = new SimpleBooleanProperty(false);
    private final BooleanProperty unconfirmed = new SimpleBooleanProperty(false);
    private final BooleanProperty ongoing = new SimpleBooleanProperty(false);
    private final ObjectProperty<TradeData> tradeData = new SimpleObjectProperty<>();
    private final ListProperty<String> places = new SimpleListProperty<>();
    private final ListProperty<DayOfWeek> days = new SimpleListProperty<>();
    private final ListProperty<LocalTime> times = new SimpleListProperty<>();

    public TradeEditorViewModel(ITradeService tradeService, ITradeParamRepository tradeParamRepository, IGetTradeDataUseCase getTradeDataUseCase) {
        this.tradeService = tradeService;
        this.tradeParamRepository = tradeParamRepository;
        this.getTradeDataUseCase = getTradeDataUseCase;
    }

    @Override
    public boolean confirmTrade(String place, DayOfWeek day, LocalTime time) {
        Result<Trade> result = tradeService.confirmTrade(
                tradeData.getValue().getTrade(),
                new TradeDetails(place, day, time),
                SessionState.getInstance().getUser()
        );

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setTradeProperties(result.getResult());
            return true;
        }
    }

    @Override
    public boolean editTrade(String place, DayOfWeek day, LocalTime time) {
        Result<Trade> result = tradeService.editTradeDetails(
                tradeData.getValue().getTrade(),
                new TradeDetails(place, day, time),
                SessionState.getInstance().getUser()
        );

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setTradeProperties(result.getResult());
            return true;
        }
    }

    @Override
    public boolean rejectTrade() {
        Result<Trade> result = tradeService.rejectTrade(
                tradeData.getValue().getTrade(),
                SessionState.getInstance().getUser()
        );

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setTradeProperties(result.getResult());
            return true;
        }
    }

    @Override
    public boolean acceptTrade() {
        Result<Trade> result = tradeService.acceptTrade(
                tradeData.getValue().getTrade(),
                SessionState.getInstance().getUser()
        );

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setTradeProperties(result.getResult());
            return true;
        }
    }

    @Override
    public void setInitiatorActive() {
        SessionState.getInstance().setOffer(
                tradeData.get().getInitiatorOfferData().getOffer()
        );
    }

    @Override
    public void setProposedActive() {
        SessionState.getInstance().setOffer(
                tradeData.get().getProposedOfferData().getOffer()
        );
    }

    @Override
    public void initialize() {
        setParamProperties();
        setTradeProperties(SessionState.getInstance().getTrade());
    }

    private void setParamProperties() {
        TradeParams params = tradeParamRepository.get();
        places.set(FXCollections.observableList(
                ListUtils.copy(params.getPlaces())
        ));
        days.set(FXCollections.observableList(
                ListUtils.copy(params.getDays())
        ));
        times.set(FXCollections.observableList(
                ListUtils.copy(params.getAllowedTimes())
        ));
    }

    private void setTradeProperties(Trade trade) {
        tradeData.set(getTradeDataUseCase.apply(trade));
        editTurn.set(
                trade.getEditTurnUser().equals(
                        SessionState.getInstance().getUser().getID()
                )
        );
        unconfirmed.set(
                trade.getStatus() == TradeStatus.UNCONFIRMED
        );
        ongoing.set(
                trade.getStatus() == TradeStatus.ONGOING
        );
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public BooleanProperty editTurnProperty() {
        return editTurn;
    }

    @Override
    public BooleanProperty unconfirmedProperty() {
        return unconfirmed;
    }

    @Override
    public BooleanProperty ongoingProperty() {
        return ongoing;
    }

    @Override
    public ObjectProperty<TradeData> tradeDataProperty() {
        return tradeData;
    }

    @Override
    public ListProperty<String> placesProperty() {
        return places;
    }

    @Override
    public ListProperty<DayOfWeek> daysProperty() {
        return days;
    }

    @Override
    public ListProperty<LocalTime> timesProperty() {
        return times;
    }
}
