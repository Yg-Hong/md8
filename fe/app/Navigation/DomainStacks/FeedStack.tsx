import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, ScrollView } from "react-native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// 여기에 Feed 도메인의 페이지들을 import
import FeedMainPage from "@/templates/Feed/FeedMain";
import FeedDetail from "@/templates/Feed/FeedDetail";
import FeedSearch from "@/templates/Feed/FeedSearch";
import FeedCreate from "@/templates/Feed/FeedCreate";
// import FeedCreatePage from "@/pages/feed/FeedCreatePage";
import SelectTrackingPage from "@/pages/feed/SelectTrackingPage";
import FeedFollowList from "@/templates/Feed/FeedFollowList";
import FeedDetailSearch from "@/templates/Feed/FeedDetailSearch";

// MainPage import 후 삭제하기
function FeedMain() {
  return <FeedMainPage />;
}

const Stack = createNativeStackNavigator();

const FeedStack = () => {
  return (
    <Stack.Navigator initialRouteName="FeedMain">
      <Stack.Screen
        name="FeedMain"
        component={FeedMain}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="FeedDetail"
        component={FeedDetail}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="FeedSearch"
        component={FeedSearch}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="FeedCreate"
        component={FeedCreate}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="SelectTracking"
        component={SelectTrackingPage}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="FeedFollowList"
        component={FeedFollowList}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="FeedDetailSearch"
        component={FeedDetailSearch}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

export default FeedStack;
