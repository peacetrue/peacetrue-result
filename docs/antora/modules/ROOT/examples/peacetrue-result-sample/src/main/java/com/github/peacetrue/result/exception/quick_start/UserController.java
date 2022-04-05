package com.github.peacetrue.result.exception.quick_start;

import com.github.peacetrue.result.exception.ResultExceptionThrowService;
import com.github.peacetrue.spring.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author peace
 **/
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //tag::getUser[]

    @GetMapping("/users")
    public UserVO getUser(@RequestParam Long id) {//<1>
        User user = userRepository.getOne(id);
        return BeanUtils.convert(user, UserVO.class);
    }
    //end::getUser[]


    //tag::result-exception[]
    @Autowired
    private ResultExceptionThrowService resultExceptionThrowService;

    @RequestMapping("/login/result-exception")
    public void loginWithResultException(UserDTO params) {
        User user = BeanUtils.convert(params, User.class);
        Optional<User> optional = userRepository.findOne(Example.of(user));
        if (!optional.isPresent()) {
            resultExceptionThrowService.throwDataResultException("user_not_exist", user);
        }
    }
    //end::result-exception[]

    //tag::custom-exception[]

    @RequestMapping("/login/custom-exception")
    public void loginWithCustomException(UserDTO params) {
        User user = BeanUtils.convert(params, User.class);
        Optional<User> optional = userRepository.findOne(Example.of(user));
        if (!optional.isPresent()) {
            throw new UserNotExistException(user);
        }
    }
    //end::custom-exception[]

}
