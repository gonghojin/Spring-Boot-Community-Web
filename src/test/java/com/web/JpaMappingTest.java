package com.web;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

    private final String boardTestTitle = "테스트";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init() {
        User user = userRepository.save(User.builder()
                .name("havi")
                .password("test")
                .email(email)
                .build()
        );

        boardRepository.save(Board.builder()
                .title(boardTestTitle)
                .subTitle("서브 타이틀")
                .content("컨텐츠")
                .boardType(BoardType.free)
                .user(user).build());
    }

    @Test
    public void save_테스트() {
        User user = userRepository.findByEmail(email);
        assertThat(user.getName()).isEqualTo("havi");
        assertThat(user.getPassword()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo(email);

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle()).isEqualTo(boardTestTitle);
        assertThat(board.getSubTitle()).isEqualTo("서브 타이틀");
        assertThat(board.getContent()).isEqualTo("컨텐츠");
        assertThat(board.getBoardType()).isEqualTo(BoardType.free);
    }
}
