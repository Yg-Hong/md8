import colors from "@/assets/color.js";
import React, { useState } from "react";
import { StyleSheet, Text, View, Pressable } from "react-native";
import Filter from "@icons/Filter.svg";

export default function IndexingCompo({
  isSelected,
  setIsSelected,
  indexes,
  filter,
  handlePresentModalPress,
}) {
  // 어떤 카테고리가 선택되었는지를 표시
  // const [isSelected, setIsSelected] = useState(0);

  const styles = StyleSheet.create({
    indexLineWrapper: {
      flexDirection: "row",
      width: "100%",
      justifyContent: "center",
      alignItems: "center",
    },
    indexWrapper: {
      height: 60,
      paddingTop: 13,
      flexDirection: "row",
      gap: 20,
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
    routingPage: {
      width: "100%",
      height: "100%",
      backgroundColor: "teal",
    },
  });

  return (
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

      {filter ? (
        <Pressable onPress={handlePresentModalPress}>
          <Filter />
        </Pressable>
      ) : null}
    </View>
  );
}
