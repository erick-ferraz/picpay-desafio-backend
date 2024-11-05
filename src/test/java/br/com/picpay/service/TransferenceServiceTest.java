package br.com.picpay.service;

import br.com.picpay.repository.TransferenceRepository;
import br.com.picpay.repository.WalletRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@QuarkusTest
public class TransferenceServiceTest {

    @InjectMocks
    TransferenceService service;

    @Mock
    TransferenceRepository transferenceRepository;

    @Mock
    WalletRepository walletRepository;

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

        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {

        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if any provided wallet ID is not found")
        void methodShouldThrowNonExistentWalletExceptionIfAnyProvidedWalletIdIsNotFound() {

        }

        @Test
        @DisplayName("Method should throw OwnerTypeException if owner type is not allowed to make transfers")
        void methodShouldThrowOwnerTypeExceptionIfOwnerTypeIsNotAllowedToMakeTransfers() {

        }

        @Test
        @DisplayName("Method should throw InsufficientBalanceException if payer wallet has not enough balance")
        void methodShouldThrowInsufficientBalanceExceptionIfPayerWalletHasNotEnoughBalance() {

        }

        @Test
        @DisplayName("Method should throw UnauthorizedTransferException if payer is not authorized")
        void methodShouldThrowUnauthorizedTransferExceptionIfPayerIsNotAuthorized() {

        }
    }

    @Nested
    class getById {

        @Test
        @DisplayName("Method should successfully return transference corresponding to provided id")
        void methodShouldSuccessfullyReturnTransferenceCorrespondingToProvidedId() {

        }

        @Test
        @DisplayName("Method should throw NonExistentTransferenceException if provided id is not found")
        void methodShouldThrowNonExistentTransferenceExceptionIfProvidedIdIsNotFound() {

        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {

        }
    }

    @Nested
    class listAllTransfersSentByWalletId {

        @Test
        @DisplayName("Method should successfully return all transferences sent by provided ID")
        void methodShouldSuccessfullyReturnAllTransferencesSentByProvidedWalletId() {

        }

        @Test
        @DisplayName("Method should return empty list if there are no transferences sent")
        void methodShouldReturnEmptyListIfThereAreNoTransferencesSent() {

        }
    }

    @Nested
    class listAllTransfersReceivedByWalletId {

        @Test
        @DisplayName("Method should successfully return all transferences received by provided ID")
        void methodShouldSuccessfullyReturnAllTransferencesReceivedByProvidedWalletId() {

        }

        @Test
        @DisplayName("Method should return empty list if there are no transferences received")
        void methodShouldReturnEmptyListIfThereAreNoTransferencesReceived() {

        }
    }
}
