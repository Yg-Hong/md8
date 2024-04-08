import React from "react";
import { View, Text, StyleSheet } from "react-native";
import { TouchableOpacity } from "react-native-gesture-handler";

import colors from "@/assets/color.js";

interface LatLng {
  latitude: number;
  longitude: number;
}

interface StartButtonCompoProps {
  setTrackingStatus: (props: number) => void;
  setGuideRoute: (props: Array<LatLng>) => void;
}

const StartWithGuideButton = (props: StartButtonCompoProps) => {
  const onPressStartButton = () => {
    props.setTrackingStatus(1);
  };

  const onPressCancelButton = () => {
    props.setGuideRoute([]);
    props.setTrackingStatus(0);
  };

  return (
    <View style={styles.rootContainer}>
      <TouchableOpacity
        style={styles.buttonConatiner}
        onPress={onPressCancelButton}
      >
        <Text style={styles.buttonText}>{`취소하기`}</Text>
      </TouchableOpacity>
      <View style={styles.line} />
      <TouchableOpacity
        style={styles.buttonConatiner}
        onPress={onPressStartButton}
      >
        <Text style={styles.buttonText}>{`시작하기`}</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    flexDirection: "row",
    width: "80%",
    height: 72,
    paddingLeft: 27,
    paddingRight: 27,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 25,
    columnGap: 25,
    borderRadius: 30,
    backgroundColor: colors.green,
    shadowColor: "rgba(0, 0, 0, 0.25)",
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 4 },
  },
  buttonConatiner: {
    paddingTop: 24,
    paddingBottom: 24,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  buttonText: {
    fontFamily: "font5",
    color: "white",
    textAlign: "center",
    fontSize: 20,
  },
  line: {
    width: 1,
    height: "70%",
    backgroundColor: "white",
  },
});

export default StartWithGuideButton;
