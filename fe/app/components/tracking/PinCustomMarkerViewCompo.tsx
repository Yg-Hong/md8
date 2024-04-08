import React from "react";
import { View, StyleSheet, Image } from "react-native";
import colors from "@/assets/color";

interface PinCustomMarkerViewCompoProps {
  uri: string;
}

const PinCustomMarkerViewCompo = (props: PinCustomMarkerViewCompoProps) => {
  return (
    <View style={styles.imageContainer}>
      <View style={styles.whiteImageContainer}>
        <Image style={styles.image} source={{ uri: props.uri }} />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  imageContainer: {
    width: 40,
    height: 40,
    borderRadius: 30,
    overflow: "hidden",
    backgroundColor: colors.darkbrown,

    justifyContent: "center",
    alignItems: "center",
  },
  whiteImageContainer: {
    width: "90%",
    height: "90%",
    borderRadius: 30,
    backgroundColor: "white",

    justifyContent: "center",
    alignItems: "center",
  },
  image: {
    width: "95%",
    height: "95%",
    borderRadius: 30,
  },
});

export default PinCustomMarkerViewCompo;
