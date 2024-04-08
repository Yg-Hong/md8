import * as React from "react";
import { Text, View } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

function BigDataScreen() {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>BigData!!!</Text>
    </View>
  );
}

function RecommendScreen() {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>Recommend!!!</Text>
    </View>
  );
}

function TrackingScreen() {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>Tracking!</Text>
    </View>
  );
}

function FeedScreen() {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>Feed!!!</Text>
    </View>
  );
}

function UserScreen() {
  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>User!!!</Text>
    </View>
  );
}

const Tab = createBottomTabNavigator();

export default function NavBarCompo() {
  return (
    <Tab.Navigator
    // screenOptions={{
    //   tabBarActiveTintColor: "#fb8c00",
    //   tabBarShowLabel: false,
    //   tabBarInactiveTintColor: "#A9A9A9",
    // }}
    >
      <Tab.Screen
        name="BigData"
        component={BigDataScreen}
        options={{
          headerShown: false,
          tabBarIcon: ({ color, size }) => (
            <Filter width={size} height={size} fill={color} />
          ),
        }}
      />
      <Tab.Screen
        name="Recommend"
        component={RecommendScreen}
        options={{
          headerShown: false,
          tabBarIcon: ({ color, size }) => (
            <Filter width={size} height={size} fill={color} />
          ),
        }}
      />
      <Tab.Screen
        name="Tracking"
        component={TrackingScreen}
        options={{
          headerShown: false,
          // tabBarIcon: ({ color, size }) => (
          //   <Filter width={size} height={size} fill={color} />
          // ),
        }}
      />
      <Tab.Screen
        name="Feed"
        component={FeedScreen}
        options={{
          headerShown: false,
          // tabBarIcon: ({ color, size }) => (
          //   <FeedSvg width={size} height={size} fill={color} />
          // ),
        }}
      />
      <Tab.Screen
        name="User"
        component={UserScreen}
        options={{
          headerShown: false,
          // tabBarIcon: ({ color, size }) => (
          //   <UserSvg width={size} height={size} fill={color} />
          // ),
        }}
      />
    </Tab.Navigator>
  );
}
