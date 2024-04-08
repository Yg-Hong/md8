import colors from "@/assets/color.js";
import React from "react";
import { Pressable, StyleSheet, Text, View } from "react-native";
import DudurimPicture from "./DudurimPicture";
import DudurimInfo from "./DudurimInfo";

const BigdataCard = ({ data, dudurimInfoMode, onPress }) => {
  return (
    <Pressable style={styles.container} onPress={onPress}>
      <DudurimPicture
        title={data.title}
        address={data.address}
        image={data.image}
        congestion={data.congestion}
        weather={data.weather}
        fineDust={data.fineDust}
        degree={data.degree}
        icon={data.icon}
      />
      <DudurimInfo
        mode={dudurimInfoMode}
        level={data.level}
        distance={data.distance}
        time={data.time}
        water={data.water}
        toilet={data.toilet}
        store={data.store}
      />
    </Pressable>
  );
};

export default BigdataCard;

const styles = StyleSheet.create({
  shadowContainer: {
    flex: 1,
  },
  container: {
    flex: 1,
    alignItems: "center",
    backgroundColor: "#ffffff",
    borderRadius: 8,
    borderColor: colors.lightgreen,
    borderWidth: 2,
  },
});
