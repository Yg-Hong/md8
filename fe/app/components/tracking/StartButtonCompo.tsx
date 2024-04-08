import React from "react";
import { Text, StyleSheet, TouchableOpacity } from "react-native";
import colors from "@/assets/color.js";

interface StartButtonCompoProps {
  setTrackingStatus: (props: number) => void;
}

const StartButtonCompo = (props: StartButtonCompoProps) => {
  const onPressStartButton = () => {
    props.setTrackingStatus(1);
  };

  return (
    <TouchableOpacity style={styles.startButton} onPress={onPressStartButton}>
      <Text style={styles.startButtonText}>{`산책 기록 시작하기`}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  startButton: {
    flexDirection: "row",
    width: "80%",
    height: 65,
    paddingLeft: 27,
    paddingRight: 27,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 30,
    backgroundColor: colors.green,
    shadowColor: "rgba(0, 0, 0, 0.25)",
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 4 },
  },
  startButtonText: {
    fontFamily: "font5",
    color: "white",
    fontSize: 18,
  },
});

export default StartButtonCompo;
