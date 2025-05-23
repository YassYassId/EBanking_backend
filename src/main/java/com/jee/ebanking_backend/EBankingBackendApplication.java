package com.jee.ebanking_backend;

import com.jee.ebanking_backend.dtos.BankAccountDTO;
import com.jee.ebanking_backend.dtos.CurrentBankAccountDTO;
import com.jee.ebanking_backend.dtos.CustomerDTO;
import com.jee.ebanking_backend.dtos.SavingBankAccountDTO;
import com.jee.ebanking_backend.entities.*;
import com.jee.ebanking_backend.enums.AccountStatus;
import com.jee.ebanking_backend.enums.OperationType;
import com.jee.ebanking_backend.enums.Role;
import com.jee.ebanking_backend.exceptions.CustomerNotFoundException;
import com.jee.ebanking_backend.repositories.AccountOperationRepository;
import com.jee.ebanking_backend.repositories.BankAccountRepository;
import com.jee.ebanking_backend.repositories.CustomerRepository;
import com.jee.ebanking_backend.repositories.UserRepository;
import com.jee.ebanking_backend.services.BankAccountService;
import com.jee.ebanking_backend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            User user = new User();
//            user.setName("Yassine");
//            user.setEmail("admin@gmail.com");
//            user.setPassword(passwordEncoder.encode("admin123"));
//            user.setRole(Role.ADMIN);
//            userRepository.save(user);
//        };
//    }
}
