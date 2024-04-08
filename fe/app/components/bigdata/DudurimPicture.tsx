import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, ImageBackground } from "react-native";
import WeatherCompo from "../WeatherCompo";
import EntropyTags from "./EntropyTags";

const DudurimPicture = ({
  title,
  address,
  image,
  congestion,
  weather,
  fineDust,
  degree,
  icon,
}) => {
  // fineDust를 숫자로 변경
  const dust = Number(fineDust);

  return (
    <ImageBackground
      source={{ uri: image }}
      resizeMode="cover"
      style={styles.container}
      imageStyle={{
        opacity: 1.2,
        borderTopLeftRadius: 8,
        borderTopRightRadius: 8,
      }}
    >
      <View style={styles.upperContainer}>
        <View style={styles.titleWrapper}>
          <Text style={styles.title}> {title} </Text>
        </View>
        <Text style={styles.address}>{address}</Text>
        <EntropyTags congestion={congestion} />
      </View>
      <View style={styles.lowerContainer}>
        <WeatherCompo
          weatherIcon={icon}
          temp={degree}
          description={weather}
          dust={dust}
        ></WeatherCompo>
      </View>
    </ImageBackground>
  );
};

export default DudurimPicture;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    justifyContent: "space-between",
    height: 400,
  },
  upperContainer: {
    width: "100%",
    paddingHorizontal: 15,
    paddingTop: 20,
    gap: 8,
  },
  titleWrapper: {
    backgroundColor: "rgba(240, 236, 219, 0.4)",
  },
  title: {
    fontFamily: "font6",
    fontSize: 24,
    textShadowColor: "#000000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 10,
    color: "#ffffff",
  },
  address: {
    fontFamily: "font5",
    fontSize: 16,
    textShadowColor: "#000000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 10,
    color: "#ffffff",
  },
  lowerContainer: {
    width: "100%",
    paddingLeft: 15,
    paddingVertical: 10,
  },
});
