SET search_path TO takimaway;

-- Create discounts table
CREATE TABLE discounts (
                           id BIGSERIAL PRIMARY KEY,
                           code VARCHAR(50) NOT NULL UNIQUE,
                           percentage NUMERIC(5, 2) NOT NULL CHECK (percentage >= 0 AND percentage <= 100),
                           is_active BOOLEAN NOT NULL DEFAULT true,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_discounts_code ON discounts(code);
CREATE INDEX idx_discounts_is_active ON discounts(is_active);

-- Create trigger for updated_at
CREATE OR REPLACE FUNCTION update_discounts_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER discounts_update_timestamp
    BEFORE UPDATE ON discounts
    FOR EACH ROW
EXECUTE FUNCTION update_discounts_timestamp();