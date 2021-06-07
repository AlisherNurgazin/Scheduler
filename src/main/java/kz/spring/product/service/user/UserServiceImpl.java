package kz.spring.product.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.impl.DefaultClaims;
import kz.spring.product.config.security.jwt.TokenAuthentication;
import kz.spring.product.constants.ClaimsKeysConstants;
import kz.spring.product.constants.StatusConstants;
import kz.spring.product.exceptions.*;
import kz.spring.product.model.Role;
import kz.spring.product.model.User;
import kz.spring.product.model.dto.request.LoginRequest;
import kz.spring.product.model.dto.request.RegisterRequest;
import kz.spring.product.model.dto.response.TokenResponse;
import kz.spring.product.repository.user.UserRepository;
import kz.spring.product.service.role.RoleService;
import kz.spring.product.service.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.roleService = roleService;
    }


    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(()->new CustomNotFoundException(String.format("" +
                "User with login : %s not found" , login)));
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        boolean existInDb = userRepository.existsByLogin(request.getLogin());
        if (existInDb){
            throw new CustomConflictException(String.format("User with login : %s already exists" , request.getLogin()));
        }
        Role role = roleService.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = new User(request , StatusConstants.ACTIVATED, roles);
        save(user);
        return new ResponseEntity<>("Registered" , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest){
        User user = getByLogin(loginRequest.getLogin());
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                throw new CustomBadRequestException("Неправильный пароль");
            if (user.getStatus().equals("blocked"))
                throw new CustomAuthenticationException("Пользователь заблокирован");
            if (user.getStatus().equals("non-active"))
                throw new CustomAuthenticationException("Активируйте свой аккаунт");
            if (user.getStatus().equals("deactivated"))
                throw new CustomAuthenticationException("Аккаунт деактивирован. Вы можете авторизоваться " +
                        "чтобы восстановить аккаунт");
            Map<String, Object> claims = new HashMap<>();
            claims.put(ClaimsKeysConstants.USERNAME, user.getLogin());
            claims.put(ClaimsKeysConstants.ROLES, user.getRoles());
            TokenResponse tokensResponse = tokenService.generateTokensResponse(claims);
            return new ResponseEntity<>(tokensResponse, HttpStatus.OK);


    }

    @Override
    public User getAuth() {
        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        logger.debug(String.format("Auth credentials by user %s", authentication.getToken()));
        return authentication.getUser();
    }



    @Override
    public Map<String, Object> refreshToken(String token) {
        if (!tokenService.tokenValidation(token))
            throw new BadRequestException("It's not valid refresh token");

        DefaultClaims claims = tokenService.getClaimsFromToken(token);
        Map<String, Object> newClaims = new HashMap<>();
        if (!claims.get(ClaimsKeysConstants.IS_REFRESH_TOKEN, Boolean.class))
            throw new BadRequestException("It's not valid refresh token");

        String login = claims.get(ClaimsKeysConstants.USERNAME, String.class);

        TokenResponse tokensResponse;

        User userEmail = getByLogin(login);
        List<String> roles = new ArrayList<>();
        List<Role> userRoles = userEmail.getRoles();
        userEmail.getRoles().stream().forEach(x -> roles.add(x.getName()));
        newClaims.put(ClaimsKeysConstants.USERNAME, userEmail.getLogin());
        tokensResponse = tokenService.generateTokensResponse(claims);
        Map<String , Object> answer = new HashMap<>();
        answer.put("accessToken" , tokensResponse.getAccessToken());
        answer.put("refreshToken" , tokensResponse.getRefreshToken());
        return answer;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


}
