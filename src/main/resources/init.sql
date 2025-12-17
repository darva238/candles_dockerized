-- Таблица профилей пользователей
CREATE TABLE IF NOT EXISTS profile (
    id_profile SERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(255)
    );

-- Таблица свечей
CREATE TABLE IF NOT EXISTS candles (
    id SERIAL PRIMARY KEY,
    candle VARCHAR(255) NOT NULL,
    cost DOUBLE PRECISION NOT NULL,
    id_profile INTEGER NOT NULL,
    CONSTRAINT fk_candles_profile
    FOREIGN KEY (id_profile)
    REFERENCES profile(id_profile)
    ON DELETE CASCADE
    );

