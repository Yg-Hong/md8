import React from "react";
import { StyleSheet, Text, View, TouchableOpacity } from "react-native";
import { useNavigation } from "@react-navigation/native";
import LeftArrow from "@icons/LeftArrow";
import SettingIcon from "@icons/SettingIcon";
import colors from "@/assets/color.js";

export interface MenuBarCompoProps {
  title?: string;
  settingButton?: boolean;
}

const MenuBarCompo = (props: MenuBarCompoProps) => {
  const navigation = useNavigation();

  const goBack = () => {
    navigation.goBack();
  };

  const onPressSettingButton = () => {
    console.log("onPressSettingButton");
    //todo navigation goSettingPage 이벤트 구현
    navigation.navigate("MySetting");
  };

  return (
    <View style={styles.rootContainer}>
      <View style={styles.navigateContainer}>
        <TouchableOpacity style={styles.vectorContainer} onPress={goBack}>
          <LeftArrow fill={colors.darkbrown} />
        </TouchableOpacity>

        <View style={styles.textContainer}>
          <Text style={styles.text}>{props.title}</Text>
        </View>
      </View>
      <View>
        {props.settingButton && (
          <TouchableOpacity
            style={styles.vectorContainer}
            onPress={onPressSettingButton}
          >
            <SettingIcon fill={colors.darkbrown} />
          </TouchableOpacity>
        )}
      </View>
    </View>
  );
};

// MenuBarCompo.tsx
const styles = StyleSheet.create({
  rootContainer: {
    width: "100%",
    flexDirection: "row",
    marginTop: 20,
    paddingTop: 8,
    paddingLeft: 7,
    paddingRight: 7,
    paddingBottom: 5,
    justifyContent: "space-between",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
  },
  navigateContainer: {
    flexDirection: "row",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
  },
  vectorContainer: {
    flexDirection: "row",
    paddingTop: 8,
    paddingLeft: 7,
    paddingRight: 15,
    paddingBottom: 8,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    color: colors.darkbrown,
  },
  textContainer: {
    padding: 10,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  text: {
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font3",
    fontSize: 17,
    lineHeight: 22,
    letterSpacing: -0.408,
  },
});

export default MenuBarCompo;
