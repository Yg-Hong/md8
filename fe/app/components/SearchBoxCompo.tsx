import React, { useState } from "react";
import { SafeAreaView, StyleSheet, TextInput, Text, View } from "react-native";
import colors from "@/assets/color";
import SearchSvg from "@/assets/icons/Search.svg";

// 검색창 모양만 만듦
// 기능 구현 필요

export default function SearchBoxCompo({ placeholder, value, onChangeText }) {
  // const [search, setSearch] = useState("");

  return (
    <View style={styles.container}>
      <View style={styles.searchBar}>
        <SearchSvg />
        <TextInput
          onChangeText={onChangeText} // 입력된 텍스트를 업데이트하는 함수
          value={value} // 입력된 텍스트 값
          style={styles.input}
          placeholder={placeholder ? placeholder : "placeholder입력"}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignSelf: "stretch",
    paddingHorizontal: 15,
    paddingVertical: 10,
  },
  searchBar: {
    flexDirection: "row",
    paddingVertical: 10,
    paddingHorizontal: 20,
    alignItems: "center",
    alignSelf: "stretch",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: colors.beige,
  },
  input: {
    flex: 1,
    marginLeft: 10,
  },
});
