package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "detection")
public class DetectionEntity {
	@EmbeddedId
	private DetectionEntityId id;

	@Column(name = "value", nullable = false)
	private Double value;

	@Column(name = "outlier", nullable = false)
	private Boolean outlier;

	protected DetectionEntity() {}

	public DetectionEntity(
		DetectionEntityId id, Double value, Boolean outlier
	) {
		this.id = id;
		this.value = value;
		this.outlier = outlier;
	}

	public DetectionEntityId getId() {
		return this.id;
	}

	public Double getValue() {
		return this.value;
	}

	public Boolean getOutlier() {
		return this.outlier;
	}
}
