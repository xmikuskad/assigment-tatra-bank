# Tatra bank assigment
_Author: Dominik Mikuska_

## Docker start
Use following command to build and run your application.

```docker compose up```

## Non docker start
Uncomment and edit application.properties file, run init sql in your postgresql database.

## Available REST endpoints

### 1. Get all exchange rates
GET ```localhost:8080/api/exchange-rates```

Returns following object:

```[{"fromCurrency":string, "toCurrency":string, "value":decimal}...]```

### 2. Get exchange rate calculation
POST ```localhost:8080/api/exchange```

Consumes following object:

```{"fromCurrency":string, "toCurrency":string, "value":decimal}```

Returns following object:

```{"amount":decimal}```