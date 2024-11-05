package br.com.picpay.service;

import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.UpdateWalletParamsDto;
import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.entity.enums.Type;
import br.com.picpay.exception.AlreadyRegisteredDataException;
import br.com.picpay.exception.DefaultException;
import br.com.picpay.exception.InputValidationException;
import br.com.picpay.exception.NonExistentWalletException;
import br.com.picpay.repository.WalletRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class WalletServiceTest {

    @InjectMocks
    WalletService service;

    @Mock
    WalletRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class createWallet {

        @Test
        @DisplayName("Method should successfully create and persist new wallet to an owner")
        void methodShouldSuccessfullyCreateAndPersistNewWalletToAnOwner() {
            var userTypeDto = new WalletRequestDto(
                "Test",
                "1234",
                "test@email.com",
                "test123",
                Type.USER
            );

            var merchantTypeDto = new WalletRequestDto(
                "Test",
                "4321",
                "testing@email.com",
                "test123",
                Type.MERCHANT
            );

            when(repository.getByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(null);
            doNothing().when(repository).persist(any(Wallet.class));

            var userResult = service.createWallet(userTypeDto);

            assertNotNull(userResult);
            assertEquals(userTypeDto.name(), userResult.getName());
            assertEquals(userTypeDto.cpfCnpj(), userResult.getCpfCnpj());
            assertEquals(userTypeDto.email(), userResult.getEmail());
            assertEquals(userTypeDto.type().toString(), userResult.getOwnerType().getType());

            var merchantResult = service.createWallet(merchantTypeDto);

            assertNotNull(merchantResult);
            assertEquals(merchantTypeDto.name(), merchantResult.getName());
            assertEquals(merchantTypeDto.cpfCnpj(), merchantResult.getCpfCnpj());
            assertEquals(merchantTypeDto.email(), merchantResult.getEmail());
            assertEquals(merchantTypeDto.type().toString(), merchantResult.getOwnerType().getType());
            verify(repository, times(2)).getByEmailOrCpfCnpj(anyString(), anyString());
            verify(repository, times(2)).persist(any(Wallet.class));
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Method should throw AlreadyRegisteredDataException if email or cpfcnpj already exists")
        void methodShouldThrowAlreadyRegisteredDataExceptionIfEmailOrCpfcnpjAlreadyExists() {
            var dto = new WalletRequestDto(
                "Test",
                "1234",
                "test@email.com",
                "test123",
                Type.USER
            );

            when(repository.getByEmailOrCpfCnpj(anyString(), anyString())).thenReturn(mock(Wallet.class));
            assertThrows(AlreadyRegisteredDataException.class, () -> service.createWallet(dto));
        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {
            var withoutName = new WalletRequestDto(
                null,
                "1234",
                "test@gmail.com",
                "test123",
                Type.USER
            );
            assertThrows(InputValidationException.class, () -> service.createWallet(withoutName));

            var withoutCpfCnpj = new WalletRequestDto(
                "Test",
                null,
                "test@gmail.com",
                "test123",
                Type.USER
            );
            assertThrows(InputValidationException.class, () -> service.createWallet(withoutCpfCnpj));

            var withoutEmail = new WalletRequestDto(
                "Test",
                "1234",
                null,
                "test123",
                Type.USER
            );
            assertThrows(InputValidationException.class, () -> service.createWallet(withoutEmail));

            var withoutPassword = new WalletRequestDto(
                "Test",
                "1234",
                "test@gmail.com",
                null,
                Type.USER
            );
            assertThrows(InputValidationException.class, () -> service.createWallet(withoutPassword));

            var withoutUser = new WalletRequestDto(
                "Test",
                "1234",
                "test@gmail.com",
                "test123",
                null
            );
            assertThrows(InputValidationException.class, () -> service.createWallet(withoutUser));
        }
    }

    @Nested
    class getById {

        @Test
        @DisplayName("Method should successfully return wallet corresponding to provided id")
        void methodShouldSuccessfullyReturnWalletCorrespondingToProvidedId() {
            when(repository.getById(anyLong())).thenReturn(mock(Wallet.class));
            var result = service.getById(1L);

            assertNotNull(result);
            verify(repository, times(1)).getById(anyLong());
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if provided id is not found")
        void methodShouldThrowNonExistentWalletExceptionIfProvidedIdIsNotFound() {
            when(repository.getById(anyLong())).thenThrow(NonExistentWalletException.class);
            assertThrows(NonExistentWalletException.class, () -> service.getById(1L));
        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {
            assertThrows(InputValidationException.class, () -> service.getById(null));
        }
    }

    @Nested
    class getAll {

        @Test
        @DisplayName("Method should successfully return all registered wallets")
        void methodShouldSuccessfullyReturnAllRegisteredWallets() {
            var mock1 = mock(Wallet.class);
            var mock2 = mock(Wallet.class);

            when(repository.listAll()).thenReturn(List.of(
                mock1,
                mock2
            ));

            when(mock1.getName()).thenReturn("Test dude");
            when(mock1.getCpfCnpj()).thenReturn("1234");
            when(mock1.getEmail()).thenReturn("test@email.com");
            when(mock1.getOwnerType()).thenReturn(Type.USER.toOwnerType());

            when(mock2.getName()).thenReturn("Test buddy");
            when(mock2.getCpfCnpj()).thenReturn("4321");
            when(mock2.getEmail()).thenReturn("testing@email.com");
            when(mock2.getOwnerType()).thenReturn(Type.USER.toOwnerType());

            var result = service.getAll();

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(repository, times(1)).listAll();
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Method should return empty list if there are no wallets")
        void methodShouldReturnEmptyListIfThereAreNoWallets() {
            var result = service.getAll();
            assertNotNull(result);
            assertEquals(0, result.size());
        }
    }

    @Nested
    class updateById {

        @Test
        @DisplayName("Method should successfully update and persist wallet with new data")
        void methodShouldSuccessfullyUpdateAndPersistWalletWithNewData() {
            var walletMock = mock(Wallet.class);
            doNothing().when(walletMock).setName(anyString());
            doNothing().when(walletMock).setEmail(anyString());
            doNothing().when(walletMock).setPassword(anyString());
            when(repository.getById(anyLong())).thenReturn(walletMock);
            doNothing().when(repository).persist(any(Wallet.class));


            var updatingEverythingDto = new UpdateWalletParamsDto(
                "Test dude",
                "test_dude@gmail.com",
                "newPassword123"
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingEverythingDto));

            var updatingOnlyNameDto = new UpdateWalletParamsDto(
                "Test dude",
                null,
                null
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingOnlyNameDto));

            var updatingOnlyEmailDto = new UpdateWalletParamsDto(
                null,
                "test_buddy@gmail.com",
                null
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingOnlyEmailDto));

            var updatingOnlyPasswordDto = new UpdateWalletParamsDto(
                null,
                null,
                "Pass123"
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingOnlyPasswordDto));

            var updatingNameAndEmailDto = new UpdateWalletParamsDto(
                "Test dude",
                "test_dude@gmail.com",
                null
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingNameAndEmailDto));

            var updatingNameAndPasswordDto = new UpdateWalletParamsDto(
                "Test dude",
                null,
                "Pass123"
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingNameAndPasswordDto));

            var updatingEmailAndPasswordDto = new UpdateWalletParamsDto(
                null,
                "test_buddy@gmail.com",
                "Pass123"
            );
            assertDoesNotThrow(() -> service.updateById(1L, updatingEmailAndPasswordDto));

            verify(repository, times(7)).getById(anyLong());
            verify(repository, times(7)).persist(any(Wallet.class));
            verifyNoMoreInteractions(repository);
        }

        @Test
        @DisplayName("Method should throw AlreadyRegisteredDataException if email already exists")
        void methodShouldThrowAlreadyRegisteredDataExceptionIfEmailAlreadyExists() {
            var dto = new UpdateWalletParamsDto(
                "Test",
                "test@email.com",
                "1234"
            );

            when(repository.getByEmail(anyString())).thenReturn(mock(Wallet.class));
            assertThrows(AlreadyRegisteredDataException.class, () -> service.updateById(1L, dto));
        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {
            var dto = new UpdateWalletParamsDto(
                null,
                null,
                null
            );

            assertThrows(InputValidationException.class, () -> service.updateById(1L, dto));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Method should successfully delete wallet corresponding to provided id")
        void methodShouldSuccessfullyDeleteWalletCorrespondingToProvidedId() {
            var walletMock = mock(Wallet.class);
            when(walletMock.hasBalance()).thenReturn(false);
            when(repository.getById(anyLong())).thenReturn(walletMock);
            when(repository.deleteById(anyLong())).thenReturn(true);

            assertDoesNotThrow(() -> service.deleteById(1L));
            verify(repository, times(1)).getById(anyLong());
            verify(repository, times(1)).deleteById(anyLong());
        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if provided id is not found")
        void methodShouldThrowNonExistentWalletExceptionIfProvidedIdIsNotFound() {
            when(repository.getById(anyLong())).thenThrow(NonExistentWalletException.class);
            assertThrows(NonExistentWalletException.class, () -> service.deleteById(1L));
        }

        @Test
        @DisplayName("Method should throw DefaultException if wallet still has balance")
        void methodShouldThrowDefaultExceptionIfWalletStillHasBalance() {
            var walletMock = mock(Wallet.class);
            when(walletMock.hasBalance()).thenReturn(true);
            when(repository.getById(anyLong())).thenReturn(walletMock);

            assertThrows(DefaultException.class, () -> service.deleteById(1L));
            verify(repository, times(1)).getById(anyLong());
            verifyNoMoreInteractions(repository);
        }
    }
}
