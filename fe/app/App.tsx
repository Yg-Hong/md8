import colors from "@/assets/color.js";
import React, { useCallback, useEffect, useState } from "react";
import { StyleSheet, Text, View, Button } from "react-native";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { useFonts } from "expo-font";
import { StatusBar } from "expo-status-bar";
import * as SplashScreen from "expo-splash-screen";
import LoginMain from "@/pages/login/LoginMain";

// 네비게이션
import { NavigationContainer } from "@react-navigation/native";
import RootStackNavigator from "./Navigation/RootStackNavigator";

// 리코일
import { RecoilRoot } from "recoil";

SplashScreen.preventAutoHideAsync();

export default function App() {
  // 기본 폰트 설정 ===================================
  const [isLoaded] = useFonts({
    font3: require("./assets/fonts/SCDream3.otf"),
    font4: require("./assets/fonts/SCDream4.otf"),
    font5: require("./assets/fonts/SCDream5.otf"),
    font6: require("./assets/fonts/SCDream5.otf"),
  });

  const onLayoutRootView = useCallback(async () => {
    if (isLoaded) {
      await SplashScreen.hideAsync();
    }
  }, [isLoaded]);

  if (!isLoaded) {
    return null;
  }
  // =================================================

  const changeToggle = () => {};

  return (
    <RecoilRoot>
      <GestureHandlerRootView
        style={styles.container}
        onLayout={onLayoutRootView}
      >
        {/* <StatusBar style="auto" /> */}

        <NavigationContainer>
          <RootStackNavigator />
        </NavigationContainer>
      </GestureHandlerRootView>
    </RecoilRoot>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: "yellow",
  },
});
