-- Créer le langage PL/pgSQL (nécessaire pour les triggers)
CREATE EXTENSION IF NOT EXISTS plpgsql;

-- Create schema
CREATE SCHEMA IF NOT EXISTS takimaway;

-- Create enum types
CREATE TYPE takimaway.room_type AS ENUM ('BASIC', 'DOUBLE', 'SUITE');
CREATE TYPE takimaway.reservation_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED');

-- Set search path
SET search_path TO takimaway;