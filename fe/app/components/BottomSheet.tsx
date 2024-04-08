import colors from "@/assets/color.js";
import { useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import Title from "@/atoms/Title";
import ToggleCompo from "./ToggleCompo";
import ButtonCompo from "./ButtonCompo";

export default function BottomSheet({ yesButton, noButton }) {
  // 토글의 상태관리 위한 state 변수 -> setIsToggle 들어감
  const [isWater, setIsWater] = useState(false);
  const [isToilet, setIsToilet] = useState(false);
  const [isSafe, setIsSafe] = useState(false);

  return (
    <View style={styles.container}>
      <Title content={"필터 적용하기"} fontsize={16} marginbottom={15}></Title>

      <View>
        <View style={styles.toggleContainer}>
          <Text style={styles.font}>음수대</Text>
          <ToggleCompo
            trueText={"필수"}
            falseText={"무관"}
            isToggle={isWater}
            setIsToggle={setIsWater}
          ></ToggleCompo>
        </View>
        <View style={styles.toggleContainer}>
          <Text style={styles.font}>화장실</Text>
          <ToggleCompo
            trueText={"필수"}
            falseText={"무관"}
            isToggle={isToilet}
            setIsToggle={setIsToilet}
          ></ToggleCompo>
        </View>
        <View style={styles.toggleContainer}>
          <Text style={styles.font}>안전지킴이</Text>
          <ToggleCompo
            trueText={"필수"}
            falseText={"무관"}
            isToggle={isSafe}
            setIsToggle={setIsSafe}
          ></ToggleCompo>
        </View>
      </View>

      <ButtonCompo
        mode={"TWO_BUTTONS"}
        textGreen={"적용하기"}
        textWhite={"취소"}
        onPressGreen={yesButton}
        onPressWhite={noButton}
      ></ButtonCompo>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "100%",
    alignItems: "center",
    justifyContent: "center",
    gap: 35,
  },
  toggleContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-between",
  },
  font: {
    fontFamily: "font5",
    fontSize: 16,
    color: colors.darkbrown,
  },
});
