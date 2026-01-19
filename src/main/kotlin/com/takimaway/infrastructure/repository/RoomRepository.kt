package com.takimaway.infrastructure.repository

import com.takimaway.domain.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : JpaRepository<Room, Long>
