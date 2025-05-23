//package com.se.user.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface TokenRepository extends JpaRepository<Token, Long> {
//    @Query("""
//        select t from Token t inner join User u on t.user.id = u.id
//        where u.id = :userId and (t.expired = false or t.revoked = false)
//    """)
//    List<Token> findAllValidTokensByUser(@Param("userId") Long userId);
//
//    Optional<Token> findByToken(String token);
//
//    @Query("""
//        select t from Token t
//        where t.token = :token and t.tokenType = :tokenType
//    """)
//    Optional<Token> findByTokenAndTokenType(@Param("token") String token, @Param("tokenType") Token.TokenType tokenType);
//
//    @Query("""
//        select t from Token t inner join User u on t.user.id = u.id
//        where u.id = :userId and t.tokenType = :tokenType and (t.expired = false or t.revoked = false)
//    """)
//    Optional<Token> findValidTokenByUser(@Param("userId") Long userId, @Param("tokenType") String tokenType);
//}