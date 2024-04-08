import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Image } from "react-native";

// Tab 페이지 import
import BigDataStack from "@/Navigation/DomainStacks/BigDataStack";
import RecommendStack from "@/Navigation/DomainStacks/RecommendStack";
import TrackingStack from "@/Navigation/DomainStacks/TrackingStack";
import FeedStack from "@/Navigation/DomainStacks/FeedStack";
import UserStack from "@/Navigation/DomainStacks/UserStack";
// import Title from "@/atoms/Title";

import BigData from "@images/BigData.png";
import Recommend from "@images/Recommend.png";
import Tracking from "@images/Tracking.png";
import Feed from "@images/Feed.png";
import User from "@images/User.png";

const Tab = createBottomTabNavigator();

function TabNavigator() {
  return (
    <Tab.Navigator
      initialRouteName="TrackingStack"
      screenOptions={{ tabBarShowLabel: false }}
    >
      <Tab.Screen
        name="BigDataStack"
        component={BigDataStack}
        options={{
          headerShown: false,
          title: "BigData",
          tabBarIcon: ({ color, size }) => (
            <Image source={BigData} style={{ width: size, height: size }} />
          ),
        }}
      />

      <Tab.Screen
        name="RecomendStack"
        component={RecommendStack}
        options={{
          headerShown: false,
          title: "Recommend",
          tabBarIcon: ({ color, size }) => (
            <Image
              source={Recommend}
              style={{ width: size, height: size, resizeMode: "contain" }}
            />
          ),
        }}
      />
      <Tab.Screen
        name="TrackingStack"
        component={TrackingStack}
        options={{
          headerShown: false,
          title: "Tracking",
          tabBarIcon: ({ color, size }) => (
            <Image
              source={Tracking}
              style={{ width: size, height: size, resizeMode: "contain" }}
            />
          ),
        }}
      />
      <Tab.Screen
        name="FeedStack"
        component={FeedStack}
        options={{
          headerShown: false,
          title: "Feed",
          tabBarIcon: ({ color, size }) => (
            <Image
              source={Feed}
              style={{ width: size, height: size, resizeMode: "contain" }}
            />
          ),
        }}
      />
      <Tab.Screen
        name="UserStack"
        component={UserStack}
        options={{
          headerShown: false,
          title: "User",
          tabBarIcon: ({ color, size }) => (
            <Image
              source={User}
              style={{ width: size, height: size, resizeMode: "contain" }}
            />
          ),
        }}
      />
    </Tab.Navigator>
  );
}

export default TabNavigator;
