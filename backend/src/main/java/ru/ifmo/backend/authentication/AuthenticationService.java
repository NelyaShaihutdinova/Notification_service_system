package ru.ifmo.backend.authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.ifmo.backend.authentication.validators.BindingChecker;
import ru.ifmo.backend.authentication.dto.LoginDTO;
import ru.ifmo.backend.authentication.dto.RegistrationDTO;
import ru.ifmo.backend.authentication.email.EmailService;
import ru.ifmo.backend.authentication.exceptions.BounceMessageException;
import ru.ifmo.backend.authentication.exceptions.ValidException;
import ru.ifmo.backend.authentication.responses.HttpResponse;
import ru.ifmo.backend.authentication.responses.PersonView;
import ru.ifmo.backend.authentication.responses.ResponseConstructor;
import ru.ifmo.backend.authentication.validators.*;
import ru.ifmo.backend.security.JwtUtil;
import ru.ifmo.backend.user.Person;
import ru.ifmo.backend.user.PersonDetails;
import ru.ifmo.backend.user.services.PeopleService;
import ru.ifmo.backend.util.ObjectConverter;

import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class AuthenticationService {
  private final ResponseConstructor constructor;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RegistrationValidator registrationValidator;
  private final BindingChecker checker;
  private final ObjectConverter objectConverter;
  private final PeopleService peopleService;
  private final JwtUtil jwtUtil;
  private final EmailService emailService;
  private final DomainValidator domainValidator;
  private static final String SUCCESSFULLY_LOGIN = "successfully login";
  private static final String SUCCESSFULLY_REGISTER = "successfully register";
  private static final String SUCCESSFULLY_RESEND = "successfully resend a token";

  @Autowired
  public AuthenticationService(
      ResponseConstructor constructor,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      RegistrationValidator registrationValidator,
      BindingChecker checker,
      ObjectConverter objectConverter,
      PeopleService peopleService,
      JwtUtil jwtUtil,
      EmailService emailService,
      DomainValidator domainValidator) {
    this.constructor = constructor;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.registrationValidator = registrationValidator;
    this.checker = checker;
    this.objectConverter = objectConverter;
    this.peopleService = peopleService;
    this.jwtUtil = jwtUtil;
    this.emailService = emailService;
    this.domainValidator = domainValidator;
  }

  public ResponseEntity<HttpResponse> register(
      RegistrationDTO registrationDTO, BindingResult bindingResult)
      throws ValidException, MailException, UnknownHostException, BounceMessageException {

    domainValidator.throwExceptionIfDomainNotExists(registrationDTO.getEmail());
    registrationValidator.validate(registrationDTO, bindingResult);
    checker.throwIfBindResultHasErrors(bindingResult);

    Person converted = objectConverter.convertToObject(registrationDTO, Person.class);
    converted.setPassword(passwordEncoder.encode(converted.getPassword()));

    Person saved = peopleService.save(converted);

    String token = jwtUtil.generateTokenWithConfirmExpirationTime(saved.getEmail());

    emailService.sendConfirmAccountMessage(saved.getUsername(), saved.getEmail(), token);

    return constructor.buildResponseEntityWithToken(
        constructor.buildHttpResponseWithoutView(SUCCESSFULLY_REGISTER), token, HttpStatus.CREATED);
  }

  public ResponseEntity<HttpResponse> login(LoginDTO loginDTO, BindingResult bindingResult)
      throws ValidException {

    checker.throwIfBindResultHasErrors(bindingResult);

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

    Person person = ((PersonDetails) authentication.getPrincipal()).getPerson();
    String token = jwtUtil.generateTokenWithCommonUserTime(person.getEmail());

    PersonView view = objectConverter.convertToObject(person, PersonView.class);

    return constructor.buildResponseEntityWithToken(
        constructor.buildHttpResponseWithView(view, SUCCESSFULLY_LOGIN), token, HttpStatus.OK);
  }

  public void confirmAccount(String token)
      throws JWTVerificationException, UsernameNotFoundException {

    String email = jwtUtil.validateTokenAndRetrieveClaim(token);
    Optional<Person> personOptional = peopleService.findByEmail(email);

    if (personOptional.isPresent()) {

      Person person = personOptional.get();
      person.setEnable(true);
      peopleService.update(person);

    } else {
      throw new UsernameNotFoundException(email);
    }
  }

  public ResponseEntity<HttpResponse> resendConfirmToken(
      LoginDTO loginDTO, BindingResult bindingResult)
      throws ValidException, UsernameNotFoundException {

    checker.throwIfBindResultHasErrors(bindingResult);

    Optional<Person> personOptional = findAndValidatePerson(loginDTO, bindingResult);

    String token = jwtUtil.generateTokenWithConfirmExpirationTime(loginDTO.getEmail());
    emailService.sendConfirmAccountMessage(
        personOptional.map(Person::getUsername).orElse(null), loginDTO.getEmail(), token);

    return constructor.buildResponseEntityWithToken(
        constructor.buildHttpResponseWithoutView(SUCCESSFULLY_RESEND), token, HttpStatus.OK);
  }

  private Optional<Person> findAndValidatePerson(LoginDTO loginDTO, BindingResult bindingResult)
      throws ValidException {
    Optional<Person> personOptional = peopleService.findByEmail(loginDTO.getEmail());

    if (personOptional.isPresent()) {
      Person person = personOptional.get();
      boolean passwordMatches =
          passwordEncoder.matches(loginDTO.getPassword(), person.getPassword());

      if (!passwordMatches) {
        bindingResult.rejectValue("password", "", "Invalid password");
      }
    } else {
      bindingResult.rejectValue("email", "", "Email not found");
    }

    checker.throwIfBindResultHasErrors(bindingResult);

    return personOptional;
  }
}
