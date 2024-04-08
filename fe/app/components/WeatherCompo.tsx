import React from "react";
import { View, Text, StyleSheet } from "react-native";
import WeatherBoxCompo from "./WeatherBoxCompo";

interface WeatherProps {
  weatherIcon: string;
  temp: number;
  description: string;
  dust: number;
}

const WeatherCompo = (props: WeatherProps) => {
  return (
    <View style={styles.rootContainer}>
      <WeatherBoxCompo
        weatherIcon={props.weatherIcon}
        description={props.description}
        temp={props.temp}
      />
      <WeatherBoxCompo mode={props.dust} />
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    flexDirection: "row",
    width: "95%",
    paddingTop: 5,
    paddingBottom: 10,
    paddingLeft: 10,
    paddingRight: 10,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 14,
    columnGap: 14,
  },
});

export default WeatherCompo;
