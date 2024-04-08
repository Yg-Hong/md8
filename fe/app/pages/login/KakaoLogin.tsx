// 카카오 로그인으로 이동하는 웹 뷰 화면
import React, { useEffect } from "react";
import { WebView } from "react-native-webview";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useNavigation } from "@react-navigation/native";
import {
  EmailAtom,
  NicknameAtom,
  ProfileAtom,
  TokenAtom,
  UserIdAtom,
} from "@/recoil/UserAtom";
import { useRecoilState, useSetRecoilState } from "recoil";
import queryString from "query-string";

// REST API 키
// import { KAKAO_REST_API_KEY } from "react-native-dotenv";
const REST_API_KEY = "a195ca620b84b8b9caa340ee0c95d95c";
const REDIRECT_URI = "http://j10a208.p.ssafy.io:8443/login/oauth/code/kakao";
const INJECTED_JAVASCRIPT = `window.ReactNativeWebView.postMessage('message from webView')`;
const URI = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

const KakaoLogin = () => {
  const navigation = useNavigation();

  // 추출한 쿼리값들을 로컬에 저장하는 함수
  const saveToLocal = async (queryParams) => {
    await AsyncStorage.setItem("email", queryParams.email);
    await AsyncStorage.setItem("nickname", queryParams.nickname);
    await AsyncStorage.setItem("profile", queryParams.profile);
    await AsyncStorage.setItem("token", queryParams.token);
    await AsyncStorage.setItem("userId", queryParams.userId);
  };

  // 추출한 쿼리값을 recoil에 저장하는 함수
  const setEmail = useSetRecoilState(EmailAtom);
  const setNickname = useSetRecoilState(NicknameAtom);
  const setProfile = useSetRecoilState(ProfileAtom);
  const setToken = useSetRecoilState(TokenAtom);
  const setUserId = useSetRecoilState(UserIdAtom);

  const saveToRecoil = async (queryParams) => {
    setEmail(() => queryParams.email);
    setNickname(() => queryParams.nickname);
    setProfile(() => queryParams.profile);
    setToken(() => queryParams.token);
    setUserId(() => queryParams.userId);
  };

  // 웹뷰의 navState가 바뀔 때마다 호출되는 함수(쿼리 읽어오기 위함)
  const handleNavigationStateChange = async (navState) => {
    const { url } = navState;
    // console.log("1. url :", url);
    // console.log(navState);

    if (url.startsWith("myapp://callback")) {
      // query-string 라이브러리로 URL 파싱
      const queryParams = queryString.parseUrl(url).query;
      console.log("2. queryParams :", queryParams);

      // 추출한 값들을 로컬에 저장
      await saveToLocal(queryParams);
      // await saveToRecoil(queryParams);

      // 로컬에서 토큰이 유효하면 메인 페이지로 이동
      navigation.navigate("BasicProfileSetting");
    } else {
      console.log("if문 안들어감");
    }
  };

  return (
    <WebView
      style={{ flex: 1 }}
      originWhitelist={["*"]}
      scalesPageToFit={false}
      source={{
        uri: URI,
      }}
      injectedJavaScript={INJECTED_JAVASCRIPT}
      javaScriptEnabled
      onNavigationStateChange={handleNavigationStateChange}
    />
  );
};

export default KakaoLogin;
