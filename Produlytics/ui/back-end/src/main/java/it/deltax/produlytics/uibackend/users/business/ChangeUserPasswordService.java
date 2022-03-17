package it.deltax.produlytics.uibackend.users.business;

import it.deltax.produlytics.uibackend.devices.business.ports.in.ChangeUserPasswordUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.PasswordEncrypter;

public class ChangeUserPasswordService implements ChangeUserPasswordUseCase{
    private final UpdateUserPort updateUserPort;
    private final FindUserPort findUserPort;
    private final PasswordEncrypter passwordEncrypter;

    public ChangeUserPasswordService(
            UpdateUserPort updateUserPort,
            FindUserPort findUserPort,
            PasswordEncrypter passwordEncrypter){
        this.updateUserPort = updateUserPort;
        this.findUserPort = findUserPort;
        this.passwordEncrypter = passwordEncrypter;
    }

    @Override
    public boolean changeByUsername(String username, String currentPassword, String hashedPassword){
        return findUserPort.find(username)
                .flatMap(characteristic -> findDevicePort.find(deviceId)
                        .map(device ->
                                new CharacteristicDisplayInfo(device, characteristic)
                        )
                );
        passwordEncrypter.matches(currentPassword,)
    }
}