import colors from "@/assets/color.js";
import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View, ImageBackground } from "react-native";

import Moring from "@images/Morning.png";
import Noon from "@images/Noon.png";
import Night from "@images/Night.png";

const BigdataMainHeader = ({ currentGu }) => {
  const [imgSource, setImgSource] = useState(Noon);

  useEffect(() => {
    // 현재 시간 조회
    let today = new Date();
    const time = today.getHours() + 9;

    // 시간에 따라 이미지 변경
    if (6 <= time && time < 12) {
      setImgSource(Moring);
    } else if (12 <= time && time < 19) {
      setImgSource(Noon);
    } else {
      setImgSource(Night);
    }
  }, []);

  const styles = StyleSheet.create({
    container: {
      width: "100%",
      paddingHorizontal: 22,
      paddingVertical: 30,
      gap: 15,
      alignItems: "flex-start",
      backgroundColor: colors.lightgreen,
    },
    title: {
      fontFamily: "font6",
      fontSize: 24,
      color: colors.darkbrown,
    },
    description: {
      fontFamily: "font4",
      fontSize: 16,
      color: colors.darkbrown,
    },
    circleWrapper: {
      width: "100%",
      alignItems: "center",
    },
    circle: {
      width: 280,
      height: 280,
      gap: 20,
      justifyContent: "center",
      alignItems: "center",
      borderRadius: 158,
    },
    circleTitle: {
      fontFamily: "font6",
      fontSize: 30,
      color: imgSource === Night ? colors.lightbeige : "#000000",
    },
    circleDescription: {
      fontFamily: "font4",
      fontSize: 15,
      color: imgSource === Night ? colors.lightbeige : "#000000",
      textAlign: "center",
    },
  });

  return (
    <View style={styles.container}>
      <Text style={styles.title}>두드림길이란?</Text>
      <Text style={styles.description}>
        서울의 아름다운 생태, 역사, 문화를 배우고 체험할 수 있도록 서울시에서
        추천하는 도심 속 자연의 느림과 여유를 만끽할 수 있는 코스입니다.
      </Text>
      <View style={styles.circleWrapper}>
        <ImageBackground
          source={imgSource}
          style={styles.circle}
          imageStyle={{ borderRadius: 158 }}
        >
          <Text style={styles.circleDescription}>현재 나의 위치</Text>
          <Text style={styles.circleTitle}>{currentGu}</Text>
          <View>
            <Text style={styles.circleDescription}>{currentGu}에서 가까운</Text>
            <Text style={styles.circleDescription}>두드림길을 추천드려요</Text>
          </View>
        </ImageBackground>
      </View>
    </View>
  );
};

export default BigdataMainHeader;
