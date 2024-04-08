import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, TextInput } from "react-native";
import { useState } from "react";
import LoginSelectList from "./LoginDropDownCompo";

const LoginInputCompo = ({
  defaultValue,
  handleInputChange,
  placeHolder,
  description,
}) => {
  const handleOnChangeText = (inputText) => {
    handleInputChange(inputText);
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.inputBox}
        defaultValue={defaultValue ? defaultValue : null}
        placeholder={placeHolder}
        keyboardType="default"
        maxLength={30}
        onChangeText={handleOnChangeText}
        underlineColorAndroid="transparent"
      />
      <Text style={styles.textBox}>{description}</Text>
    </View>
  );
};

export default LoginInputCompo;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#ffffff",
    // borderRadius: 8,
    // borderWidth: 1,
    // borderColor: colors.darkbrown,
    paddingHorizontal: 10,
  },

  inputBox: {
    flex: 8,
    paddingVertical: 10,
    textAlign: "left",
    borderBottomColor: colors.beige,
    borderBottomWidth: 1,
    fontFamily: "font6",
    fontSize: 20,
    color: colors.darkbrown,
  },
  textBox: {
    flex: 2,
    textAlign: "right",
    fontFamily: "font6",
    fontSize: 25,
    color: colors.darkbrown,
  },
});
