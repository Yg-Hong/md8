import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, Image } from "react-native";
import PagerView from "react-native-pager-view";

const CarouselCompo = () => {
  return (
    <View style={styles.container}>
      <PagerView style={styles.viewPager} initialPage={0}>
        <View style={styles.page} key="1">
          <Text>First page</Text>
          <Text>Swipe ➡️</Text>
        </View>
        <View style={styles.page} key="2">
          <Text>Second page</Text>
        </View>
        <View style={styles.page} key="3">
          <Text>Third page</Text>
        </View>
      </PagerView>
    </View>
  );
};

export default CarouselCompo;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: 200,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: colors.lightbeige,
  },
  viewPager: {
    flex: 1,
  },
  page: {
    justifyContent: "center",
    alignItems: "center",
  },
});
