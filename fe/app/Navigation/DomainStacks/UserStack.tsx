import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

import MyPage from "@/pages/user/MyPage";
import MySettingPage from "@/pages/user/MySettingPage";
import BlockedUserPage from "@/pages/user/BlockedUserPage";

// 여기에 User 도메인의 페이지들을 import
// import UserMain from "";

const Stack = createNativeStackNavigator();

const UserStack = () => {
  return (
    <Stack.Navigator initialRouteName="UserMain">
      <Stack.Screen
        name="UserMain"
        component={MyPage}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="MySetting"
        component={MySettingPage}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="BlockedUser"
        component={BlockedUserPage}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

export default UserStack;
