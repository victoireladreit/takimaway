SET search_path TO takimaway;

-- Create rooms table
CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,
                       room_number VARCHAR(50) NOT NULL UNIQUE,
                       room_type room_type NOT NULL,
                       price_per_night NUMERIC(10, 2) NOT NULL CHECK (price_per_night > 0),
                       capacity INT NOT NULL CHECK (capacity > 0),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_rooms_room_type ON rooms(room_type);
CREATE INDEX idx_rooms_created_at ON rooms(created_at);

-- Create trigger for updated_at
CREATE OR REPLACE FUNCTION update_rooms_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER rooms_update_timestamp
    BEFORE UPDATE ON rooms
    FOR EACH ROW
EXECUTE FUNCTION update_rooms_timestamp();