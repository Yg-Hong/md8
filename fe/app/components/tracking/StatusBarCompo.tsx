import React, { useEffect } from "react";
import { View, Image, Text, StyleSheet } from "react-native";
import colors from "@/assets/color.js";
import WeatherCompo from "@/components/WeatherCompo";

interface WeatherProps {
  weatherIcon: string;
  temp: number;
  description: string;
}

interface StatusBarCompoProps {
  trackingStatus: number;
  location: string;
  weather: WeatherProps;
  dust: number;
}

const StatusBarCompo = (props: StatusBarCompoProps) => {
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  // 요일
  const week = [
    "일요일",
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일",
  ];
  const dayOfWeek = week[date.getDay()];

  return (
    <View style={styles.rootContainer}>
      {/* Weather 컴포넌트 */}
      <View style={styles.WeatherContainer}>
        <WeatherCompo
          weatherIcon={props.weather.weatherIcon}
          temp={props.weather.temp}
          description={props.weather.description}
          dust={props.dust}
        />
      </View>

      {/* 하단 컴포넌트 */}
      <View style={styles.lowerContainer}>
        <View style={styles.locationAndDateContainer}>
          <Text style={styles.lowerText}>{`${props.location}`}</Text>
        </View>
        <View style={styles.locationAndDateContainer}>
          <Text
            style={styles.lowerText}
          >{`${year}.${month}.${day} ${dayOfWeek}`}</Text>
        </View>
      </View>

      {/* 일시정지 컴포넌트 */}
      {props.trackingStatus === 2 ? (
        <View style={styles.PauseContainer}>
          <View style={styles.PauseContainer2}>
            <Text style={styles.PauseText}>{`일시 정지 중`}</Text>
          </View>
        </View>
      ) : (
        <></>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    width: "100%",
    flexDirection: "column",
    borderBottomLeftRadius: 25,
    borderBottomRightRadius: 25,
    borderTopLeftRadius: 0,
    borderTopRightRadius: 0,
    backgroundColor: "rgba(255, 255, 255, 0.95)",
    shadowColor: "rgba(0, 0, 0, 0.25)",
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 4 },
  },
  WeatherContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 14,
    columnGap: 14,
    alignSelf: "stretch",
  },
  lowerContainer: {
    flexDirection: "row",
    paddingBottom: 10,
    justifyContent: "space-between",
    alignItems: "center",
    alignSelf: "stretch",
  },
  locationAndDateContainer: {
    flexDirection: "row",
    paddingTop: 2,
    paddingLeft: 20,
    paddingRight: 20,
    paddingBottom: 2,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 15,
  },
  lowerText: {
    color: colors.darkbrown,
    textAlign: "right",
    fontFamily: "font3",
    fontSize: 12,
    fontStyle: "normal",
  },
  PauseContainer: {
    flexDirection: "row",
    paddingBottom: 10,
    justifyContent: "center",
    alignItems: "center",
    alignSelf: "stretch",
  },
  PauseContainer2: {
    flexDirection: "row",
    paddingTop: 2,
    paddingLeft: 20,
    paddingRight: 20,
    justifyContent: "center",
    alignItems: "center",
  },
  PauseText: {
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 20,
    fontStyle: "normal",
  },
});

export default StatusBarCompo;
