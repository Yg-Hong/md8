import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import WordCloud from "@/components/bigdata/WordCloud";

const BigdataDetailBody = ({ title, content, keywords }) => {
  console.log("키워드 2: ", keywords);
  console.log(typeof keywords);

  return (
    <View style={styles.container}>
      <View style={styles.innerContainer}>
        <Text style={styles.title}>두드림길 정보</Text>
        <Text style={styles.content}>{content}</Text>
      </View>
      <View style={styles.innerContainer}>
        <Text style={styles.title}>연관 검색어</Text>
        <Text style={styles.content}>
          {title}(와/과) 함께 이런 단어들이 검색되었어요.
        </Text>
      </View>
      <WordCloud keywords={keywords} />
    </View>
  );
};

export default BigdataDetailBody;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
    paddingHorizontal: 22,
    paddingTop: 25,
    gap: 30,
    paddingBottom: 20,
  },
  innerContainer: {
    gap: 10,
  },
  title: {
    fontFamily: "font6",
    fontSize: 16,
    color: colors.darkbrown,
  },
  content: {
    fontFamily: "font4",
    fontSize: 15,
    color: colors.darkbrown,
    lineHeight: 25,
  },
});
