package it.deltax.produlytics.persistence;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class DetectionEntityId implements Serializable {
	private Instant creationTime;

	private Integer characteristicId;

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
