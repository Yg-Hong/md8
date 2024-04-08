import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { useEffect, useState } from "react";
import { StyleSheet } from "react-native";
import colors from "@/assets/color";

// TabNavigator import
import TabNavigator from "./TabNavigator";

// 페이지 import
import LoginMain from "@/pages/login/LoginMain";
import KakaoLogin from "../pages/login/KakaoLogin";
import BasicProfileSetting from "@/pages/login/BasicProfileSetting";

// 로컬 스토리지 import
import AsyncStorage from "@react-native-async-storage/async-storage";

// recoil import
import { TokenAtom } from "@/recoil/UserAtom";
import { useRecoilValue, useRecoilState, constSelector } from "recoil";

const Stack = createNativeStackNavigator();

const RootStackNavigator = () => {
  // async에서 토큰 조회해서 isLogined 상태 바꾸는 함수
  const [isLogined, setIsLogined] = useState(false);

  const checkIsLogined = async () => {
    try {
      const value = await AsyncStorage.getItem("token");
      if (value) {
        setIsLogined(true);
        console.log(`RootStack : token이 ${value}로 존재함 -> 메인페이지로`);
      } else {
        console.log(`RootStack : token이 존재하지 않음 -> 로그인페이지로`);
      }
    } catch (e) {
      console.log("checkIsLogined 에러", e);
    }
  };

  // 페이지 렌더링 될 때마다 isLogined 상태 조회하기
  useEffect(() => {
    checkIsLogined();
  }, []);

  return (
    <Stack.Navigator>
      {isLogined ? (
        <Stack.Screen
          name="TabNavigator"
          component={TabNavigator}
          options={{ headerShown: false }}
        />
      ) : (
        <Stack.Screen
          name="LoginMain"
          component={LoginMain}
          options={{ headerShown: false }}
        />
      )}
      <Stack.Screen
        name="KakaoLogin"
        component={KakaoLogin}
        options={{
          title: "카카오로 로그인",
          headerTitleStyle: styles.headerText,
        }}
      />
      <Stack.Screen
        name="AfterTabNavigator"
        component={TabNavigator}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="BasicProfileSetting"
        component={BasicProfileSetting}
        options={{ title: "프로필 설정" }}
      />
    </Stack.Navigator>
  );
};

export default RootStackNavigator;

const styles = StyleSheet.create({
  headerText: {
    fontFamily: "font3",
    fontSize: 17,
    color: colors.darkbrown,
  },
});
