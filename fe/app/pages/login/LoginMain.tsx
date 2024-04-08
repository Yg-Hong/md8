import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, Image, TouchableOpacity } from "react-native";
import { useNavigation } from "@react-navigation/native";
import LoginBig from "@images/LogoBig.png";
import KakaoLogo from "@images/KakaoLogo.png";

const LoginMain = () => {
  // 카카오 로그인 웹뷰로 이동하는 함수
  const navigation = useNavigation();
  const toKakaoLogin = () => {
    navigation.navigate("KakaoLogin");
    console.log("카카오로 이동");
  };

  return (
    <View style={styles.container}>
      <Image style={styles.bigLogo} source={LoginBig}></Image>

      <View style={styles.textContainer}>
        <Text style={styles.titleText}>문득</Text>
        <Text style={styles.contentText}>문을 열고 나가면 문득 있는 세상</Text>
      </View>

      <TouchableOpacity
        style={styles.button}
        activeOpacity={0.7}
        onPress={toKakaoLogin}
      >
        <Image style={styles.kakaoLogo} source={KakaoLogo}></Image>
        <Text style={styles.buttonText}>카카오로 시작하기</Text>
      </TouchableOpacity>

      <View>
        <Text style={{ ...styles.contentText, textAlign: "center" }}>
          간편하게 로그인하고
        </Text>
        <Text style={{ ...styles.contentText, textAlign: "center" }}>
          다양한 서비스를 경험해보세요
        </Text>
      </View>
    </View>
  );
};

export default LoginMain;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#ffffff",
  },
  bigLogo: {
    width: 100,
    height: 130,
    marginBottom: 10,
  },
  textContainer: {
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  titleText: {
    fontFamily: "font6",
    fontSize: 40,
    color: colors.darkbrown,
  },
  contentText: {
    fontFamily: "font3",
    fontSize: 16,
    color: colors.darkbrown,
  },
  button: {
    width: "90%",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    gap: 10,
    marginTop: 50,
    marginBottom: 20,
    paddingVertical: 13,
    backgroundColor: "#fee500",
    borderRadius: 5,
  },
  buttonText: {
    textAlign: "center",
    fontFamily: "font5",
    fontSize: 14,
    color: colors.darkbrown,
  },
  kakaoLogo: {
    width: 20,
    height: 20,
    resizeMode: "contain",
  },
});
