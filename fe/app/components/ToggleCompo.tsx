import colors from "@/assets/color.js";
import React, { useState, useEffect } from "react";
import { StyleSheet, Text, View, Switch } from "react-native";

// ☆☆☆ 토글버튼이 우측에 있는 경우가 많아 flex-end로 작성했으니 우측 끝 선에 맞춰주세요
export default function ToggleCompo({
  trueText,
  falseText,
  isToggle,
  setIsToggle,
  fontcolor = colors.darkbrown,
}) {
  // 토글의 상태를 참조하는 state 변수
  const getToggleStatus = () => setIsToggle((isToggle) => !isToggle);

  const styles = StyleSheet.create({
    container: {
      height: 35,
      flexDirection: "row",
      gap: 6,
      alignItems: "center",
      justifyContent: "space-between",
    },
  });

  // 동적 styles 선언
  const dynamiStyles = StyleSheet.create({
    text: {
      fontFamily: "font5",
      fontSize: 12,
      color: fontcolor,
    },
  });

  return (
    <View style={styles.container}>
      <Switch
        trackColor={{ false: colors.grey, true: colors.green }}
        thumbColor={isToggle ? "#ffffff" : "#ffffff"}
        onValueChange={getToggleStatus}
        value={isToggle}
      />
      {isToggle ? (
        <Text style={dynamiStyles.text}>{trueText}</Text>
      ) : (
        <Text style={dynamiStyles.text}>{falseText}</Text>
      )}
    </View>
  );
}
