import React from "react";
import { SafeAreaView, StyleSheet, TextInput, Text, View } from "react-native";
import colors from "@/assets/color";
import Title from "@/atoms/Title";

export default function InputBoxCompo({
  mode,
  title,
  placeholder,
  onSubmitEditing,
  reset,
  content,
  setContent,
}) {
  // const [content, onChangeContent] = React.useState("");
  const handleOnSubmitEditing = () => {
    if (onSubmitEditing) {
      onSubmitEditing(content); // 부모 컴포넌트에서 전달된 onSubmitEditing 함수 호출
    }
    if (reset) {
      setContent(""); // 입력값 초기화
    }
  };

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      paddingVertical: 10,
      paddingHorizontal: 0,
      justifyContent: "flex-start",
      alignItems: "center",
      alignSelf: "stretch",
      backgroundColor: "red",
    },
    titleContainer: {
      width: "100%",
      flexDirection: "row",
      justifyContent: "flex-start",
      marginBottom: 3,
    },
    gap: {
      marginVertical: 5,
    },
    nameContainer: {
      flexDirection: "row",
      alignItems: "center",
      alignSelf: "stretch",
      // backgroundColor: "teal",
    },
    text: {
      color: colors.darkbrown,
      // fontFamily: "font6",
      fontSize: 16,
    },
  });

  const dynamicStyles = StyleSheet.create({
    inputContainer: {
      height: mode === "SMALL_INPUT" ? 50 : 120,
      alignSelf: "stretch",
      alignItems: "flex-start",
      // backgroundColor: "orange",
      textAlignVertical: "top",
      borderRadius: 8,
      borderWidth: 1,
      borderColor: colors.beige,
      paddingVertical: 15,
      paddingHorizontal: 5,
    },
  });

  switch (mode) {
    case "SMALL_INPUT":
      return (
        <View>
          <View style={styles.titleContainer}>
            <Title content={title}></Title>
          </View>
          <TextInput
            style={dynamicStyles.inputContainer}
            onChangeText={setContent}
            value={content}
            placeholder={placeholder ? placeholder : "placeholder입력"}
            onChangeText={setContent}
            value={content}
            onSubmitEditing={onSubmitEditing}
          />
        </View>
      );
    case "LARGE_INPUT":
      return (
        <View>
          <View style={styles.titleContainer}>
            <Title content={title}></Title>
          </View>
          <TextInput
            style={dynamicStyles.inputContainer}
            onChangeText={setContent}
            value={content}
            placeholder={placeholder ? placeholder : "placeholder입력"}
            onChangeText={setContent}
            value={content}
            onSubmitEditing={onSubmitEditing}
          />
        </View>
      );
    default:
      return <></>;
  }
}
