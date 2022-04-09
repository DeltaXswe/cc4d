package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "characteristic")
public class CharacteristicEntity {
	@EmbeddedId
	private CharacteristicEntityId id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "upper_limit", nullable = true)
	private Double upperLimit;

	@Column(name = "lower_limit", nullable = true)
	private Double lowerLimit;

	@Column(name = "auto_adjust", nullable = false)
	private Boolean autoAdjust;

	@Column(name = "sample_size", nullable = true)
	private Integer sampleSize;

	@Column(name = "archived", nullable = false)
	private Boolean archived;

	protected CharacteristicEntity() {}

	public CharacteristicEntity(
		CharacteristicEntityId id,
		String name,
		Double upperLimit,
		Double lowerLimit,
		Boolean autoAdjust,
		Integer sampleSize,
		Boolean archived
	) {
		this.id = id;
		this.name = name;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.autoAdjust = autoAdjust;
		this.sampleSize = sampleSize;
		this.archived = archived;
	}

	public CharacteristicEntityId getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Double getUpperLimit() {
		return this.upperLimit;
	}

	public Double getLowerLimit() {
		return this.lowerLimit;
	}

	public Boolean getAutoAdjust() {
		return this.autoAdjust;
	}

	public Integer getSampleSize() {
		return this.sampleSize;
	}

	public Boolean getArchived() {
		return this.archived;
	}
}
