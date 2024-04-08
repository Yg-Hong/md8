import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, Dimensions } from "react-native";
import BigdataCard from "@/components/bigdata/BigdataCard";

const BigdataDetailHeader = ({ data }) => {
  const varWidth = Dimensions.get("window").width;

  return (
    <View style={styles.container}>
      <BigdataCard data={data} dudurimInfoMode={"GREEN"} />
    </View>
  );
};

export default BigdataDetailHeader;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
    paddingHorizontal: 22,
    paddingTop: 25,
    gap: 25,
    paddingBottom: 20,
    backgroundColor: colors.lightgreen,
  },
});
