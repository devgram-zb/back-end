package com.project.devgram.oauth2.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Redis CRUD Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisCrudTest {

    @Autowired
    private TokenRedisRepository tokenRedisRepository;

    private RedisUser redisUser;

    @BeforeEach
    void setUp() {
        redisUser = new RedisUser("P0001", "RTK", "token",12000L);
    }

    @AfterEach
    void teardown() {
        tokenRedisRepository.deleteById(redisUser.getId());
    }

    @Test
    @DisplayName("Redis 에 데이터를 저장하면 정상적으로 조회되어야 한다")
    void redis_save_test() {
        // given
        tokenRedisRepository.save(redisUser);

        // when
        RedisUser persistProduct = tokenRedisRepository.findById(redisUser.getId())
                .orElseThrow(RuntimeException::new);

        // then
        assertThat(persistProduct.getId()).isEqualTo("P0001");
        assertThat(persistProduct.getToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("Redis 에 데이터를 수정하면 정상적으로 수정되어야 한다")
    void redis_update_test() {
        // given
        tokenRedisRepository.save(redisUser);
        RedisUser persistProduct = tokenRedisRepository.findById(redisUser.getId())
                .orElseThrow(RuntimeException::new);

        // when
        persistProduct.setToken("newToken");
        tokenRedisRepository.save(persistProduct);

        // then
        assertThat(persistProduct.getToken()).isEqualTo("newToken");
    }

    @Test
    @DisplayName("Redis 에 데이터를 삭제하면 정상적으로 삭제되어야 한다")
    void redis_delete_test() {
        // given
        tokenRedisRepository.save(redisUser);

        // when
        tokenRedisRepository.delete(redisUser);
        Optional<RedisUser> deletedProduct = tokenRedisRepository.findById(redisUser.getId());

        // then
        assertTrue(deletedProduct.isEmpty());
    }
}