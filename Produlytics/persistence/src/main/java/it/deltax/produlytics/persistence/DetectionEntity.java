package it.deltax.produlytics.persistence;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "detection")
@IdClass(DetectionEntityId.class)
public class DetectionEntity {
	@Column(name = "creation_time", nullable = false)
	@Id
	private Instant creationTime;

	@Column(name = "characteristic_id", nullable = false)
	@Id
	private Integer characteristicId;

	@Column(name = "device_id", nullable = false)
	@Id
	private Integer deviceId;

	@Column(name = "value", nullable = false)
	private Double value;

	@Column(name = "outlier", nullable = false)
	private Boolean outlier;

	protected DetectionEntity() {}

	public DetectionEntity(
		Instant creationTime, Integer characteristicId, Integer deviceId, Double value, Boolean outlier
	) {
		this.creationTime = creationTime;
		this.characteristicId = characteristicId;
		this.deviceId = deviceId;
		this.value = value;
		this.outlier = outlier;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Integer getCharacteristicId() {
		return characteristicId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public Double getValue() {
		return this.value;
	}

	public Boolean getOutlier() {
		return this.outlier;
	}
}
