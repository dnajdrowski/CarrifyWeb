package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Car.Car;
import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.model.Rent.Rent;
import com.carrify.web.carrifyweb.model.Rent.RentDTO;
import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.model.Variable.Variable;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.carrify.web.carrifyweb.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static com.carrify.web.carrifyweb.model.Transaction.TransactionDTO.TYPE_CHARGE;
import static java.util.stream.Collectors.toList;


@Slf4j
@Service
@AllArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    private final DriverLicenceRepository driverLicenceRepository;
    private final UserRepository userRepository;
    private final VariableRepository variableRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final ReservationService reservationService;

    @Transactional
    public List<RentDTO> getAllRents() {
        Iterable<Rent> rents = rentRepository.findAll();
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
    public List<RentDTO> getAllUserRents(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Iterable<Rent> rents = rentRepository.findAllByUser_Id(id);
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
    public RentDTO getUserActiveRent(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<Rent> rentOptional = rentRepository.findFirstByUser_IdAndEndAtIsNull(id);
        if (rentOptional.isPresent()) {
            return new RentDTO(rentOptional.get());
        } else {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
    }

    @Transactional
    public List<RentDTO> getAllCarRents(String carId) {
        int id;
        try {
            id = Integer.parseInt(carId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }

        Iterable<Rent> rents = rentRepository.findAllByCar_Id(id);
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
    public RentDTO getRentById(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rent = rentRepository.findById(id);
        if (rent.isPresent()) {
            return new RentDTO(rent.get());
        } else {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }
    }

    @Transactional
    public RentDTO finishRent(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Rent rent = rentOptional.get();

        if (rent.getEndAt() != null) {
            throw new ApiBadRequestException(CARRIFY015_MSG, CARRIFY015_CODE);
        }

        Optional<Car> carOptional = carRepository.findById(rent.getCar().getId());

        if (carOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }

        Optional<Variable> pricePerMinuteOptional = variableRepository.findByName("pricePerMinute");

        if (pricePerMinuteOptional.isEmpty()) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        Optional<Variable> pricePerKmOptional = variableRepository.findByName("pricePerKm");

        if (pricePerKmOptional.isEmpty()) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        int pricePerMinute;
        int pricePerKm;

        try {
            pricePerKm = Integer.parseInt(pricePerKmOptional.get().getValue());
            pricePerMinute = Integer.parseInt(pricePerMinuteOptional.get().getValue());
        } catch (NumberFormatException e) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        Car car = carOptional.get();
        car.setCarState(1);
        carRepository.save(car);

        LocalDateTime rentalStartTime = rent.getCreatedAt();
        LocalDateTime rentalFinishTime = LocalDateTime.now();
        long rentTimeInMinutes = Duration.between(rentalStartTime, rentalFinishTime).toMinutes();
        int amount = 2000 / 1000 * pricePerKm + (int) rentTimeInMinutes * pricePerMinute;
        rent.setEndAt(rentalFinishTime);
        rent.setDistance(2000);
        rent.setAmount(amount);
        rentRepository.save(rent);

        Optional<Wallet> optionalWallet = walletRepository.findById(rent.getUser().getId());

        if (optionalWallet.isEmpty()) {
            //TODO
        }

        Wallet wallet = optionalWallet.get();

        int balance = wallet.getAmount() - amount;

        if (balance < 0) {
            throw new ApiBadRequestException(CARRIFY028_MSG, CARRIFY028_CODE);
        }

        wallet.setAmount(wallet.getAmount() - amount);
        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .balance(balance)
                .operationType(TYPE_CHARGE)
                .createdAt(rentalFinishTime)
                .rent(rent)
                .wallet(wallet)
                .build();

        transactionRepository.save(transaction);

        return new RentDTO(rent);
    }

    @Transactional
    public RentDTO startRent(Integer userId, Integer carId) {

        Optional<DriverLicence> driverLicence = driverLicenceRepository.findById(userId);

        if (driverLicence.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY024_MSG, CARRIFY024_CODE);
        }

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        if (userOptional.get().getVerified() == 0) {
            throw new ApiBadRequestException(CARRIFY025_MSG, CARRIFY025_CODE);
        }

        if (rentRepository.findFirstByUser_IdAndEndAtIsNull(userId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY016_MSG, CARRIFY016_CODE);
        }

        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }

        if (rentRepository.findFirstByCar_IdAndEndAtIsNull(carId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY017_MSG, CARRIFY017_CODE);
        }

        if (reservationService.existsReservationOnOtherCarByUserId(userId, carId))
            throw new ApiBadRequestException(CARRIFY035_MSG, CARRIFY035_CODE);

        Rent rent = Rent.builder()
                .distance(0)
                .createdAt(LocalDateTime.now())
                .car(carOptional.get())
                .user(userOptional.get())
                .build();

        Rent savedRent = rentRepository.save(rent);

        if (savedRent == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }
        return new RentDTO(savedRent);
    }
}
