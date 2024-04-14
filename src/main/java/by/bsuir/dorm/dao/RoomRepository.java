package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.Room;

import java.util.UUID;

public interface RoomRepository extends NaturalJpaRepository<Room, UUID, Integer> {
}