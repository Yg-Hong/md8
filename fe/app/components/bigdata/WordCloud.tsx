import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { TouchableOpacity } from "react-native-gesture-handler";

/* keywords 형식
[[0, 양재천], [1, 산책길], [2, 벚꽃], [3, 라멘], [4, 다리], [5, 서초구], [6, 명곡], [7, 서울], [8, 집], [9, 개포동], [10, 우시], [11, 길], [12, 하천], [13, 곳곳], [14, 봄]]
*/

// 배열 순서 섞는 함수
const shuffleOrder = (orderedList) => {
  for (let i = 14; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [orderedList[i], orderedList[j]] = [orderedList[j], orderedList[i]];
  }
  return orderedList;
};

const WordCloud = ({ keywords }) => {
  // console.log("키워드 3:", keywords);

  // 문자열을 배열로 변환하는 함수
  const strip = keywords.replace(/[\[\]]/g, "");
  const tempList = strip.split(", ");
  const orderedList = [];
  for (let i = 0; i < 30; i += 2) {
    orderedList.push(tempList.slice(i, i + 2));
  }

  const shuffledList = shuffleOrder(orderedList);
  // console.log("### 최종 shuffledList :", shuffledList);

  // 순위에 따라 button 스타일을 바꾸기
  let buttonColorStyle;

  return (
    <View style={styles.container}>
      {shuffledList.map((word, idx) => {
        if (0 <= word[0] && word[0] < 5) {
          buttonColorStyle = styles.buttonColorHigh;
        } else if (5 <= word[0] && word[0] < 10) {
          buttonColorStyle = styles.buttonColorMid;
        } else if (10 <= word[0] && word[0] < 15) {
          buttonColorStyle = styles.buttonColorLow;
        }
        return (
          <TouchableOpacity
            key={idx}
            activeOpacity={0.8}
            style={[styles.button, buttonColorStyle]}
          >
            <Text style={styles.text}>{word[1]}</Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
};

export default WordCloud;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
    flexDirection: "row",
    alignItems: "flex-start",
    gap: 10,
    flexWrap: "wrap",
  },
  button: {
    alignItems: "center",
    paddingHorizontal: 25,
    paddingVertical: 12,
    borderRadius: 23,
    borderColor: colors.green,
    borderWidth: 1,
  },
  text: {
    fontFamily: "font5",
    fontSize: 17,
    color: colors.darkbrown,
  },
  buttonColorHigh: {
    backgroundColor: colors.green,
  },
  buttonColorMid: {
    backgroundColor: colors.lightgreen,
  },
  buttonColorLow: {
    backgroundColor: "#ffffff",
  },
});
