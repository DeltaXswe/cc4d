package it.deltax.produlytics.uibackend.admins.characteristics.business.ports.out;

import it.deltax.produlytics.persistence.CharacteristicEntity;

import java.util.List;

public interface FindCharacteristicPort {
	public List<CharacteristicEntity> findByName(String name);
}
