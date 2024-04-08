import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import TrackingMainPage from "@/pages/tracking/TrackingMainPage";
import TrackingInfoDetail from "@/pages/tracking/TrackingInfoDetailPage";
import TrackingCameraPage from "@/pages/tracking/TrackingCameraPage";
import TrackingGalleryPage from "@/pages/tracking/TrackingGalleryPage";

// 여기에 Tracking 도메인의 페이지들을 import
// import TrackingMain from "";

const Stack = createNativeStackNavigator();

const TrackingStack = () => {
  return (
    <Stack.Navigator initialRouteName="TrackingMain">
      <Stack.Screen
        name="TrackingMain"
        component={TrackingMainPage}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="TrackingInfoDetail"
        component={TrackingInfoDetail}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="TrackingCameraPage"
        component={TrackingCameraPage}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="TrackingGalleryPage"
        component={TrackingGalleryPage}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

export default TrackingStack;
