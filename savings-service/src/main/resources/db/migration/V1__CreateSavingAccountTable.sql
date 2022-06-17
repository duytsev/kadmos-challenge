CREATE TABLE savings_account (
    id UUID PRIMARY KEY,
    balance NUMERIC,
    created_at TIMESTAMP DEFAULT now()
);
