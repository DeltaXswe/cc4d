package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
}