package it.deltax.produlytics.persistence;

import java.io.Serializable;
import java.util.Objects;

public class CharacteristicEntityId implements Serializable {
	private Integer id;

	private Integer deviceId;

	protected CharacteristicEntityId() {}

	public CharacteristicEntityId(Integer deviceId) {
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

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		CharacteristicEntityId that = (CharacteristicEntityId) o;
		return getId().equals(that.getId()) && getDeviceId().equals(that.getDeviceId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getDeviceId());
	}
}
