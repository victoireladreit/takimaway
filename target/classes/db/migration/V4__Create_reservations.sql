SET search_path TO takimaway;

-- Create reservations table
CREATE TABLE reservations (
                              id BIGSERIAL PRIMARY KEY,
                              room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE RESTRICT,
                              guest_name VARCHAR(255) NOT NULL,
                              guest_email VARCHAR(255) NOT NULL,
                              check_in_date DATE NOT NULL,
                              check_out_date DATE NOT NULL,
                              total_price NUMERIC(10, 2) NOT NULL CHECK (total_price >= 0),
                              status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Constraint : check_out_date doit être après check_in_date
                              CONSTRAINT chk_dates CHECK (check_out_date > check_in_date)
);

-- Create indexes
CREATE INDEX idx_reservations_room_id ON reservations(room_id);
CREATE INDEX idx_reservations_guest_email ON reservations(guest_email);
CREATE INDEX idx_reservations_check_in_date ON reservations(check_in_date);
CREATE INDEX idx_reservations_check_out_date ON reservations(check_out_date);
CREATE INDEX idx_reservations_status ON reservations(status);
CREATE INDEX idx_reservations_created_at ON reservations(created_at);

-- Index composite pour les requêtes de disponibilité (très important !)
CREATE INDEX idx_reservations_availability ON reservations(room_id, status, check_in_date, check_out_date);

-- Create trigger for updated_at
CREATE OR REPLACE FUNCTION update_reservations_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reservations_update_timestamp
    BEFORE UPDATE ON reservations
    FOR EACH ROW
EXECUTE FUNCTION update_reservations_timestamp();