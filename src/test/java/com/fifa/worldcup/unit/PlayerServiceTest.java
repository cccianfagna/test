package com.fifa.worldcup.unit;

import com.fifa.worldcup.domain.Player;
import com.fifa.worldcup.repository.PlayerRepository;
import com.fifa.worldcup.service.PlayerService;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void savingAValidPlayer() {
        // given
        Player aPlayer = new Player("Juan", "Pedro");

        // when
        playerService.save(aPlayer);

        // then
        verify(playerRepository, times(1)).save(aPlayer);
    }

    @Test
    public void aTooShortNameAndLastNamePlayer() {
        // given
        Player aPlayer = new Player("Cris", "Cian");

        // when
        ThrowingCallable throwingCallable =
                () -> playerService.save(aPlayer);

        // then
        verify(playerRepository, never()).save(aPlayer);
        assertThatThrownBy(throwingCallable).
                isInstanceOf(RuntimeException.class).
                hasMessage("No valid player (Cris Cian). Length of (name + lastName) < 10");
    }
}
