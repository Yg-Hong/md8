import { useState } from "react";
import colors from "@/assets/color";
import { StyleSheet, Text, View, TouchableOpacity } from "react-native";

export default function SmallButtonCompo({ mode, text, onPress }) {
  switch (mode) {
    // 짙은 갈색 버튼 (차단해제, 수정, 삭제 등)
    case "BROWN_BUTTON":
      return (
        <TouchableOpacity
          activeOpacity={0.9}
          style={styles.brownButton}
          onPress={onPress}
        >
          <Text style={styles.brownButtonText}>
            {text ? text : "내용 입력"}
          </Text>
        </TouchableOpacity>
      );

    // 베이지색 버튼 (Recommend '수정하기'에 사용)
    case "BEIGE_BUTTON":
      return (
        <TouchableOpacity
          activeOpacity={0.9}
          style={styles.beigeButton}
          onPress={onPress}
        >
          <Text style={styles.beigeButtonText}>
            {text ? text : "내용 입력"}
          </Text>
        </TouchableOpacity>
      );
    default:
      return <></>;
  }
}

const styles = StyleSheet.create({
  brownButton: {
    width: 80,
    height: 26,
    backgroundColor: "#9B937A",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  brownButtonText: {
    fontFamily: "font6",
    fontSize: 12,
    color: "#ffffff",
  },
  beigeButton: {
    width: 130,
    height: 36,
    backgroundColor: colors.lightbeige,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  beigeButtonText: {
    fontFamily: "font6",
    fontSize: 16,
    color: colors.darkbrown,
  },
});
