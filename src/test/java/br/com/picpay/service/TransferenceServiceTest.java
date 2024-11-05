package br.com.picpay.service;

import br.com.picpay.entity.Transference;
import br.com.picpay.entity.Wallet;
import br.com.picpay.entity.dto.NotificationEventDto;
import br.com.picpay.entity.dto.TransferenceRequestDto;
import br.com.picpay.exception.*;
import br.com.picpay.repository.TransferenceRepository;
import br.com.picpay.repository.WalletRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class TransferenceServiceTest {

    @InjectMocks
    TransferenceService service;

    @Mock
    TransferenceRepository transferenceRepository;

    @Mock
    WalletRepository walletRepository;

    @Mock
    AuthorizerClient authorizerClient;

    @Mock
    NotificationRabbitmqProducer notificationProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class transfer {

        @Test
        @DisplayName("Method should successfully transfer amount from one wallet to another, persist transference and " +
            "send notification to broker")
        void methodShouldSuccessfullyTransferAmountFromOneWalletToAnotherPersistTransferenceAndSendNotificationToBroker() {
            TransferenceRequestDto dto = new TransferenceRequestDto(
                BigDecimal.TEN,
                1L,
                2L
            );

            var payerMock = mock(Wallet.class);
            var payeeMock = mock(Wallet.class);
            when(walletRepository.getById(anyLong())).thenReturn(payerMock, payeeMock);
            when(payerMock.isUser()).thenReturn(true);
            when(payerMock.getBalance()).thenReturn(BigDecimal.TEN);
            doNothing().when(payerMock).debit(any(BigDecimal.class));
            doNothing().when(payeeMock).credit(any(BigDecimal.class));
            doNothing().when(walletRepository).persist(any(Wallet.class));
            var responseMock = mock(Response.class);
            when(authorizerClient.requestClient()).thenReturn(responseMock);
            when(responseMock.getStatus()).thenReturn(200);
            doNothing().when(transferenceRepository).persist(any(Transference.class));
            when(payeeMock.getEmail()).thenReturn("test@test.com");
            when(payerMock.getEmail()).thenReturn("testing@test.com");
            doNothing().when(notificationProducer).sendNotificationToQueue(any(NotificationEventDto.class));

            var result = service.transfer(dto);

            assertNotNull(result);
            assertEquals(dto.amount(), result.getAmount());
            assertEquals(dto.payee(), result.getPayee());
            assertEquals(dto.payer(), result.getPayer());
            verify(walletRepository, times(2)).getById(anyLong());
            verify(walletRepository, times(2)).persist(any(Wallet.class));
            verify(authorizerClient, times(1)).requestClient();
            verify(transferenceRepository, times(1)).persist(any(Transference.class));
            verify(notificationProducer, times(1))
                .sendNotificationToQueue(any(NotificationEventDto.class));
            verifyNoMoreInteractions(transferenceRepository, walletRepository,
                authorizerClient, notificationProducer);
        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {
            TransferenceRequestDto dto = new TransferenceRequestDto();
            assertThrows(InputValidationException.class, () -> service.transfer(dto));
        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if any provided wallet ID is not found")
        void methodShouldThrowNonExistentWalletExceptionIfAnyProvidedWalletIdIsNotFound() {
            TransferenceRequestDto dto = new TransferenceRequestDto(
                BigDecimal.TEN,
                1L,
                2L
            );

            when(walletRepository.getById(anyLong())).thenThrow(NonExistentWalletException.class);
            assertThrows(NonExistentWalletException.class, () -> service.transfer(dto));
        }

        @Test
        @DisplayName("Method should throw OwnerTypeException if owner type is not allowed to make transfers")
        void methodShouldThrowOwnerTypeExceptionIfOwnerTypeIsNotAllowedToMakeTransfers() {
            TransferenceRequestDto dto = new TransferenceRequestDto(
                BigDecimal.TEN,
                1L,
                2L
            );

            var payerMock = mock(Wallet.class);
            var payeeMock = mock(Wallet.class);
            when(walletRepository.getById(anyLong())).thenReturn(payerMock, payeeMock);
            when(payerMock.isUser()).thenReturn(false);

            assertThrows(OwnerTypeException.class, () -> service.transfer(dto));
        }

        @Test
        @DisplayName("Method should throw InsufficientBalanceException if payer wallet has not enough balance")
        void methodShouldThrowInsufficientBalanceExceptionIfPayerWalletHasNotEnoughBalance() {
            TransferenceRequestDto dto = new TransferenceRequestDto(
                BigDecimal.TEN,
                1L,
                2L
            );

            var payerMock = mock(Wallet.class);
            var payeeMock = mock(Wallet.class);
            when(walletRepository.getById(anyLong())).thenReturn(payerMock, payeeMock);
            when(payerMock.isUser()).thenReturn(true);
            when(payerMock.getBalance()).thenReturn(BigDecimal.ZERO);

            assertThrows(InsufficientBalanceException.class, () -> service.transfer(dto));
        }

        @Test
        @DisplayName("Method should throw UnauthorizedTransferException if payer is not authorized")
        void methodShouldThrowUnauthorizedTransferExceptionIfPayerIsNotAuthorized() {
            TransferenceRequestDto dto = new TransferenceRequestDto(
                BigDecimal.TEN,
                1L,
                2L
            );

            var payerMock = mock(Wallet.class);
            var payeeMock = mock(Wallet.class);
            when(walletRepository.getById(anyLong())).thenReturn(payerMock, payeeMock);
            when(payerMock.isUser()).thenReturn(true);
            when(payerMock.getBalance()).thenReturn(BigDecimal.TEN);
            doNothing().when(payerMock).debit(any(BigDecimal.class));
            doNothing().when(payeeMock).credit(any(BigDecimal.class));
            doNothing().when(walletRepository).persist(any(Wallet.class));
            var responseMock = mock(Response.class);
            when(authorizerClient.requestClient()).thenReturn(responseMock);
            when(responseMock.getStatus()).thenReturn(504);

            assertThrows(UnauthorizedTransferException.class, () -> service.transfer(dto));
        }
    }

    @Nested
    class getById {

        @Test
        @DisplayName("Method should successfully return transference corresponding to provided id")
        void methodShouldSuccessfullyReturnTransferenceCorrespondingToProvidedId() {
            when(transferenceRepository.getById(anyLong())).thenReturn(mock(Transference.class));
            var result = service.getById(1L);

            assertNotNull(result);
            verify(transferenceRepository, times(1)).getById(anyLong());
            verifyNoMoreInteractions(transferenceRepository);
        }

        @Test
        @DisplayName("Method should throw NonExistentTransferenceException if provided id is not found")
        void methodShouldThrowNonExistentTransferenceExceptionIfProvidedIdIsNotFound() {
            when(transferenceRepository.getById(anyLong())).thenThrow(NonExistentTransferenceException.class);
            assertThrows(NonExistentTransferenceException.class, () -> service.getById(1L));
        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {
            assertThrows(InputValidationException.class, () -> service.getById(null));
        }
    }

    @Nested
    class listAllTransfersSentByWalletId {

        @Test
        @DisplayName("Method should successfully return all transferences sent by provided ID")
        void methodShouldSuccessfullyReturnAllTransferencesSentByProvidedWalletId() {
            var mock1 = mock(Transference.class);
            var mock2 = mock(Transference.class);
            when(transferenceRepository.listAll()).thenReturn(List.of(
                mock1,
                mock2
            ));

            when(mock1.getPayer()).thenReturn(10L);
            when(mock2.getPayer()).thenReturn(10L);

            var result = service.listAllTransfersSentByWalletId(10L);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(transferenceRepository, times(1)).listAll();
            verifyNoMoreInteractions(transferenceRepository);
        }

        @Test
        @DisplayName("Method should return empty list if there are no transferences sent")
        void methodShouldReturnEmptyListIfThereAreNoTransferencesSent() {
            var result = service.listAllTransfersSentByWalletId(1L);
            assertNotNull(result);
            assertEquals(0, result.size());
        }
    }

    @Nested
    class listAllTransfersReceivedByWalletId {

        @Test
        @DisplayName("Method should successfully return all transferences received by provided ID")
        void methodShouldSuccessfullyReturnAllTransferencesReceivedByProvidedWalletId() {
            var mock1 = mock(Transference.class);
            var mock2 = mock(Transference.class);
            when(transferenceRepository.listAll()).thenReturn(List.of(
                mock1,
                mock2
            ));

            when(mock1.getPayee()).thenReturn(10L);
            when(mock2.getPayee()).thenReturn(10L);

            var result = service.listAllTransfersReceivedByWalletId(10L);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(transferenceRepository, times(1)).listAll();
            verifyNoMoreInteractions(transferenceRepository);
        }

        @Test
        @DisplayName("Method should return empty list if there are no transferences received")
        void methodShouldReturnEmptyListIfThereAreNoTransferencesReceived() {
            var result = service.listAllTransfersReceivedByWalletId(1L);
            assertNotNull(result);
            assertEquals(0, result.size());
        }
    }
}
