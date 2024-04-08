import colors from "@/assets/color.js";
import React from "react";
import { useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  ScrollView,
  FlatList,
  Pressable,
} from "react-native";

import RecommendSortedByAlgo from "./RecommendSortedByAlgo";
import RecommendSortedByLike from "./RecommendSortedByLike";
import Filter from "@icons/Filter.svg";

const RecommendMainBody = ({ time, dist, handlePresentModalPress }) => {
  // 어떤 카테고리가 선택되었는지를 표시
  const [isSelected, setIsSelected] = useState(0);
  const indexes = ["추천 순", "찜 많은 순"];

  return (
    <View style={styles.container}>
      {/* 인덱스 선택란을 출력하는 부분 */}
      <View style={styles.indexLineWrapper}>
        <View style={styles.indexWrapper}>
          {indexes.map((index, idx) => {
            return isSelected === idx ? (
              <Pressable
                key={idx}
                style={styles.selectedIndex}
                onPress={() => {
                  setIsSelected(idx);
                }}
              >
                <Text style={styles.selectedText}>{index}</Text>
              </Pressable>
            ) : (
              <Pressable
                key={idx}
                style={styles.unselectedIndex}
                onPress={() => {
                  setIsSelected(idx);
                }}
              >
                <Text style={styles.unselectedText}>{index}</Text>
              </Pressable>
            );
          })}
        </View>
        <Pressable onPress={handlePresentModalPress}>
          <Filter />
        </Pressable>
      </View>

      {/* 선택된 인덱스에 따라 다른 컴포넌트를 출력하는 부분 */}
      {isSelected === 0 ? (
        <RecommendSortedByAlgo time={time} dist={dist} />
      ) : (
        <RecommendSortedByLike time={time} dist={dist} />
      )}
    </View>
  );
};

export default RecommendMainBody;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    alignItems: "center",
    paddingVertical: 5,
    paddingHorizontal: 22,
  },
  routingPage: {
    width: "100%",
    height: "100%",
  },
  // indexing 용 styles
  indexLineWrapper: {
    flexDirection: "row",
    width: "100%",
    justifyContent: "space-between",
    alignItems: "center",
  },
  indexWrapper: {
    height: 60,
    paddingTop: 13,
    flexDirection: "row",
    gap: 10,
    alignItems: "flex-start",
    justifyContent: "flex-start",
  },
  selectedIndex: {
    paddingVertical: 5,
    paddingHorizontal: 5,
    borderBottomWidth: 2,
    borderBottomColor: colors.darkbrown,
  },
  unselectedIndex: {
    paddingVertical: 5,
    paddingHorizontal: 5,
    borderBottomColor: colors.darkbrown,
  },
  selectedText: {
    fontFamily: "font6",
    fontSize: 16,
    color: colors.darkbrown,
    textAlign: "center",
  },
  unselectedText: {
    fontFamily: "font6",
    fontSize: 16,
    color: "#9B937A",
    textAlign: "center",
  },
});
