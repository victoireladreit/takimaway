SET search_path TO takimaway;

-- Insérer des chambres
INSERT INTO rooms (room_number, room_type, price_per_night, capacity)
VALUES ('101', 'BASIC', 50.00, 1),
       ('102', 'BASIC', 50.00, 1),
       ('103', 'BASIC', 50.00, 1),
       ('201', 'DOUBLE', 100.00, 2),
       ('202', 'DOUBLE', 100.00, 2),
       ('203', 'DOUBLE', 100.00, 2),
       ('301', 'SUITE', 250.00, 4),
       ('302', 'SUITE', 250.00, 4);

-- Insérer des codes de remise
INSERT INTO discounts (code, percentage, is_active)
VALUES ('WELCOME10', 10.00, true),
       ('SUMMER20', 20.00, true),
       ('BLACKFRIDAY30', 30.00, true),
       ('EXPIRED', 50.00, false);

-- Insérer des réservations existantes pour les tests de disponibilité
-- Chambre 201 : réservée du 15 au 18 août 2025
INSERT INTO reservations (room_id, guest_name, guest_email, check_in_date, check_out_date, total_price, status)
VALUES (4, 'John Doe', 'john@example.com', '2025-08-15', '2025-08-18', 300.00, 'CONFIRMED');

-- Chambre 202 : réservée du 20 au 23 août 2025
INSERT INTO reservations (room_id, guest_name, guest_email, check_in_date, check_out_date, total_price, status)
VALUES (5, 'Jane Smith', 'jane@example.com', '2025-08-20', '2025-08-23', 300.00, 'CONFIRMED');

-- Chambre 101 : réservée du 1er au 3 septembre 2025
INSERT INTO reservations (room_id, guest_name, guest_email, check_in_date, check_out_date, total_price, status)
VALUES (1, 'Bob Johnson', 'bob@example.com', '2025-09-01', '2025-09-03', 100.00, 'CONFIRMED');

-- Chambre 301 : réservée du 10 au 15 septembre 2025
INSERT INTO reservations (room_id, guest_name, guest_email, check_in_date, check_out_date, total_price, status)
VALUES (7, 'Alice Williams', 'alice@example.com', '2025-09-10', '2025-09-15', 1250.00, 'CONFIRMED');

-- Chambre 103 : ancienne réservation annulée (pour tester les statuts)
INSERT INTO reservations (room_id, guest_name, guest_email, check_in_date, check_out_date, total_price, status)
VALUES (3, 'Charlie Brown', 'charlie@example.com', '2025-07-01', '2025-07-05', 200.00, 'CANCELLED');