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

	public CharacteristicEntityId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}
}
