package likelion15.mutsa.service;

import likelion15.mutsa.entity.User;
import likelion15.mutsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final List<User> userList = new ArrayList<>();
    private final UserRepository repository;

    // 회원가입 - 유저 객체 추가
    public User joinUser(User user) {
        // User Entity에 setter가 없어서 setter추가 안하고 builder로 id추가함.

        userList.add(user);
        return user;
    }

    // 해당하는 id의 객체를 찾아 반환
    public User readUser(Long id) {
        for (User user : userList) {
            if(user.getId().equals(id))
                return user;
        }
        return null;
    }

    // email,password정보에 해당하는 id를 반환
    public Long login(String email, String password) {
        for (User user : userList) {
            if(user.getEmail().equals(email)){
                if(user.getPassword().equals(password)){
                    System.out.println("로그인 성공");
                    return 1L; // db에서 id받아오기 전에 임시로 설정
                }else{ //비밀번호가 일치하지 않는 경우
                    System.out.println("비밀번호를 다시 확인해주세요.");
                }
            }else{
                System.out.println("존재하지 않는 아이디입니다.");
            }
        }

        return null;
    }
}
