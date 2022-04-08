package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDeviceDetailsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class GetDeviceDetailsService implements GetDeviceDetailsUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;

	public GetDeviceDetailsService(
		FindDetailedDevicePort findDetailedDevicePort
	) {
		this.findDetailedDevicePort = findDetailedDevicePort;
	}

	@Override
	public DetailedDevice getDeviceDetails(int id) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder result = findDetailedDevicePort.findDetailedDevice(id)
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		return result.build();
	}
}
