package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.GetDeviceDetailsUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.DeviceDetails;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetDeviceDetailsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

import java.util.List;
import java.util.Optional;

public class GetDeviceDetailsService implements GetDeviceDetailsUseCase {
	FindTinyDevicePort findTinyDevicePort;
	GetDeviceDetailsPort getDeviceDetailsPort;

	public GetDeviceDetailsService(
		FindTinyDevicePort findTinyDevicePort, GetDeviceDetailsPort getDeviceDetailsPort) {
		this.findTinyDevicePort = findTinyDevicePort;
		this.getDeviceDetailsPort = getDeviceDetailsPort;
	}

	@Override
	public Optional<DeviceDetails> getDeviceDetails(int id) throws BusinessException {
		TinyDevice.TinyDeviceBuilder result = findTinyDevicePort.find(id)
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		return getDeviceDetailsPort.getDeviceDetails(id);
	}
}
