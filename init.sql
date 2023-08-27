-- DROP DATABASE assigment;
-- CREATE DATABASE assigment;
SET SEARCH_PATH TO public;

-- Create the table
-- DROP TABLE exchange_rate;
CREATE TABLE IF NOT EXISTS exchange_rate (
    id bigserial not null primary key,
    from_currency varchar(32),
    to_currency varchar(32),
    value numeric(10,4)
);

-- Insert some fake data
INSERT INTO exchange_rate (from_currency, to_currency, value)
VALUES
    ('USD', 'JPY', 109.47),
    ('USD', 'EUR', 0.8247),
    ('USD', 'CAD', 1.2418),
    ('USD', 'CZK', 21.50),
    ('USD', 'HUF', 302.94),
    ('JPY', 'USD', 0.0098),
    ('JPY', 'EUR', 0.0078),
    ('JPY', 'CAD', 0.0114),
    ('JPY', 'CZK', 0.197),
    ('JPY', 'HUF', 2.77),
    ('EUR', 'USD', 1.2114),
    ('EUR', 'JPY', 130.02),
    ('EUR', 'CAD', 1.5178),
    ('EUR', 'CZK', 26.28),
    ('EUR', 'HUF', 369.18),
    ('CAD', 'USD', 0.8015),
    ('CAD', 'JPY', 87.65),
    ('CAD', 'EUR', 0.6598),
    ('CAD', 'CZK', 17.32),
    ('CAD', 'HUF', 243.01),
    ('CZK', 'USD', 0.0465),
    ('CZK', 'JPY', 5.03),
    ('CZK', 'EUR', 0.0381),
    ('CZK', 'CAD', 0.0578),
    ('CZK', 'HUF', 14.05),
    ('HUF', 'USD', 0.0033),
    ('HUF', 'JPY', 0.36),
    ('HUF', 'EUR', 0.0027),
    ('HUF', 'CAD', 0.0041),
    ('HUF', 'CZK', 0.071);