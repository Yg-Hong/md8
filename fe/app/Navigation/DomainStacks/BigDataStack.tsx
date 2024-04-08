import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import BigDataMain from "@/pages/bigdata/BigDataMain";
import BigDataDetail from "@/pages/bigdata/BigDataDetail";

const Stack = createNativeStackNavigator();

const BigDataStack = () => {
  return (
    <Stack.Navigator initialRouteName="BigDataMain">
      <Stack.Screen
        name="BigDataMain"
        component={BigDataMain}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="BigDataDetail"
        component={BigDataDetail}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

export default BigDataStack;
