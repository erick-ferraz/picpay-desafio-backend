package br.com.picpay.service;

import br.com.picpay.entity.dto.WalletRequestDto;
import br.com.picpay.entity.enums.Type;
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

        }

        @Test
        @DisplayName("Method should throw AlreadyRegisteredDataException if email or cpfcnpj already exists")
        void methodShouldThrowAlreadyRegisteredDataExceptionIfEmailOrCpfcnpjAlreadyExists() {

        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {

        }
    }

    @Nested
    class getById {

        @Test
        @DisplayName("Method should successfully return wallet corresponding to provided id")
        void methodShouldSuccessfullyReturnWalletCorrespondingToProvidedId() {

        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if provided id is not found")
        void methodShouldThrowNonExistentWalletExceptionIfProvidedIdIsNotFound() {

        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {

        }
    }

    @Nested
    class getAll {

        @Test
        @DisplayName("Method should successfully return all registered wallets")
        void methodShouldSuccessfullyReturnAllRegisteredWallets() {

        }

        @Test
        @DisplayName("Method should return empty list if there are no wallets")
        void methodShouldReturnEmptyListIfThereAreNoWallets() {

        }
    }

    @Nested
    class updateById {

        @Test
        @DisplayName("Method should successfully update and persist wallet with new data")
        void methodShouldSuccessfullyUpdateAndPersistWalletWithNewData() {

        }

        @Test
        @DisplayName("Method should throw AlreadyRegisteredDataException if email or cpfcnpj already exists")
        void methodShouldThrowAlreadyRegisteredDataExceptionIfEmailOrCpfcnpjAlreadyExists() {

        }

        @Test
        @DisplayName("Method should throw InputValidationException if any required field is not provided")
        void methodShouldThrowInputValidationExceptionIfAnyRequiredFieldIsNotProvided() {

        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Method should successfully delete wallet corresponding to provided id")
        void methodShouldSuccessfullyReturnWalletCorrespondingToProvidedId() {

        }

        @Test
        @DisplayName("Method should throw NonExistentWalletException if provided id is not found")
        void methodShouldThrowNonExistentWalletExceptionIfProvidedIdIsNotFound() {

        }

        @Test
        @DisplayName("Method should throw DefaultException if wallet still has balance")
        void methodShouldThrowDefaultExceptionIfWalletStillHasBalance() {

        }
    }
}
