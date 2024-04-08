import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// 여기에 Recommend 도메인의 페이지들을 import
import RecommendMain from "@/pages/recommend/RecommendMain";

const Stack = createNativeStackNavigator();

const RecommendStack = () => {
  return (
    <Stack.Navigator initialRouteName="RecommendMain">
      <Stack.Screen
        name="RecommendMain"
        component={RecommendMain}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

export default RecommendStack;
