package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class DetectionEntityId implements Serializable {
	@Column(name = "creation_time", nullable = false)
	private Instant creationTime;

	@Column(name = "characteristic_id", nullable = false)
	private Integer characteristicId;

	@Column(name = "device_id", nullable = false)
	private Integer deviceId;

	protected DetectionEntityId() {}

	public DetectionEntityId(Instant creationTime, Integer characteristicId, Integer deviceId) {
		this.creationTime = creationTime;
		this.characteristicId = characteristicId;
		this.deviceId = deviceId;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Integer getCharacteristicId() {
		return this.characteristicId;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		DetectionEntityId that = (DetectionEntityId) o;
		return getCreationTime().equals(that.getCreationTime())
			&& getCharacteristicId().equals(that.getCharacteristicId())
			&& getDeviceId().equals(that.getDeviceId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCreationTime(), getCharacteristicId(), getDeviceId());
	}
}
