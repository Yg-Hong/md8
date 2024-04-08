// package ms8.domain.global.auth.entity;
//
// import com.fasterxml.jackson.annotation.JsonIgnore;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToOne;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import ms8.domain.feature.user.entity.User;
//
// @Entity
// @Getter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// @Setter
// public class Token {
//
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "token_id")
//     private Integer id;
//
//
//     @Column(name = "refresh_token")
//     private String refreshToken;
//
//     @JsonIgnore
//     @OneToOne
//     @JoinColumn(name = "user_id")
//     private User user;
// }
