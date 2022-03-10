package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
public class CharacteristicEntityId implements Serializable {
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "device_id", nullable = false)
	private Integer deviceId;

	protected CharacteristicEntityId() {}

	public CharacteristicEntityId(Integer id, Integer deviceId) {
		this.id = id;
		this.deviceId = deviceId;
	}
	
	public CharacteristicEntityId(Integer deviceId, Integer id) {
		this.deviceId = deviceId;
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}
}
