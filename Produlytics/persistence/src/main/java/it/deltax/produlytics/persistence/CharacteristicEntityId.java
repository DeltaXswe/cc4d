package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

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
