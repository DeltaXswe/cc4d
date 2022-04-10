package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.*;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AdminDeviceAdapter implements
	InsertDevicePort,
	GetDevicesPort,
	FindTinyDevicePort,
	FindDetailedDevicePort,
	UpdateDeviceArchiveStatusPort,
	UpdateDeviceDeactivateStatusPort,
	UpdateDeviceNamePort
{
	private final DeviceRepository repo;

	public AdminDeviceAdapter(DeviceRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Device> getDevices() {
		return StreamSupport.stream(this.repo.findAll().spliterator(), false)
			.map(device ->
				new Device(
					device.getId(),
					device.getName(),
					device.getArchived(),
					device.getDeactivated())
			)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<TinyDevice> findTinyDevice(int deviceId) {
		return this.repo.findById(deviceId)
			.map(device ->
				new TinyDevice(
					device.getId(),
					device.getName())
			);
	}

	@Override
	public Optional<DetailedDevice> findDetailedDevice(int deviceId) {
		return this.repo.findById(deviceId)
			.map(device ->
				new DetailedDevice(
					device.getId(),
					device.getName(),
					device.getArchived(),
					device.getDeactivated(),
					device.getApikey())
			);
	}

	@Override
	public void updateDeviceArchiveStatus(DetailedDevice device) {
		this.repo.save(new DeviceEntity(
			device.name(),
			device.archived(),
			device.deactivated(),
			device.apiKey()));
	}

	@Override
	public void updateDeviceDeactivateStatus(DetailedDevice device) {
		this.repo.save(new DeviceEntity(
			device.name(),
			device.archived(),
			device.deactivated(),
			device.apiKey()));
	}

	@Override
	public void updateDeviceName(DetailedDevice device) {
		this.repo.save(new DeviceEntity(
			device.name(),
			device.archived(),
			device.deactivated(),
			device.apiKey()));
	}

	@Override
	public int insertDevice(NewDevice device) {
		DeviceEntity entity = this.repo.saveAndFlush(new DeviceEntity(device.name(),
			device.archived(),
			device.deactivated(),
			device.apiKey()
		));
		return entity.getId();
	}
}
