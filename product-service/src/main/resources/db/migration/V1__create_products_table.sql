CREATE TABLE products (
                          id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          name        VARCHAR(200)   NOT NULL,
                          price       NUMERIC(12, 2) NOT NULL CHECK (price >= 0),
                          stock       INTEGER        NOT NULL CHECK (stock >= 0),
                          created_at  TIMESTAMPTZ    NOT NULL DEFAULT now(),
                          updated_at  TIMESTAMPTZ    NOT NULL DEFAULT now()
);