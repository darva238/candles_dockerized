package service;

import entity.Candle;
import exception.ServiceException;
import repository.CandleRepository;

import java.util.List;

public class CandleService {
    private final CandleRepository candleRepository;

    public CandleService(CandleRepository candleRepository) {
        this.candleRepository = candleRepository;
    }

    public boolean saveCandle(String candleName, double cost, int idProfile) {
        if (candleName == null || candleName.isBlank()) {
            throw new ServiceException("Название свечи не может быть пустым.");
        }
        if (cost <= 0) {
            throw new ServiceException("Стоимость должна быть положительной.");
        }

        Candle candle = new Candle();
        candle.setCandle(candleName);
        candle.setCost(cost);
        candle.setIdProfile(idProfile);
        return candleRepository.saveCandle(candle);
    }

    public List<Candle> getByCandle(int idProfile) {
        return candleRepository.getByCandle(idProfile);
    }

    public List<Candle> getCandlesByNameAndCost(int idProfile, String candle) {
        return candleRepository.getCandlesByNameAndCost(idProfile, candle);
    }

    public boolean updateCandle(int id, String candleName, double cost, int profileId) throws ServiceException {
        if (candleName == null || candleName.isBlank()) {
            throw new ServiceException("Название свечи не может быть пустым.");
        }
        if (cost <= 0) {
            throw new ServiceException("Стоимость должна быть положительной.");
        }

        Candle candle = new Candle();
        candle.setId(id);
        candle.setCandle(candleName);
        candle.setCost(cost);
        candle.setIdProfile(profileId);

        return candleRepository.updateCandle(candle);
    }

    public boolean deleteCandle(int candleId, int profileId) {
        return candleRepository.deleteCandle(candleId, profileId);
    }
}
