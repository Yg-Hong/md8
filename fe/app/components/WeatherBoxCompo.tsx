import React from "react";
import { View, Text, StyleSheet, Image } from "react-native";

import Bad from "@icons/WeatherBoxBad";
import Good from "@icons/WeatherBoxGood";
import Normal from "@icons/WeatherBoxNormal";
import Terrible from "@icons/WeatherBoxTerrible";

import colors from "@/assets/color";

interface WeatherBoxCompoProps {
  mode?: number;
  weatherIcon?: string;
  temp?: number;
  description?: string;
}

/**
 * @param mode 날씨 아이콘 모드
 * @param weather 날씨
 *
 * mode 1: 미세먼지 좋음
 * mode 2: 미세먼지 보통
 * mode 3: 미세먼지 나쁨
 * mode 4: 미세먼지 매우 나쁨
 * @returns
 */
const WeatherBoxCompo = (props: WeatherBoxCompoProps) => {
  let weather: string = "";
  let fineDust: string = "";

  if (typeof props === "object") {
    if (props.mode === 1) {
      fineDust = "좋음";
    } else if (props.mode === 2) {
      fineDust = "보통";
    } else if (props.mode === 3) {
      fineDust = "나쁨";
    } else if (props.mode === 4) {
      fineDust = "매우 나쁨";
    }
  }

  return (
    <View style={styles.rootContainer}>
      <View style={styles.iconContainer}>
        {typeof props.weatherIcon === "string" ? (
          <Image
            style={styles.weatherIcon}
            source={{
              uri: `https://openweathermap.org/img/wn/${props.weatherIcon}@2x.png`,
            }}
          />
        ) : props.mode === 1 ? (
          <Good />
        ) : props.mode === 2 ? (
          <Normal />
        ) : props.mode === 3 ? (
          <Bad />
        ) : props.mode === 4 ? (
          <Terrible />
        ) : (
          <></>
        )}
      </View>
      <View style={styles.textContainer}>
        <View style={styles.textComponent}>
          {typeof props.weatherIcon === "string" ? (
            <Text style={styles.text}>{`현재 날씨`}</Text>
          ) : (
            <Text style={styles.text}>{`미세먼지`}</Text>
          )}
        </View>
        <View style={styles.textComponent}>
          {typeof props.weatherIcon === "string" ? (
            <Text style={styles.text}>{`${props.temp}℃ ${weather}`}</Text>
          ) : (
            <Text style={styles.text}>{`${fineDust}`}</Text>
          )}
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    flexDirection: "row",
    width: "50%", //todo: width를 좀더 유동적으로 변경할 수 있도록 수정
    height: 70,
    paddingTop: 14,
    paddingLeft: 20,
    paddingRight: 20,
    paddingBottom: 14,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 15,
    columnGap: 15,
    flexShrink: 0,
    borderRadius: 15,
    borderWidth: 1,
    borderStyle: "solid",
    borderColor: colors.lightgreen,
    backgroundColor: "#ffffff",
  },
  iconContainer: {
    paddingTop: 5,
    paddingLeft: 4,
    paddingRight: 4,
    paddingBottom: 5,
    width: 50,
    height: 50,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  weatherIcon: {
    width: 50,
    height: 50,
  },
  textContainer: {
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
  },
  textComponent: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  text: {
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 14,
  },
});

export default WeatherBoxCompo;
