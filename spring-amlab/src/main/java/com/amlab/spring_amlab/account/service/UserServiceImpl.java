package com.amlab.spring_amlab.account.service;

import com.amlab.spring_amlab.account.entity.User;
import com.amlab.spring_amlab.account.exception.InvalidPasswordException;
import com.amlab.spring_amlab.account.exception.UserNameNotFoundException;
import com.amlab.spring_amlab.account.repository.UserRepository;
import com.amlab.spring_amlab.account.service.request.CreateUserRequest;
import com.amlab.spring_amlab.account.service.request.LoginUserRequest;
import com.amlab.spring_amlab.account.service.response.CreateUserResponse;
import com.amlab.spring_amlab.account.service.response.LoginUserResponse;
import com.amlab.spring_amlab.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{8,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{1,20}$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{1,10}$");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 해시화 도구
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        List<String> errors = new ArrayList<>();

        // 1. username 검증
        if (!USERNAME_PATTERN.matcher(request.getUsername()).matches()) {
            errors.add("아이디는 8~20자의 영소문자와 숫자만 가능합니다.");
        } else if (userRepository.existsByUsername(request.getUsername())) {
            errors.add("이미 사용 중인 아이디입니다.");
        }

        // 2. password 검증 (평문 상태로 검증!)
        if (!PASSWORD_PATTERN.matcher(request.getPassword()).matches()) {
            errors.add("비밀번호는 1~20자여야 합니다.");
        }

        // 3. nickname 검증
        if (!NICKNAME_PATTERN.matcher(request.getNickname()).matches()) {
            errors.add("닉네임은 한글, 영어, 숫자 1~10자만 가능합니다.");
        } else if (userRepository.existsByNickname(request.getNickname())) {
            errors.add("이미 사용 중인 닉네임입니다.");
        }

        // 4. 에러가 있으면 실패 반환
        if (!errors.isEmpty()) {
            return new CreateUserResponse(false, errors);
        }

        // 5. 모든 검증 통과 - 사용자 생성
        //  비밀번호 해시화
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                hashedPassword,  // 해시화된 비밀번호 저장
                request.getNickname()
        );

        userRepository.save(user);

        return new CreateUserResponse(true, List.of());
    }

    @Override
    @Transactional
    public LoginUserResponse loginUser(LoginUserRequest request) {
        //유저 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new UserNameNotFoundException("존재하지 않는 사용자입니다."));
        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JWT 생성
        String accessToken = jwtProvider.createToken(user.getId());

        // 4. 응답 반환
        return new LoginUserResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                accessToken
        );


    }
}